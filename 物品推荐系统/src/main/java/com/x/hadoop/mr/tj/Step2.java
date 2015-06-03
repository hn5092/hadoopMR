package com.x.hadoop.mr.tj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Step2 {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(Step2.class);
		
		job.setMapperClass(StepMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		
		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);

	}

	public static class StepMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
		private Text k2 = new Text();
		private IntWritable v2 = new IntWritable(1);
		
		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] values=line.split("\t");
			String[] mvs=values[1].split(",");//[103:3.0,102:2.5]
			List<String> mid=new ArrayList<String>();
			for(String s:mvs){
			mid.add(s.split(":")[0]);
			}
			for(String s:mid){
				for(String s2:mid){
					k2.set(s+":"+s2);
					context.write(k2, v2);
				}
			}
			
			
		}
		
	}
	
	
	public static class StepReducer extends Reducer<Text, IntWritable, Text, Text>{

		private Text v3 = new Text();

		@Override
		protected void reduce(Text k2, Iterable<IntWritable> v2,Context context)
				throws IOException, InterruptedException {
			int count =0;
			for(IntWritable i:v2){
				count++;
			}
			v3.set(count+"");
			context.write(k2, v3); 
		}
		
	
		
	}
}
