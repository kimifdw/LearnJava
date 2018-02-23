#!/usr/bin/env bash

wget http://mirror.bit.edu.cn/apache/kafka/1.0.0/kafka_2.11-1.0.0.tgz
sudo tar -xzf kafka_2.11-1.0.0.tgz -C /usr/local/

sudo chown -R sjgw:sjgw /usr/local/kafka_2.11-1.0.0
sudo echo "#KAFKA VARIABLES START
export KAFKA_HOME=/usr/local/kafka_2.11-1.0.0
#KAFKA VARIABLES END" >> .bashrc