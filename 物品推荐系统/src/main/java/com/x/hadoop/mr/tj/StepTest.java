package com.x.hadoop.mr.tj;

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

public class StepTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(StepTest.class);
		
		job.setMapperClass(StepMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

	}

	public static class StepMapper extends Mapper<LongWritable, Text, Text, Text>{
		Text k2 = new Text();
		Text v2 = new Text();
		
		@Override
		//1	103:2.5,101:5.0,102:3.0
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] values=line.split("\t");
			k2.set(values[0]);
			v2.set(values[1]);
			context.write(k2, v2);
			
		}
		
	}
	
	//1,[103:2.5,101:5.0,102:3.0]
	public static class StepReducer extends Reducer<Text, Text, Text, Text>{

		private Text v3 = new Text();

		@Override
		protected void reduce(Text k2, Iterable<Text> v2,
				Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			StringBuffer count=new StringBuffer();
			for(Text t:v2){
			String line =t.toString();
			String[] a=line.split(",");
			for(String s:a){
				count.append(s.split(":")[1]+",");
			}}
			v3.set(count.toString().substring(0,count.lastIndexOf(",")));
			context.write(k2, v3); 
		}
		
	
		
	}
}
