#!/bin/bash

sudo yum install java-devel
sudo mdadm --create --verbose /dev/md0 --level=0 --name=createraid --raid-devices=3 /dev/xvdb /dev/xvdc /dev/xvdd
sudo mkfs.ext4 -L createraid /dev/md0
sudo mkdir -p /mnt/raid
sudo mount LABEL=createraid /mnt/raid

sudo mkdir /mnt/raid/temporary_dir

sudo wget  "https://archive.apache.org/dist/hadoop/core/hadoop-2.7.2/hadoop-2.7.2.tar.gz"

sudo tar  -zxf  hadoop-2.7.2.tar.gz

sudo mv hadoop-2.7.2 hadoop

sudo mv hadoop /usr/local/


