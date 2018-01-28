#!/usr/bin/env bash
file = "sqoop-1.4.6.bin__hadoop-2.0.4-alpha"
if [ -f "$file" ]
then
	echo "$file exists."
else
  wget http://mirror.bit.edu.cn/apache/sqoop/1.4.6/sqoop-1.4.6.bin__hadoop-2.0.4-alpha.tar.gz
fi
sudo tar xzf sqoop-1.4.6.bin__hadoop-2.0.4-alpha -C /usr/local
sudo chown -R sjgw:sjgw /usr/local/sqoop-1.4.6.bin__hadoop-2.0.4-alpha
sudo echo "#SQOOP VARIABLES START
export SQOOP_HOME=/usr/local/sqoop-1.4.6.bin__hadoop-2.0.4-alpha
export PATH=$SQOOP_HOME/bin:$PATH
#SQOOP VARIABLES END" >> ~/.bashrc