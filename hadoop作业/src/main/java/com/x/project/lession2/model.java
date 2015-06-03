package com.x.project.lession2;

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

public class model {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(model.class);

		job.setMapperClass(KPIMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));

		job.setReducerClass(KPIReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
	}

	public static class KPIMapper extends
			Mapper<LongWritable, Text, Text, Text> {
			Text k2 = new Text();
			Text v2 = new Text();
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			
			
			
			
		}

	}

	public static class KPIReduce extends Reducer<Text, Text, Text, Text> {
			Text k3 = new Text();
			Text v3 = new Text();
		@Override
		protected void reduce(Text arg0, Iterable<Text> arg1,
				Reducer<Text, Text, Text, Text>.Context arg2)
				throws IOException, InterruptedException {
			
		}

	}

}
