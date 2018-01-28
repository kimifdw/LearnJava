# SQOOP
## import mysql data to hdfs
`sqoop --options-file /home/sjgw/sqoop/import-mysql-dev.txt --table sjes_hema_order --class-name sjes_hema_order -m 1 --split-by id --target-dir /user/hema`