package com.x.hadoop.mr.tj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Step5 {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf);
		job.setJarByClass(Step5.class);

		job.setMapperClass(StepMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		FileInputFormat
				.setInputPaths(job, new Path(args[0]), new Path(args[1]));

		job.setReducerClass(StepReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		job.waitForCompletion(true);

	}

	public static class StepMapper extends
			Mapper<LongWritable, Text, Text, Text> {
		Text k2 = new Text();
		Text v2 = new Text();
		public static final List<String> list = new ArrayList<String>();

		@Override
		// 101 4,2,5,2,1,4,3 这是文件1
		// 1 2.5,5.0,3.0 这是文件2
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			InputSplit inputSplit = (InputSplit) context.getInputSplit();
			String fileName = ((FileSplit) inputSplit).getPath().toString();
			if (fileName.contains("step1")) {
				String line = value.toString();
				String[] values = line.split("\t");
				k2.set(values[0]);
				v2.set(values[1]);
				context.write(k2, v2);
			}
			if (fileName.contains("step6")) {
				k2.set("0");
				context.write(k2, value);
			}

		}

	}

	// 1,[103:2.5,101:5.0,102:3.0]
	public static class StepReducer extends Reducer<Text, Text, Text, Text> {
		private Text v3 = new Text();
		private Text k3 = new Text();
		Map<String, List<IteamInfo>> mar = new HashMap<String, List<IteamInfo>>();
			//记录所有的iteamid用来 循环
		List<String> iteams = new ArrayList<String>();

		@Override
		protected void reduce(Text k2, Iterable<Text> v2,
				Reducer<Text, Text, Text, Text>.Context context)
				throws IOException, InterruptedException {
			if (k2.toString().equals("0")) {
				for (Text t : v2) {
					//101;103:4,105:2,101:5,106:2,107:1,104:4,102:3
					String line = t.toString();
					String[] mars = line.split(";");//101   103:4,105:2,101:5,106:2,107:1,104:4,102:3
					String iteamId1 = mars[0];	//101 
					iteams.add(iteamId1);
					String[] iteamNum = mars[1].split(",");//103:4   
					List<IteamInfo> infos = new ArrayList<IteamInfo>();
					for (String s : iteamNum) {
						String iteamId2 = s.split(":")[0];//103
						String time = s.split(":")[1];//4
						IteamInfo info = new IteamInfo(iteamId2,
								Integer.parseInt(time));
						infos.add(info);
					}
					mar.put(iteamId1, infos);

				}
			} else {
				for (Text t : v2) {
					for (String s2 : iteams) {
						//103:2.5,101:5.0,102:3.0
						List<IteamInfo> list = mar.get(s2);
						// 通过list得到每一列 然后遍历
						String[] score = t.toString().split(",");//103:2.5
						
						Map<String, Double> map = new HashMap<String, Double>();
						//
						for (String s : score) {
							map.put(s.split(":")[0],
									Double.parseDouble(s.split(":")[1])); //103 2.5
						}
						double count = 0.0;
						for (IteamInfo i : list) {
							if (map.get(i.getIteamId()) == null) {
								continue;
							} else {
								count += map.get(i.getIteamId()) * i.getNum();
							}
						}
						v3.set(s2+":"+count);
						context.write(k2, v3);
					}

				}
			}

		}
	}
}
