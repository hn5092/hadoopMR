package datacount;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import com.sun.tools.classfile.Opcode.Set;

public class DataCount extends Mapper<LongWritable, Text, Text, DataBean> {
			public static void main(String[] args) throws Exception {
				Configuration conf=new Configuration();
				Job job=Job.getInstance();
				job.setJarByClass(DataCount.class);
				job.setMapperClass(DCMapper.class);
//				job.setMapOutputKeyClass(Text.class);
//				job.setMapOutputValueClass(DataBean.class); //k2 k3 v2 v3一样 可以胜率
				FileInputFormat.setInputPaths(job, new Path(args[0]));
				job.setReducerClass(DCReduces.class);
				job.setOutputKeyClass(Text.class);
				job.setOutputValueClass(DataBean.class);
				FileOutputFormat.setOutputPath(job, new Path(args[1]));;
				job.setPartitionerClass(ProviderPartitioner.class);
				job.setNumReduceTasks(Integer.parseInt(args[2]));
				job.waitForCompletion(true);
				
				
			}
	public static class DCMapper extends
			Mapper<LongWritable, Text, Text, DataBean> {

		@Override
		protected void map(LongWritable key, Text value,
				Mapper<LongWritable, Text, Text, DataBean>.Context context)
				throws IOException, InterruptedException {
			String line = value.toString();
			String[] fields = line.split("\t");
			String telNo = fields[1];
			long up =Long.parseLong(fields[8]);
			long down=Long.parseLong(fields[9]);
			DataBean bean =new DataBean(telNo,up,down);
			context.write(new Text(telNo), bean);//每一次new 玩再赋值 因为被序列化了指向同一个对象没问题
		}

	}
	public static class DCReduces extends Reducer<Text, DataBean, Text, DataBean>{

		@Override
		protected void reduce(Text key, Iterable<DataBean> v2s,Context context)
				throws IOException, InterruptedException {
			long up_sum=0;
			long down_sum=0;
			for(DataBean d:v2s){
				up_sum+=d.getUpPayLoad();
				down_sum+=d.getDownPayLoad();
				
			}
			DataBean bean=new DataBean("",up_sum,down_sum);
			context.write(key,bean);
			
		}
		
		
	}
	public static class ProviderPartitioner extends Partitioner<Text, DataBean>{
		private static Map<String,Integer> providerMap=new HashMap<String,Integer>();
				static{
					providerMap.put("135", 1);
					providerMap.put("136", 1);
					providerMap.put("137", 1);
					providerMap.put("138", 1);
					providerMap.put("139", 1);
					providerMap.put("139", 1);
					providerMap.put("150", 2);
					providerMap.put("159", 2);
					providerMap.put("182", 3);
					providerMap.put("183", 3);
				}

		@Override
		public int getPartition(Text key, DataBean value, int numPartitions) {
			String account=key.toString();
			String sub_acc=account.substring(0,3);
			Integer code=providerMap.get(sub_acc);
			if(code==null){
				code=0;
			}
			return code;
		}
		
	}
}
