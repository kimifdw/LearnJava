package com.kimifdw.java.consumer.lowlevel;

public class KafkaBrokerInfo {

    private String brokerHost;

    private int brokerPort;

    public String getBrokerHost() {
        return brokerHost;
    }

    public void setBrokerHost(String brokerHost) {
        this.brokerHost = brokerHost;
    }

    public int getBrokerPort() {
        return brokerPort;
    }

    public void setBrokerPort(int brokerPort) {
        this.brokerPort = brokerPort;
    }

    public KafkaBrokerInfo(String brokerHost, int brokerPort) {
        this.brokerHost = brokerHost;
        this.brokerPort = brokerPort;
    }

    public KafkaBrokerInfo(String brokerHost) {
        this(brokerHost, 9092);
    }
}
