package com.x.hadoop.mr;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 1.分析具体业务逻辑,确定输出入数据的样式
 * 2.自定义一个类,这个类要继承map这个类 重写map方法 实现具体的业务逻辑,将新的kv输出
 * 2.自顶一个一个类,这个类要继承reduces 重写 reduces方法 实现具体的业务逻辑,将新的kv输出
 * 4.将自定义的mapper和reduces组装起来
 * @author Administrator
 *
 */
public class WordCount {

	public static void main(String[] args) throws Exception {

		
			Job job =Job.getInstance(new Configuration());
			//注意,main方法所在的类
			job.setJarByClass(WordCount.class);
			//设置Mapper相关属性
			job.setMapperClass(WCMapper.class);
			job.setMapOutputKeyClass(Text.class);
			job.setMapOutputValueClass(LongWritable.class);
			FileInputFormat.setInputPaths(job, new Path("/words"));
			//设置reduces属性
			job.setReducerClass(WCReducer.class);
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(LongWritable.class);  //可以指定MAP 也可以指定reduce 因为有时候可以没有reduces
			FileOutputFormat.setOutputPath(job, new Path("/wcc"));//切记都得写上/ 
			
			job.waitForCompletion(true);
			
	}

}
