#!/bin/bash

# Start 1 network node, then allocate the resources using it
java Main NetworkNode -ident 1 -tcpport 9000 A:1 &
sleep 1
java Main NetworkNode -ident 1 -tcpport 9001 -gateway localhost:9000 A:1 &
sleep 1
java Main NetworkNode -ident 1 -tcpport 9002 -gateway localhost:9001 A:1 &
sleep 1
java Main NetworkClient -gateway localhost:9001 terminate
