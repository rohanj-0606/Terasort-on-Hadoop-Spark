#!/bin/bash


eval `ssh-agent -s`

ssh-add Parth.pem

sudo mv slaves /usr/local/hadoop/etc/hadoop/

sudo mdadm --create --verbose /dev/md0 --level=0 --name=createraid --raid-devices=3 /dev/xvdb /dev/xvdc /dev/xvdd
sudo mkfs.ext4 -L createraid /dev/md0
sudo mkdir -p /mnt/raid
sudo mount LABEL=createraid /mnt/raid

sudo mkdir /mnt/raid/temporary_dir



