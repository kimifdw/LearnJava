package com.kimifdw.java.spark;

import org.apache.spark.sql.SparkSession;

import java.io.File;

public class demo {

    public static void main(String[] args) {
        String warehouselocation = new File("spark-warehouseLocation").getAbsolutePath();
        SparkSession sparkSession = SparkSession.builder()
                .appName("JAVA Spark Hive Example")
                .master("spark://srv7.sanjiang.info:7077")
                .config("hive.metastore.uris", "thrift://srv7.sanjiang.info:9083")
                .enableHiveSupport()
                .getOrCreate();

        sparkSession.sql("SELECT COUNT(*) FROM sjes_hema_order").show();

        sparkSession.stop();
    }
}
