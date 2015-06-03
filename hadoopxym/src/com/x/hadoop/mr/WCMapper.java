package com.x.hadoop.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WCMapper extends Mapper<LongWritable, Text,Text, LongWritable> {

	@Override
	protected void map(LongWritable key, Text value,Mapper<LongWritable, Text, Text, LongWritable>.Context context)
			throws IOException, InterruptedException {
		//接收数据V1
		String line =value.toString();
		//切分数据
		String[] words =line.split(" ");
		for(String s:words){
			context.write(new Text(s), new LongWritable(1));
		}
		
	}

	

}
