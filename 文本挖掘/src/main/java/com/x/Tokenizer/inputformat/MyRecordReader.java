package com.x.Tokenizer.inputformat;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;	
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.CombineFileSplit;

public class MyRecordReader extends RecordReader<Text, Text> {
	private CombineFileSplit combineFileSplit;  //当前处理的 是哪个分片
	private int totalLength;  				    //分片包含的文件数量
	private int currentIndex;					//分片处理的文件的索引
	private float currentProgress = 0 ;			//当前的任务进度
	private Text currentKey = new Text();		//当前的Key值
	private Text curretnValue = new Text();		//当前的value值
	private Configuration conf;					//任务信息
	private boolean processed;					//记录当前文件是否已经被读取
	
	public MyRecordReader(CombineFileSplit combineFileSplit,TaskAttemptContext context,Integer index) throws IOException {
		super();
		this.combineFileSplit = combineFileSplit;
		this.currentIndex = index;
		conf = context.getConfiguration();
		totalLength = combineFileSplit.getPaths().length;
		processed  = false;
		
	}
	
	
	@Override
	public void initialize(InputSplit split, TaskAttemptContext context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Text getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return currentKey;
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return curretnValue;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}

	
	
}