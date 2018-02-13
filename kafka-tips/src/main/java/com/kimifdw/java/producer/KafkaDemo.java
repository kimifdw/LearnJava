package com.kimifdw.java.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class KafkaDemo {

    private final static String TOPIC = "test";

    private final static String BOOTSTRAP_SERVERS = "srv7.sanjiang.info:9092";

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                //runSyncProducer(5);
                runAsyncProducer(5);
            } else {
                runSyncProducer(Integer.parseInt(args[0]));
                runAsyncProducer(Integer.parseInt(args[0]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步消息发送
     *
     * @param sendMessageCount
     * @throws Exception
     */
    private static void runSyncProducer(final int sendMessageCount) throws Exception {
        final Producer<Long, String> producer = createProducer();
        Long time = System.currentTimeMillis();
        try {
            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(TOPIC,
                        index, "HELLO WORLD " + index);

                RecordMetadata metadata = producer.send(record).get();

                long elapsedTime = System.currentTimeMillis() - time;
                System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d,offset=%d) time=%d\n", record.key(), record.value(), metadata.partition(), metadata.offset(), elapsedTime);
            }
        } finally {
            producer.flush();
            producer.close();
        }
    }

    /**
     * 异步消费
     *
     * @param sendMessageCount
     * @throws Exception
     */
    private static void runAsyncProducer(final int sendMessageCount) throws Exception {
        final Producer<Long, String> producer = createProducer();
        final long time = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(sendMessageCount);
        try {
            for (long index = time; index < time + sendMessageCount; index++) {
                final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(TOPIC, index, "Hello New World " + index);
                producer.send(record, new Callback() {
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        long elapsedTime = System.currentTimeMillis() - time;
                        if (recordMetadata != null) {
                            System.out.printf("sent record(key=%s value=%s) " + "meta(partition=%d,offset=%d) time=%d\n",
                                    record.key(), record.value(), recordMetadata.partition(), recordMetadata.offset(), elapsedTime);
                        } else {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await(25, TimeUnit.SECONDS);
        } finally {
            producer.flush();
            producer.close();
        }
    }

    /**
     * 创建生产者
     *
     * @return
     */
    private static Producer<Long, String> createProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KAFKA_DEMO");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<Long, String>(properties);
    }
}
