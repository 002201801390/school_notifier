#!/bin/bash

# Database
docker start postgres &&

docker cp ./database/configure_database.sql postgres:/root/configure_database.sql &&
docker cp ./database/fill_database.sql postgres:/root/fill_database.sql &&

docker exec postgres psql -U postgres -d school_notifier -a -f /root/configure_database.sql &&
docker exec postgres psql -U postgres -d school_notifier -a -f /root/fill_database.sql
