package com.iceberg;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

public class WordCountPartitioner {

    public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable>{

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();

            String[] info = line.split(" ");

            context.write(new Text(info[0]), new LongWritable(Long.parseLong(info[1])));
        }
    }

    public static class MyReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        @Override
        protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

            long sum = 0;

            for (LongWritable value : values) {
                sum += value.get();
            }
            context.write(key, new LongWritable(sum));
        }
    }


    public static class MyPartitioner extends Partitioner<Text, LongWritable> {

        @Override
        public int getPartition(Text text, LongWritable longWritable, int i) {
            if (text.toString().equals("xiaomi")) {
                return 0;
            } else if (text.toString().equals("iphone")) {
                return 1;
            } else if (text.toString().equals("huawei")) {
                return 2;
            }
            return 3;
        }
    }

    public static void main(String[] args) throws Exception {

        //创建Configuration
        Configuration configuration = new Configuration();

        // 准备清理已存在的输出目录
        Path outputPath = new Path(args[1]);
        FileSystem fileSystem = FileSystem.get(configuration);
        if(fileSystem.exists(outputPath)){
            fileSystem.delete(outputPath, true);
            System.out.println("output file exists, has deleted");
        }

        //创建Job
        Job job = Job.getInstance(configuration, "WordCount");

        //设置job的处理类
        job.setJarByClass(WordCountPartitioner.class);

        //设置作业处理的输入路径
        // hdfs://localhost:9000/hello.txt
        // /home/vaderwang/hello.txt
        FileInputFormat.setInputPaths(job, new Path(args[0]));

        //设置map相关参数
        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        //设置reduce相关参数
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        //设置job的partition
        job.setPartitionerClass(MyPartitioner.class);

        //设置4个reducer，每个分区一个
        job.setNumReduceTasks(4);
        //设置作业处理的输出路径
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
