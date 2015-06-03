package com.x.project.lession2;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class KPISource {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(KPISource.class);

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
			KPI k = KPI.filterIPs(value.toString());
			if(k.isValid()){
			k2.set(k.getHttp_referer());
			v2.set(k.getRemote_addr());
			context.write(k2, v2);
			}
		}

	}

	public static class KPIReduce extends Reducer<Text, Text, Text, Text> {
		Text k3 = new Text();
		Text v3 = new Text();

		@Override
		protected void reduce(Text k2, Iterable<Text> v2,
				Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			Set<String> total = new HashSet<String>();
			for (Text t : v2) {
				total.add(t.toString());
			}
			v3.set(total.size() + "");
			;
			context.write(k2, v3);
		}

	}

}
