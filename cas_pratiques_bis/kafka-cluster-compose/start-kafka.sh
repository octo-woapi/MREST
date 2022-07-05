#!/bin/bash

export HOST_IP=$(ifconfig | awk '/inet /&&!/127.0.0.1/{print $2;exit}')

docker-compose up -d