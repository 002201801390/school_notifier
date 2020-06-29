#!/bin/bash

# catalina.sh path
if [ -z "$1" ]; then
  echo "'catalina.sh' path is missing"
  exit 1
fi

# webapps path
if [ -z "$2" ]; then
  echo "'webapps' path is missing"
  exit 1
fi

$1 stop &&
mvn -f ./rest_api -DskipTests=true clean install &&
mvn -f ./rest_api -DskipTests=true package &&
rm -rf "$2"/school_notifier &&
cp ./rest_api/target/SchoolNotifier-0.0.1-SNAPSHOT.war "$2" &&
unzip -d "$2"/school_notifier "$2"/SchoolNotifier-0.0.1-SNAPSHOT.war &&
rm -rf "$2"/SchoolNotifier-0.0.1-SNAPSHOT.war &&
$1 run
