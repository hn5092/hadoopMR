package com.x.hadoop.mr;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WCReducer extends Reducer<Text, LongWritable, Text, LongWritable>{

	@Override
	protected void reduce(Text k2, Iterable<LongWritable> v2,Context context)
			throws IOException, InterruptedException {
		//接收数据
		//定义一个计数器
		long counter = 0;
		for(LongWritable l:v2){
			counter+=l.get();
		}
		context.write(k2, new LongWritable(counter));
	}

}
