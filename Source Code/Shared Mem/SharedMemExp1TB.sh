#!/bin/bash

echo "The Shared Memory Tera Sort Experiments is running for 1 TB of input data "
echo ""

echo "The Shared Memory Tera Experiment is running with 1 Thread"
java -Xms256m -Xmx28672m SharedMemTera 1 input.txt
rm output.txt
echo ""

echo "The Shared Memory Tera Experiment is running with 2 Thread"
java -Xms256m -Xmx28672m SharedMemTera 2 input.txt
rm output.txt
echo ""

echo "The Shared Memory Tera Experiment is running with 3 Thread"
java -Xms256m -Xmx28672m SharedMemTera 3 input.txt
rm output.txt
echo ""

echo "The Shared Memory Tera Experiment is running with 4 Thread"
java -Xms256m -Xmx28672m SharedMemTera 4 input.txt
rm output.txt
echo ""

echo "The Shared Memory Tera Experiment is running with 5 Thread"
java -Xms256m -Xmx28672m SharedMemTera 5 input.txt
rm output.txt
echo ""

echo "The Shared Memory Tera Experiment is running with 6 Thread"
java -Xms256m -Xmx28672m SharedMemTera 6 input.txt
rm output.txt
echo ""

echo "The Shared Memory Tera Experiment is running with 7 Thread"
java -Xms256m -Xmx28672m SharedMemTera 7 input.txt
rm output.txt
echo""

echo "The Shared Memory Tera Experiment is running with 8 Thread"
java -Xms256m -Xmx28672m SharedMemTera 8 input.txt
