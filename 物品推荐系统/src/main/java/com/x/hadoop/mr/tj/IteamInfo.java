package com.x.hadoop.mr.tj;

public class IteamInfo {
public String iteamId;
public int num;
public String getIteamId() {
	return iteamId;
}
public void setIteamId(String iteamId) {
	this.iteamId = iteamId;
}
public int getNum() {
	return num;
}
public void setNum(int num) {
	this.num = num;
}
public IteamInfo(String iteamId, int num) {
	super();
	this.iteamId = iteamId;
	this.num = num;
}
public IteamInfo() {
	super();
	// TODO Auto-generated constructor stub
}

}
