package com.kimifdw.java.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class KafkaHighLevelDemo {

    private final static String TOPIC = "test";

    private final static String BOOTSTRAP_SERVERS = "srv7.sanjiang.info:9092";


    public static void main(String[] args) {
        runHighConsumer();
    }

    /**
     * 消费记录
     */
    private static void runHighConsumer() {
        final Consumer<Long, String> consumer = createConsumer();
        final int giveUp = 100;
        int noRecordsCount = 0;

        while (true) {
            final ConsumerRecords<Long, String> consumerRecords = consumer.poll(10);
            if (consumerRecords.isEmpty()) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) {
                    break;
                } else {
                    continue;
                }
            }

            for (ConsumerRecord<Long, String> consumerRecord : consumerRecords) {
                System.out.printf("Consumber Record:(%d,%s,%d,%d)\n",
                        consumerRecord.key(), consumerRecord.value(), consumerRecord.partition(), consumerRecord.offset());
            }

            consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {

                }
            });
        }

        consumer.close();
        System.out.println("DONE");
    }


    /**
     * 创建消费者
     *
     * @return
     */
    private static Consumer<Long, String> createConsumer() {
        final Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 设置最大的读取量
//        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);

        final Consumer<Long, String> consumer = new KafkaConsumer<Long, String>(properties);

        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }
}
