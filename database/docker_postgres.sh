#!/bin/bash
docker pull postgres && \

mkdir -p "$HOME"/docker/volumes/postgres && \
docker run --name postgres -e POSTGRES_PASSWORD=pass -d -p 5432:5432 -v "$HOME"/docker/volumes/postgres:/var/lib/postgresql/data  postgres && \

docker exec postgres psql -U postgres -c 'CREATE DATABASE school_notifier;' && \

docker exec -it postgres psql -U postgres -d school_notifier
