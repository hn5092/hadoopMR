package com.x.rm;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

public class InfoBean implements WritableComparable<InfoBean>{

		private String acount;
		private double income;
		private double expenses;
		private double surplus;
	public void write(DataOutput out) throws IOException {
			out.writeUTF(acount);
			out.writeDouble(income);
			out.writeDouble(expenses);
			out.writeDouble(surplus);
		
	}
	public void set(String acount,double income,double expense){
		this.acount=acount;
		this.income=income;
		this.expenses=expense;
		this.surplus=income-expense;
	}
	public void readFields(DataInput in) throws IOException {
		this.acount=in.readUTF();
		this.income=in.readDouble();
		this.expenses=in.readDouble();
		this.surplus=in.readDouble();
	}


	public int compareTo(InfoBean o) {
		if(this.income==o.getIncome()){
			return this.expenses>o.getExpenses()?1:-1;
		}else{
			return this.income>o.getIncome()?-1:1;
		}
	}

	public String getAcount() {
		return acount;
	}

	public void setAcount(String acount) {
		this.acount = acount;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpenses() {
		return expenses;
	}

	public void setExpenses(double expenses) {
		this.expenses = expenses;
	}

	public double getSurplus() {
		return surplus;
	}

	public void setSurplus(double surplus) {
		this.surplus = surplus;
	}

	@Override
	public String toString() {
		return this.income + "\t" + this.expenses + "\t" + this.surplus;
	}

}
