package com.x.mr.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

public class ExampleTotalMapReduce{
    public static void main(String[] args) {
        try{
            Configuration config = HBaseConfiguration.create();
            Job job = new Job(config,"ExampleSummary");
            job.setJarByClass(ExampleTotalMapReduce.class);     // class that contains mapper and reducer

            Scan scan = new Scan();
            scan.setCaching(500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
            scan.setCacheBlocks(false);  // don't set to true for MR jobs
            // set other scan attrs
            //scan.addColumn(family, qualifier);
            TableMapReduceUtil.initTableMapperJob(
                    "access-log",        // input table
                    scan,               // Scan instance to control CF and attribute selection
                    MyMapper.class,     // mapper class
                    Text.class,         // mapper output key
                    IntWritable.class,  // mapper output value
                    job);
            TableMapReduceUtil.initTableReducerJob(
                    "total-access",        // output table
                    MyTableReducer.class,    // reducer class
                    job);
            job.setNumReduceTasks(1);   // at least one, adjust as required

            boolean b = job.waitForCompletion(true);
            if (!b) {
                throw new IOException("error with job!");
            } 
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static class MyMapper extends TableMapper<Text, IntWritable>  {

        private final IntWritable ONE = new IntWritable(1);
        private Text text = new Text();

        public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
            String ip = Bytes.toString(row.get()).split("-")[0];
            String url = new String(value.getValue(Bytes.toBytes("info"), Bytes.toBytes("url")));
            text.set(ip+"&"+url);
            context.write(text, ONE);
        }
    }

    public static class MyTableReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable>  {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }

            Put put = new Put(key.getBytes());
            put.add(Bytes.toBytes("info"), Bytes.toBytes("count"), Bytes.toBytes(String.valueOf(sum)));

            context.write(null, put);
        }
    }
}