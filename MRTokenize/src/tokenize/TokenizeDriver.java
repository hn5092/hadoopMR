package tokenize;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import tokenize.inputformat.MyInputFormat;


public class TokenizeDriver {

	public static void main(String[] args) throws Exception {
		
		// set configuration
		Configuration conf = new Configuration();
		conf.setLong("mapreduce.input.fileinputformat.split.maxsize", 4000000);    //max size of Split
		
		Job job = new Job(conf,"Tokenizer");
		job.setJarByClass(TokenizeDriver.class);

	    // specify input format
		job.setInputFormatClass(MyInputFormat.class);
		
        //  specify mapper
		job.setMapperClass(tokenize.TokenizeMapper.class);
		
		// specify output types
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		// specify input and output DIRECTORIES 
		Path inPath = new Path(args[0]);
		Path outPath = new Path(args[1]);
		try {                                            //  input path
			FileSystem fs = inPath.getFileSystem(conf);
			FileStatus[] stats = fs.listStatus(inPath);
			for(int i=0; i<stats.length; i++)
				FileInputFormat.addInputPath(job, stats[i].getPath());
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}			
        FileOutputFormat.setOutputPath(job,outPath);     //  output path

		// delete output directory
		try{
			FileSystem hdfs = outPath.getFileSystem(conf);
			if(hdfs.exists(outPath))
				hdfs.delete(outPath);
			hdfs.close();
		} catch (Exception e){
			e.printStackTrace();
			return ;
		}
		
		//  run the job
		System.exit(job.waitForCompletion(true) ? 0 : 1);
		
	}

}
