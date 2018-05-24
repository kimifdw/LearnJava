package com.kimifdw.java.spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;

/**
 * demo
 */
public class demo {

    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder()
                .appName("JAVA Spark Hive Example")
                .master("spark://srv7.sanjiang.info:7077")
                // 连接hive
                .config("hive.metastore.uris", "thrift://srv7.sanjiang.info:9083")
                .enableHiveSupport()
                .getOrCreate();

        //SELECT floor((payable-refund)/10) area,count(*) num,sum(payable-refund) payable FROM sjes_hema_order where  order_resource='淘宝TC'  and order_type in ('主订单','主子订单') and DATE_FORMAT(create_time,'%Y-%m-%d')>='2018-05-20' and DATE_FORMAT(create_time,'%Y-%m-%d')<='2018-05-22' and sj_shop_id='sj014'  group by floor((payable-refund)/10);
        Dataset<Row> dataset = sparkSession
                .sqlContext()
                .table("hema_fresh_dev.sjes_hema_order")
                .filter(
                        col("order_type").isin("主子订单", "主订单")
                                .and(col("order_resource").equalTo("淘宝TC"))
                                .and(col("sj_shop_id").equalTo("sj014"))
                                .and(col("create_time").geq("2017-11-22 00:00:00"))
                                .and(col("create_time").leq("2018-05-03 23:59:59"))
                )
                .groupBy(floor(when(col("payable").isNull(), lit(0)).otherwise(col("payable")).minus(when(col("refund").isNull(), lit(0)).otherwise(col("refund"))).divide(10)))
                .agg(count(col("*")),
                        floor(when(col("payable").isNull(), lit(0)).otherwise(col("payable")).minus(when(col("refund").isNull(), lit(0)).otherwise(col("refund"))).divide(10)).as("num"),
                        sum(when(col("payable").isNull(), lit(0)).otherwise(col("payable")).minus(when(col("refund").isNull(), lit(0)).otherwise(col("refund")))).as("payable")
                ).toDF();

//        dataset.where();

        dataset.explain();

        dataset.show(true);

//        dataset.count()

//        Row firstRow = dataset.first();
//
//        System.out.println("record out:" + firstRow.getDecimal(0) + "," + firstRow.getLong(1) + "," + firstRow.getDecimal(3));

        sparkSession.stop();
    }
}
