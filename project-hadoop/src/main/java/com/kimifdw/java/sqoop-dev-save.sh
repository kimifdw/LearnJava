#!/usr/bin/env bash
yesterday=`date -d last-day +%Y-%m-%d`
echo "delete job"
$SQOOP_HOME/bin/sqoop job --delete txdDevUpdateJob
echo "create job"
$SQOOP_HOME/bin/sqoop job --create txdDevUpdateJob -- import --connect jdbc:mysql://srv3.sanjiang.info:3306/sjes-hema-fresh --table sjes_hema_order -m 1 --split-by id --fields-terminated-by "," --hive-import  --hive-table hema_fresh_dev.sjes_hema_order --incremental lastmodified --check-column create_time --last-value "${yesterday}" --target-dir /user/hive/warehouse/dev &> sqoop_${yesterday}.log

echo "lastmodified job start"
$SQOOP_HOME/bin/sqoop job --exec txdDevUpdateJob -- --username sjes -P