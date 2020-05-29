#!/bin/bash

# Database
docker start postgres &&
  docker exec postgres psql -U postgres -d school_notifier -a -f ./database/configure_database.sql

# Deploy
./deploy.sh "$1" "$2"
