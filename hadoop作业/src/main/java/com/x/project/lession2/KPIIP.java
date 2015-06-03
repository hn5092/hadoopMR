package com.x.project.lession2;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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


public class KPIIP {
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(KPIIP.class);

		job.setMapperClass(KPIIPMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat.setInputPaths(job, new Path(args[0]));

		job.setReducerClass(KPIIPReduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		job.waitForCompletion(true);
	}

	public static class KPIIPMapper extends
			Mapper<LongWritable, Text, Text, Text> {
			Text k2 = new Text("pv");
			Text v2 = new Text();
		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			KPI kpi = KPI.filterIPs(value.toString());
			if(kpi.isValid()){
				v2.set(kpi.getRequest());
			context.write(k2,v2 );
			}
			
			
			
		}

	}

	public static class KPIIPReduce extends Reducer<Text, Text, Text, Text> {
			Text v3 = new Text();
		
		@Override
		protected void reduce(Text k2, Iterable<Text> v2,
				Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			Set<String> total = new HashSet<String>();
			for(Text i:v2){
				total.add(i.toString());
				}
			v3.set(total.size()+"");
			context.write(k2, v3);
		}

	}

}
