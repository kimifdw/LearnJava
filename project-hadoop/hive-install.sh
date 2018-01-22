#!/usr/bin/env bash
file = "apache-hive-2.3.2-bin.tar.gz"
if [ -f "$file" ]
then
	echo "$file exists."
else
    wget http://mirror.bit.edu.cn/apache/hive/hive-2.3.2/apache-hive-2.3.2-bin.tar.gz
fi
tar xzf apache-hive-2.3.2-bin.tar.gz
sudo mkdir /usr/local/hive
sudo cp -R ~/apache-hive-2.3.2-bin/* /usr/local/hive
sudo chown -R sjgw:sjgw /usr/local/hive
sudo echo "#HIVE VARIABLES START
export HIVE_HOME=/usr/local/hive
export PATH=$HIVE_HOME/bin:$PATH
#HIVE VARIABLES END" >> ~/.bashrc

# install apache derdy
file=""
if [ -f "$file" ]
then
    echo "$file exists."
else
    wget http://mirrors.hust.edu.cn/apache//db/derby/db-derby-10.14.1.0/db-derby-10.14.1.0-bin.tar.gz
fi

sudo echo "#DERBY VARIABLES START
export DERBY_HOME=/usr/local/db-derby-10.14.1.0-bin
export PATH=$PATH:$DERBY_HOME/bin
export CLASSPATH=$CLASSPATH:$DERBY_HOME/lib/derby.jar:$DERBY_HOME/lib/derbytools.jar
#DERBY VARIABLES END" >> ~/.bashrc

# 在后台服务启动hiveserver2
nohup hiveserver2 > hive.log &


cd /usr/local/hive/conf
cp hive-default.xml.template hive-site.xml