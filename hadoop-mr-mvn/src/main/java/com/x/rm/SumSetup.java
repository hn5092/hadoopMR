package com.x.rm;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SumSetup {

	
	
	
	public static class SumMapper extends Mapper<LongWritable, Text, Text, InfoBean>{
		private Text k=new Text();
		private InfoBean bean=new InfoBean();
		@Override
		protected void map(LongWritable key, Text value,Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] field=line.split("\t");
			String acount =field[0];
			double in=Double.parseDouble(field[1]);
			double out=Double.parseDouble(field[2]);
			k.set(acount);
			bean.set(acount, in, out);
			context.write(k,bean );
		}
	}
	public static class SumReducer extends Reducer<Text, InfoBean, Text, InfoBean>{
		private InfoBean v=new InfoBean();
		@Override
		protected void reduce(Text key, Iterable<InfoBean> value,
				Reducer<Text, InfoBean, Text, InfoBean>.Context context)
				throws IOException, InterruptedException {
			double in_sum=0;
			double out_sum=0;
			for(InfoBean bean:value){
				in_sum+=bean.getIncome();
				out_sum+=bean.getExpenses();
			}
			v.set("", in_sum, out_sum);
		context.write(key, v);
			
		}
	}
	public static void main(String[] args)throws Exception{
		Configuration conf =new Configuration();
		Job job =Job.getInstance(conf);
		job.setJarByClass(SumSetup.class);
		job.setMapperClass(SumMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(InfoBean.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(SumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(InfoBean.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
		
		
		
	}
}
