package com.kimifdw.java.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.col;

public class demo {

    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder()
                .appName("JAVA Spark Hive Example")
                .master("spark://srv7.sanjiang.info:7077")
                // 连接hive
                .config("hive.metastore.uris", "thrift://srv7.sanjiang.info:9083")
                .enableHiveSupport()
                .getOrCreate();

        Dataset<Row> dataset = sparkSession.sql("SELECT * FROM hema_fresh_dev.sjes_hema_order");

        dataset.explain();

        dataset.selectExpr("ORDER_ID")
                .groupBy(col("ORDER_ID"));

        sparkSession.range(500).toDF().collect();


        for (String column : dataset.columns()) {
            System.out.println("columnName:" + column);
        }


        sparkSession.stop();
    }
}
