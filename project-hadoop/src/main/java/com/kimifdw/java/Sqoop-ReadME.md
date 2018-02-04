# SQOOP
## import mysql data to hdfs
`sqoop --options-file /home/sjgw/sqoop/import-mysql-dev.txt --table sjes_hema_order --class-name sjes_hema_order -m 1 --split-by id --target-dir /user/hema`
## import mysql data to hive
`sqoop import --connect jdbc:mysql://srv3.sanjiang.info:3306/sjes-hema-fresh --username sjes --password sjes --table sjes_hema_order -m 1 --split-by id --fields-terminated-by ","  --hive-import --hive-overwrite --create-hive-table --hive-table sjes_hema_order --target-dir /user/hive/warehouse/dev`
## create job
`sqoop job --create txdDevUpdateJob -- import --connect jdbc:mysql://srv3.sanjiang.info:3306/sjes-hema-fresh --table sjes_hema_order -m 1 --split-by id --fields-terminated-by "," --hive-import  --hive-table hema_fresh_dev.sjes_hema_order --incremental lastmodified --check-column create_time --last-value "2018-01-14" --target-dir /user/hive/warehouse/dev`