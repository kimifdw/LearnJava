package com.kimifdw.java;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class Demo {

    public static void main(String[] args) {
        String url = "hdfs://srv7.sanjiang.info:9000/";
        Configuration config = new Configuration();
        try {
            // 连接hdfs
            FileSystem fs = FileSystem.get(URI.create(url), config);

            // 获取文件列表
            FileStatus[] statuses = fs.listStatus(new Path("/user/sjgw/demo/"));
            for (FileStatus status : statuses) {
                System.out.println(status);
            }

            // 写入数据
            FSDataOutputStream os = fs.create(new Path("/user/sjgw/demo/hadoop.log"));
            os.write("hello hadoop!你好,hadoop！".getBytes());
            os.flush();
            os.close();

            InputStream is = fs.open(new Path("/user/sjgw/demo/hadoop.log"));
            IOUtils.copyBytes(is, System.out, 1024, true);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }


    }
}
