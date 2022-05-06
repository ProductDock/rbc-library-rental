#!/bin/bash
sudo usermod -a -G docker ${USER}
docker-credential-gcr configure-docker

docker stop rbc-library-rental
docker rm rbc-library-rental
docker rmi $(docker images | grep "rbc-library-rental")

docker run -dp 8083:8080 --env-file /home/pd-library/.rental-service_env --name=rbc-library-rental gcr.io/prod-pd-library/rbc-library-rental:$1
docker container ls -a