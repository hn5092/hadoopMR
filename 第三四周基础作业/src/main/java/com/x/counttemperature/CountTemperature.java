package com.x.counttemperature;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CountTemperature {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(CountTemperature.class);
        
        job.setMapperClass(StepMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        
        job.setReducerClass(StepReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);

    }
      
    public static class StepMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
        Text k2 = new Text();
        IntWritable v2 = new IntWritable();
        private static final int MISSING = 9999;
        @Override
        protected void map(LongWritable key, Text value, Context context)
                throws IOException, InterruptedException {
            String line = value.toString();
            String year = line.substring(15,19);
            int tem ;
            if(line.charAt(87) == '+'){
               tem = Integer.parseInt(line.substring(88,92));
            } else {
              tem = Integer.parseInt(line.substring(87, 92));
            }
            String quality = line.substring(92, 93);
            if(tem != MISSING && quality.matches("[01459]")){
              k2.set(year);
              v2.set(tem);
              context.write(k2, v2);
            }
          
            
        }
        
    }
    
    public static class StepReducer extends Reducer<Text, IntWritable, Text, IntWritable>{


        @Override
        protected void reduce(Text k2, Iterable<IntWritable> v2,
                Reducer<Text, IntWritable, Text, IntWritable>.Context context)
                throws IOException, InterruptedException {
            int max = Integer.MIN_VALUE;
            for(IntWritable i:v2){
             max = Math.max(max, i.get());
            }
            context.write(k2, new IntWritable(max)); 
        }
        
    
        
    }
}
