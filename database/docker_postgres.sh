#!/bin/bash
docker pull postgres &&
docker run --name postgres -e POSTGRES_PASSWORD=pass -p 5432:5432 -d postgres &&
sleep 5 &&
docker exec postgres psql -U postgres -c 'CREATE DATABASE school_notifier;'
