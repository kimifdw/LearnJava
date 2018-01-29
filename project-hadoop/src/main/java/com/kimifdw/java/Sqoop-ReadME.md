# SQOOP
## import mysql data to hdfs
`sqoop --options-file /home/sjgw/sqoop/import-mysql-dev.txt --table sjes_hema_order --class-name sjes_hema_order -m 1 --split-by id --target-dir /user/hema`
## import mysql data to hive
`sqoop import --connect jdbc:mysql://srv3.sanjiang.info:3306/sjes-hema-fresh --username sjes --password sjes --table sjes_hema_order -m 1 --split-by id --fields-terminated-by ","  --hive-import --hive-overwrite --create-hive-table --hive-table sjes_hema_order --target-dir /user/hive/warehouse/dev`