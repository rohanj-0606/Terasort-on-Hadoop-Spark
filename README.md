# Terasort-on-Hadoop-Spark

This Programming Assignment involves implementing the Sort Application using 3 different approaches:
1) Shared Memory Sort.
2) Apache Hadoop.
3) Apache Spark.

The Assignment Directory contains following documents and folders:

1. Source Code of the program for Terasort on Hadoop, Spark adn Shared Memory - Source Code
2. Performance Evaluation Report - prog2_report.pdf
3. Snapshots of outputs running on Amazon AWS- Snapshots
4. Configuration files of Hadoop and Spark - Config files

STEPS FOR EXECUTION: 

SHARED MEMORY: 

1) Navigate to the Folder which contains the Source Code. 

2) Once landed in the Folder, execute the accompanying Commands: 

Gathering: javac SharedMemoryTera.java 

Execution: java SharedMemoryTera 

So as to execute the Module on AWS, play out the accompanying advances: 

1) Go to Amazon Web Services (AWS). 

2) Launch an AWS Instance and pick "Linux Ubuntu AMI". 

3) Perform the Compilation and Execution Commands as expressed previously. 


APACHE HADOOP: 

1) First of all, we need to introduce Apache Hadoop by executing the Script. 

2) Once Apache Hadoop is introduced effectively, play out the accompanying advances: 

I) Execute "gensort". 

ii) Execute "TeraByteSorting.java". 

iii) Execute "valsort". 


APACHE SPARK: 

1) First of all, we need to introduce Apache Spark by executing the Bash Script. 

2) The Bash Script will introduce Apache Spark on the Amazon Cluster. 

3) Once Apache Spark is introduced effectively, play out the accompanying advances: 

I) Execute "gensort" and take the "input" document. 

ii) Transfer File where the information is arranged for the gensort 

iii) Execute "pyTeraSort.py". 

iv) Execute "valsort".
