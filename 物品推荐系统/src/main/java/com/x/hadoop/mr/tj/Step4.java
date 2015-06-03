package com.x.hadoop.mr.tj;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/**
 * 该类用来生成的矩阵
 * 输入是step3
 * in:101	4,2,5,2,1,4,3
 * out:101:4,2,5,2,1,4,3|102:3,4,4,2,2,1,1|
 * @author Administrator
 *
 */
public class Step4 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(Step4.class);
		
		job.setMapperClass(StepMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(NullWritable.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

	}
	//in 101	4,2,5,2,1,4,3 
	public static class StepMapper extends Mapper<LongWritable, Text, IntWritable, Text>{
		IntWritable k2=new IntWritable(1);
		Text v2 = new Text();
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] values=line.split("\t");
			v2.set(values[0]+";"+values[1]);
			context.write(k2, v2);
			
		}
		
	}
	
	//in 101	4,2,5,2,1,4,3 
	public static class StepReducer extends Reducer<IntWritable, Text, NullWritable, Text>{

		private Text v3 = new Text();

		@Override
		protected void reduce(IntWritable k2, Iterable<Text> v2,Context context)
				throws IOException, InterruptedException {
			StringBuffer count=new StringBuffer();
			for(Text t:v2){
				count.append(t.toString()+"\n");
			}
			String line=count.toString();
			line=line.substring(0,line.lastIndexOf("\n"));
			v3.set(line);
			context.write(NullWritable.get(), v3); 
		}
		
	
		
	}
}
