#!/usr/bin/env bash
file = "zookeeper-3.4.11.tar.gz"
if [ -f "$file" ]
then
	echo "$file exists."
else
  wget wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.4.11/zookeeper-3.4.11.tar.gz
fi
tar xzf zookeeper-3.4.11.tar.gz
sudo cp -R ~/zookeeper-3.4.11/* /usr/local/zookeeper
sudo chown -R sjgw:sjgw /usr/local/zookeeper
sudo echo "#ZOOKEEPER VARIABLES START
export ZOOKEEPER_HOME=/usr/local/zookeeper
export PATH=$PATH:$ZOOKEEPER_HOME/bin
#ZOOKEEPER VARIABLES END" >> ~/.bashrc

