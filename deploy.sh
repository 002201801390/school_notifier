#!/bin/bash

# catalina.sh path
# $1
# webapps path
# $2

$1 stop &&
mvn -f ./rest_api -DskipTests=true clean install &&
mvn -f ./rest_api -DskipTests=true package &&
rm -rf "$2"/school_notifier &&
unzip -d "$2"/school_notifier "$2"/SchoolNotifier-0.0.1-SNAPSHOT.war &&
rm -rf "$2"/SchoolNotifier-0.0.1-SNAPSHOT.war &&
$1 run
