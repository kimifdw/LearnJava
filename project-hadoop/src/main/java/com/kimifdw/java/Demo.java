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
      FileSystem fs = FileSystem.get(URI.create(url), config);

      FileStatus[] statuses = fs.listStatus(new Path("/demo/"));
      for (FileStatus status : statuses) {
        System.out.println(status);
      }

      FSDataOutputStream os = fs.create(new Path("/demo/hadoop.log"));
      os.write("hello hadoop!你好,hadoop！".getBytes());
      os.flush();
      os.close();

      InputStream is = fs.open(new Path("/demo/hadoop.log"));
      IOUtils.copyBytes(is, System.out, 1024, true);
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    }


  }
}
