package com.x.hadoop.hdfs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class HDFSDemo {
	FileSystem fs = null;

	@Before
	public void init() throws Exception {
		fs = FileSystem.get(new URI("hdfs://xym01:9000"), new Configuration(),
				"root");
	}

	@Test
	public void testUpload() throws Exception {
		// 读取本地文件系统的文件
		InputStream in = new FileInputStream("e://hadoop-shell.pdf");
		OutputStream out = fs.create(new Path("/xym01/test.pdf")); // 如果路径不写他会自动写
																// user/root
		IOUtils.copyBytes(in, out, 4096, true);

	}
	@Test//下载
	public void testDownLoad() throws IllegalArgumentException, IOException{
		fs.copyToLocalFile( new Path("/jdk1.7"), new Path("e://jdk111"));
	}
	@Test//删除
	public void delete() throws IllegalArgumentException, IOException{
		boolean flag=fs.delete(new Path("/xym01"), true); //删除,是否递归  递归会直接删除目录下其他的
		System.out.println(flag);
	}
	
	@Test //创建目录
	public void testMKdir() throws IllegalArgumentException, IOException{
		boolean mkdirs = fs.mkdirs(new Path("/xym01"));
		System.out.println(mkdirs);
	}
	
	public static void main(String[] args) throws Exception {
		FileSystem fs = FileSystem.get(new URI("hdfs://xym01:9000"),new Configuration());
		InputStream in = fs.open(new Path("/jdk1.7"));// 在hdfs上
		OutputStream out = new FileOutputStream("e://jkd1.7");
		IOUtils.copyBytes(in, out, 4096, true);// 拷贝完成之后传true 4096大师级

	}
}
