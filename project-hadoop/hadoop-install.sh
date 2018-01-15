#!/usr/bin/env bash
sudo addgroup hadoop
sudo adduser --ingroup hadoop hduser
sudo adduser hduser sudo
sudo echo "ubuntu localhost=(hduser) NOPASSWD: ALL" >> /etc/sudoers
ssh-keygen -t rsa -P ""
ssh-copy-id user@host
cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys
ssh localhost
ssh 0.0.0.0
sudo rm -rf /usr/local/hadoop
sudo mkdir /usr/local/hadoop
file = "hadoop-3.0.0.tar.gz"
if [ -f "$file" ]
then
	echo "$file exists."
else
  wget http://mirrors.hust.edu.cn/apache/hadoop/common/hadoop-3.0.0/hadoop-3.0.0.tar.gz
fi

tar xf hadoop-3.0.0.tar.gz
sudo cp -R ~/hadoop-3.0.0/* /usr/local/hadoop
sudo chown -R hduser:hadoop /usr/local/hadoop
sudo echo "#HADOOP VARIABLES START
export JAVA_HOME=/usr/lib/jvm/java-8-oracle
export HADOOP_INSTALL=/usr/local/hadoop
export PATH=$PATH:$HADOOP_INSTALL/bin
export PATH=$PATH:$HADOOP_INSTALL/sbin
export HADOOP_MAPRED_HOME=$HADOOP_INSTALL
export HADOOP_COMMON_HOME=$HADOOP_INSTALL
export HADOOP_HDFS_HOME=$HADOOP_INSTALL
export YARN_HOME=$HADOOP_INSTALL
export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_INSTALL/lib/native
export HADOOP_OPTS="-Djava.library.path=$HADOOP_INSTALL/lib"
#HADOOP VARIABLES END" >> ~/.bashrc

sudo echo "export JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /usr/local/hadoop/etc/hadoop/hadoop-env.sh
sudo rm -rf /app/hadoop/tmp
sudo mkdir -p /app/hadoop/tmp
sudo chown hduser:hadoop /app/hadoop/tmp

sudo echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<?xml-stylesheet type=\"text/xsl\" href=\"configuration.xsl\"?>
<configuration>
<property>
<name>hadoop.tmp.dir</name>
<value>/usr/local/hadoop/tmp</value>
<description>A base for other temporary directories.</description>
</property>
<property>
<name>fs.defaultFs</name>
<value>hdfs://srv7.sanjiang.info:9000</value>
<description>The name of the default file system. A URI whose
scheme and authority determine the FileSystem implementation. The
uri's scheme determines the config property (fs.SCHEME.impl) naming
the FileSystem implementation class. The uri's authority is used to
determine the host, port, etc. for a filesystem.</description>
</property>
</configuration>" > /usr/local/hadoop/etc/hadoop/core-site.xml

sudo cp /usr/local/hadoop/etc/hadoop/mapred-site.xml.template /usr/local/hadoop/etc/hadoop/mapred-site.xml

sudo echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<?xml-stylesheet type=\"text/xsl\" href=\"configuration.xsl\"?>
<configuration>
<property>
<name>mapred.job.tracker</name>
<value>srv7.sanjiang.info:9001</value>
<description>The host and port that the MapReduce job tracker runs
at. If 'local', then jobs are run in-process as a single map
and reduce task.
</description>
</property>
</configuration>" > /usr/local/hadoop/etc/hadoop/mapred-site.xml

sudo rm -rf /usr/local/hadoop_store/hdfs/namenode
sudo rm -rf /usr/local/hadoop_store/hdfs/datanode
sudo mkdir -p /usr/local/hadoop_store/hdfs/namenode
sudo mkdir -p /usr/local/hadoop_store/hdfs/datanode

sudo chown -R sjgw:sjgw /usr/local/hadoop_store


echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<?xml-stylesheet type=\"text/xsl\" href=\"configuration.xsl\"?>
<configuration>
<property>
<name>dfs.replication</name>
<value>1</value>
<description>Default block replication.
The actual number of replications can be specified when the file is created.
The default is used if replication is not specified in create time.
</description>
</property>
<property>
<name>dfs.namenode.name.dir</name>
<value>/usr/local/hadoop_store/hdfs/namenode</value>
</property>
<property>
<name>dfs.datanode.data.dir</name>
<value>/usr/local/hadoop_store/hdfs/datanode</value>
</property>
</configuration>" > /usr/local/hadoop/etc/hadoop/hdfs-site.xml

sudo echo "export PATH=$PATH:/usr/local/hadoop/bin" >> ~/.bashrc
sudo echo "export PATH=$PATH:/usr/local/hadoop/sbin" >> ~/.bashrc

sudo /usr/local/hadoop/bin/hdfs namenode -i format
#sudo /usr/local/hadoop/sbin/start-dfs.sh
su - hduser -c "/usr/local/hadoop/sbin/start-dfs.sh"
