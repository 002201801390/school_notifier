#!/bin/bash

mvn -f ./rest_api clean install
mvn -f ./rest_api -DskipTests=true package

# Extract
##TODO
