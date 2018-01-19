package com.kimifdw.java;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

/**
 * ListStatus：查看HDFS中文件或者目录的元信息
 */
public class ListStatusDemo {

    public static void main(String[] args) throws IOException {
        String uri = "hdfs://srv7.sanjiang.info:9000/";
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(URI.create(uri), conf);

        Path[] paths = new Path[1];
        paths[0] = new Path(uri);
        FileStatus[] status = fs.listStatus(paths);
        Path[] listPaths = FileUtil.stat2Paths(status);
        for (Path listPath : listPaths) {
            System.out.println(listPath);
            fs.delete(listPath, true);
        }

    }
}
