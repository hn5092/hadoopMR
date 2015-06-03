package com.x.math.dichotomy;

import javax.naming.ldap.SortControl;

public class 二分法  {
       public int serach(int key,int... i){
    	   int flag=-1;
    	   System.out.println(key);
    	   int mid =i.length/2;
    	   while(i[mid]!=key){
    		   if(i[mid]<key)
    			   mid++;
    		   if(i[mid]>key)
    			   mid--;
    		   if(i[mid]==key)
    			   flag=mid;
    		   if(mid<0||mid>(i.length-1))
    			   break;
    	   }
    	   if(i[mid]==key)
			   flag=mid;
		return  flag;
       }
        public static void main(String[] args) {
        	二分法 a=new 二分法();
			  System.out.println(a.serach(90, 0,1,5,7,15,20,24,56,77,90,123));
			  
		}
       
       
}
