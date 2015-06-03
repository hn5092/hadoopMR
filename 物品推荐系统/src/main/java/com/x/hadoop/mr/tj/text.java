package com.x.hadoop.mr.tj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class text {
	public static void main(String[] args) {
		Map<String, List<IteamInfo>> mar = new HashMap<String, List<IteamInfo>>();
		List<String> iteams = new ArrayList<String>();
		String line="107;101:1,102:1,103:1,104:1,105:2,107:2";
	
		String[] mars=line.split(";");
		String iteamId1=mars[0];
		iteams.add(iteamId1);
		String[] iteamNum=mars[1].split(",");
		List<IteamInfo> infos=new ArrayList<IteamInfo>();
		for(String s:iteamNum){
			String iteamId2=s.split(":")[0];
			String time=s.split(":")[1];
			IteamInfo info =new IteamInfo(iteamId2,Integer.parseInt(time));
			infos.add(info);
		
		mar.put(iteamId1, infos);
		}
		 line="103:2.5,101:5.0,102:3.0";
		for(String s2:iteams){
			List<IteamInfo> list = mar.get(s2);
			//通过list得到每一列 然后遍历
			String[] score=line.split(",");
			Map<String,Double> map=new HashMap<String, Double>();
			for(String s:score){
				map.put(s.split(":")[0],Double.parseDouble( s.split(":")[1]));
			}
			int count=0;
			for(IteamInfo i:list){
				if(map.get(i.getIteamId())==null){
				continue;
				}else{
			count+=	map.get(i.getIteamId())*i.getNum();
				}
			}
			System.out.println(count);
			}
	}
	
}
