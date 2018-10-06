#! /bin/bash

docker stop demo-postgres >> /dev/null
docker rm demo-postgres >> /dev/null

docker run --name demo-postgres -p 15432:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_USER=testuser -d postgres
