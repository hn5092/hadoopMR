package com.x.hadoop.hdfs;

import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HDFSDemo_HA {
			public static void main(String[] args) throws Exception {
				Configuration conf =new  Configuration();
				conf.set("dfs.nameservices", "ns1");
				conf.set("dfs.ha.namenodes.ns1","nn1,nn2");
				conf.set("dfs.namenode.rpc-address.ns1.nn1","xym01:9000");
				conf.set("dfs.namenode.rpc-address.ns1.nn2","xym02:9000");
				conf.set("dfs.client.failover.proxy.provider.ns1","org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");//实现方士
				FileSystem fs=FileSystem.get(new URI("hdfs://ns1"),conf,"root");
//				InputStream in=new FileInputStream("e://jkd1.7");
//				OutputStream out =fs.create(new Path("/jdk"));
//				InputStream in = fs.open(new Path("/test"));
//				OutputStream out =new FileOutputStream("e://12345");
//				IOUtils.copyBytes(in, out, 4096,true);
				InputStream in =null;
				in = fs.open(new Path("hdfs://ns1/native.tar.gz"));
				IOUtils.copyBytes(in, System.out, 4096,false);
				fs.close();
				System.out.println("成功");
				
			}
}
