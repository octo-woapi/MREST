#!/bin/bash

export HOST_IP=$(ifconfig | grep -E "([0-9]{1,3}.){3}[0-9]{1,3}" | grep -v 127.0.0.1 | awk '{ print $2 }' | cut -f2 -d: | sed -n -e 2p)

docker-compose up -d