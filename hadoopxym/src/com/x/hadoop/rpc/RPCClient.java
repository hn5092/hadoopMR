package com.x.hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class RPCClient {
		public static void main(String[] args) throws IOException {
			Bizable proxy = (Bizable) RPC.getProxy(Bizable.class, 10, new InetSocketAddress("192.168.199.161",9527), new Configuration());
			String sysHi = proxy.sysHi("jack");
			System.out.println(sysHi);
			RPC.stopProxy(proxy);
		}
}
