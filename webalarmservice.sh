#!/bin/sh
SERVICE_NAME=webalarmservice
PATH_TO_JAR=alarmservice-1.0.jar 
PID_PATH_NAME=/tmp/web_alarm-pid
echo “Starting $SERVICE_NAME ..”

if [ ! -f $PID_PATH_NAME ]; then
echo "$SERVICE_NAME start ..."
nohup java -jar $PATH_TO_JAR >> webalarmservice.out 2>&1 &
echo “$SERVICE_NAME started. Writing a PID..”
echo $! > $PID_PATH_NAME
echo “$SERVICE_NAME PID has been written.”
else
echo “$SERVICE_NAME is already running..”
PID=$(cat $PID_PATH_NAME);
echo “$SERVICE_NAME with PID = $PID stopping..”
kill $PID;
echo “$SERVICE_NAME with PID = $PID stopped..”
rm $PID_PATH_NAME;
echo “file $PID_PATH_NAME was deleted”
fi
