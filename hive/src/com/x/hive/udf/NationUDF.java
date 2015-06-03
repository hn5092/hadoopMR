package com.x.hive.udf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;

public class NationUDF extends UDF{
	public static Map<String,String> nationMap=new HashMap<String,String>();
	static{
		nationMap.put("china", "中国");
		nationMap.put("japan","小日本");
	}
	Text t=new Text();
	// sum(incom)
	public Text evaluate (LongWritable id,Text nation){
		String nation_e=nation.toString();
		String names = nationMap.get(nation_e);
		if(names==null){
			names="火星人"+id.toString();
		}
		t.set(names);
		return t;
	}
}
