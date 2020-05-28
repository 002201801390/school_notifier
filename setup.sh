#!/bin/bash

# Database
docker exec postgres psql -U postgres -d school_notifier -a -f ./database/configure_database.sql

# Deploy
./deploy.sh
