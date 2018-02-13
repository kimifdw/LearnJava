package com.kimifdw.java.consumer;

import com.kimifdw.java.consumer.lowlevel.KafkaBrokerInfo;
import com.kimifdw.java.consumer.lowlevel.KafkaSimpleConsumerAPI;
import com.kimifdw.java.consumer.lowlevel.KafkaTopicPartitionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * kafka低级api消费
 */
public class KafkaLowLevelDemo {

    public static void main(String[] args) {
        KafkaSimpleConsumerAPI kafkaSimpleConsumerAPI = new KafkaSimpleConsumerAPI();
        long maxReads = 200;
        String topic = "test";
        int partitionID = 0;

        KafkaTopicPartitionInfo kafkaTopicPartitionInfo = new KafkaTopicPartitionInfo(topic, partitionID);
        List<KafkaBrokerInfo> kafkaBrokerInfos = new ArrayList<>();
        kafkaBrokerInfos.add(new KafkaBrokerInfo("srv7.sanjiang.info"));

        try {
            kafkaSimpleConsumerAPI.run(maxReads, kafkaTopicPartitionInfo, kafkaBrokerInfos);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println(KafkaSimpleConsumerAPI.fetchTopicPartitionIDs(kafkaBrokerInfos, topic, 100000, 6 * 1024, "client-id"));
    }
}
