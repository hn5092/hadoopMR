package com.x.basic.lession2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

public class GetHdfsFile {
	FileSystem fs = null;

	@Before
	public void init() throws IOException, InterruptedException, URISyntaxException {
		Configuration conf = new Configuration();
		conf.set("dfs.nameservices", "ns1");
		conf.set("dfs.ha.namenodes.ns1", "nn1,nn2");
		conf.set("dfs.namenode.rpc-address.ns1.nn1", "xym01:9000");
		conf.set("dfs.namenode.rpc-address.ns1.nn2", "xym02:9000");
		conf.set("dfs.client.failover.proxy.provider.ns1",
				"org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");// 实现方士
		fs = FileSystem.get(new URI("hdfs://ns1"), conf, "root");
	}

	@Test
	public void openFile() throws IOException {
		InputStream in = null;
		in = fs.open(new Path("hdfs://ns1/native.tar.gz"));
		IOUtils.copyBytes(in, System.out, 4096, false);
		fs.close();
	}
	@Test
	public void testUpload() throws IllegalArgumentException, IOException{
		fs.copyFromLocalFile(new Path("c://log"), new Path("/upload/log1"));
	}
	@Test
	public void upLoadFile()throws Exception{
		File f = new File("c://log");
		Path dfs = new Path("/upload/log");
		FileInputStream fis = new FileInputStream(f);
		FSDataOutputStream fsDataOutputStream = fs.create(dfs);
		byte[] b2 = new byte[20];
		fis.skip(100);
		for(int i=100;i<120;i++){
			b2[i-100] = (byte) fis.read();
		}
		fsDataOutputStream.write(b2);
		fsDataOutputStream.close();
		fis.close();
		
		
	}
	@Test
	public void readFile()throws Exception{
		InputStream is = fs.open(new Path("/upload/log1"));
		OutputStream os = new FileOutputStream("c:\\log2");
		is.skip(100);
		byte[] b2 = new byte[20];
		for(int i=100;i<120;i++){
			b2[i-100] = (byte) is.read();
		}
		os.write(b2);
		is.close();
		os.close();
	}

}
