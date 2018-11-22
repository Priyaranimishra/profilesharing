#! /bin/bash
echo "Stopping the service in shell"
ps -ef | grep profit-sharing-rules | grep -v grep | awk '{print $2}' | xargs -r kill
echo "Service is stopped"
echo "Verifying"
ps -ef | grep profit-sharing-rules | grep -v grep | awk '{print $2}' 