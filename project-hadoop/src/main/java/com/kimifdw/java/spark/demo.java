package com.kimifdw.java.spark;

import org.apache.spark.sql.SparkSession;

public class demo {

    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder()
                .appName("JAVA Spark Hive Example")
                .master("spark://srv7.sanjiang.info:7077")
                // 连接hive
                .config("hive.metastore.uris", "thrift://srv7.sanjiang.info:9083")
                .enableHiveSupport()
                .getOrCreate();

        sparkSession.sql("SELECT * FROM hema_fresh_dev.sjes_hema_order where id=4815680").show();

        sparkSession.stop();
    }
}
