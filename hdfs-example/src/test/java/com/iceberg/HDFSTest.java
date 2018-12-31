package com.iceberg;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

public class HDFSTest {

    public static final String HDFS_PATH = "hdfs://localhost:9000";

    FileSystem fileSystem = null;
    Configuration configuration = null;

    @Test
    public void mkdir() throws Exception {
        fileSystem.mkdirs(new Path("hdfsExample/test"));
    }

    @Test
    public void create() throws Exception {
        FSDataOutputStream outputStream = fileSystem.create(new Path("hdfsExample/test/a.txt"));
        outputStream.write("hello world".getBytes());
        outputStream.flush();
        outputStream.close();
    }

    @Test
    public void cat() throws Exception {
        FSDataInputStream inputStream = fileSystem.open(new Path("hdfsExample/test/a.txt"));
        IOUtils.copyBytes(inputStream, System.out, 1024);
        inputStream.close();
    }

    @Test
    public void rename() throws Exception {
        Path oldPath = new Path("hdfsExample/test/a.txt");
        Path newPath = new Path("hdfsExample/test/b.txt");

        fileSystem.rename(oldPath, newPath);
    }

    @Test
    public void copyFromLocal() throws Exception {
        Path localPath = new Path("/home/vaderwang/Desktop/kafka-canal.rar");
        Path HDFSPath = new Path("hdfsExample/test/kafka-canal.rar");

        fileSystem.copyFromLocalFile(localPath, HDFSPath);
    }

    @Test
    public void copyFromLocalWithProgress() throws Exception {
        Path localPath = new Path("/home/vaderwang/software/spark-2.3.0-bin-hadoop2.6.tgz");
        Path HDFSPath = new Path("hdfsExample/test/spark-2.3.0-bin-hadoop2.6.tgz");

        fileSystem.copyFromLocalFile(localPath, HDFSPath);

        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File("/home/vaderwang/software/spark-2.3.0-bin-hadoop2.6.tgz")));

        FSDataOutputStream outputStream = fileSystem.create(new Path("hdfsExample/test/spark-2.3.0.tgz"), new Progressable() {
            @Override
            public void progress() {
                System.out.print(".");
            }
        });

        IOUtils.copyBytes(inputStream, outputStream, 4096);
    }

    @Test
    public void copyToLocal() throws Exception {
        Path HDFSPath = new Path("hdfsExample/test/kafka-canal.rar");
        Path localPath = new Path("/home/vaderwang/Desktop/kafka-canal.rar");

        fileSystem.copyToLocalFile(HDFSPath, localPath);
    }

    @Test
    public void listFiles() throws Exception {
//        Path HDFSPath = new Path("hdfsExample/test/");
        Path HDFSPath = new Path("/software");


        FileStatus[] fileStatuses = fileSystem.listStatus(HDFSPath);

        for (FileStatus fileStatus : fileStatuses) {
            String isDir = fileStatus.isDirectory() ? "directory" : "file";
            short replication = fileStatus.getReplication();
            long len = fileStatus.getLen();
            String path = fileStatus.getPath().toString();
//            System.out.println(fileStatus);
            System.out.println(isDir + " " + replication + " " + len + " " + path);
        }
    }


    @Test
    public void delete() throws Exception {
        Path HDFSPath = new Path("hdfsExample/test/kafka-canal.rar");
        fileSystem.delete(HDFSPath, true);
    }

    @Before
    public void setUp() throws Exception{
        configuration = new Configuration();
        fileSystem = FileSystem.get(new URI(HDFS_PATH), configuration, "vaderwang");
        System.out.println("HDFS setUp");
    }

    @After
    public void tearDown() throws Exception{
        configuration = null;
        fileSystem = null;
        System.out.println("HDFS tearDown");

    }
}
