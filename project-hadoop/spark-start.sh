#!/bin/bash

SPARK_SBIN=$SPARK_HOME/sbin
PID=''

if [ -f $SPARK_HOME/conf/spark-env.sh  ];then
    source $SPARK_HOME/conf/spark-env.sh
else
    echo "$SPARK_HOME/conf/spark-env.sh does not exist. Can't run script."
    exit 1
fi


check_status() {

    PID=$(ps ax | grep 'org.apache.spark.deploy.master.Master' | grep java | grep -v grep | awk '{print $1}')

    if [ -n "$PID" ]
    then
    return 1
    else
    return 0
    fi

}

start() {

    check_status

    if [ "$?" -ne 0 ]
    then
    echo "Master already running"
    exit 1
    fi

    echo -n "Starting master and workers ...  "

    su user -c "$SPARK_SBIN/start-all.sh" spark  &>/dev/null

    sleep 5

    check_status

    if [ "$?" -eq 0 ]
    then
    echo "FAILURE"
    exit 1
    fi

    echo "SUCCESS"
    exit 0

}

stop() {

    check_status

    if [ "$?" -eq 0 ]
    then
    echo "No master running ..."
    return 1
    else

    echo "Stopping master and workers ..."

    su user -c "$SPARK_SBIN/stop-all.sh" spark &>/dev/null
    sleep 4

    echo "done"

    return 0
    fi
}

status() {

    check_status

    if [ "$?" -eq 0 ]
    then
    echo "No master running"
    exit 1
    else
    echo -n "master running: "
    echo $PID
    exit 0
    fi
}

case "$1" in
    start)
    start
    ;;
    stop)
    stop
    ;;
    restart)
    stop
    start
    ;;
    status)
    status
    ;;
    *)
    echo "Usage: $0 {start|stop|restart|status}"
    exit 1
esac

exit 0