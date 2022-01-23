#!/bin/bash

# Start 1 network node, then terminate it
java Main NetworkNode -ident 1 -tcpport 9000 A:1 &
sleep 1
java Main NetworkClient -gateway localhost:9000 terminate
