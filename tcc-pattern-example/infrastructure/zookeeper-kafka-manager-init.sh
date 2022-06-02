#!/bin/bash
/apache-zookeeper-3.6.3-bin/bin/zkCli.sh -server zookeeper:2181 <<EOF
create /kafka-manager/mutex ""
create /kafka-manager/mutex/locks ""
create /kafka-manager/mutex/leases ""
quit
EOF


