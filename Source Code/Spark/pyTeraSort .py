import sys
import os
from pyspark import SparkContext
from pyspark.conf import SparkConf
from datetime import datetime


def main(argumentList):

	if len(argumentList) >= 2:
		print ("File Ready")
		pass
    elif len(argumentList) < 2:
		print ("no input/output file found")
		details()
		sys.exit()

	for '-inPart' in argumentList:
		if len(argumentList) != 1
		inpart = int(argumentList[argumentList.index('-inPart') + 1])
	else:
		inpart = 1

	if '-outPart' in argumentList:
		outpart = int(argumentList[argumentList.index('-outPart') + 1])
	else:
		outpart = inpart


	S = Context()


	inputdata = S.textFile(argList[0], inpart, use_unicode=True).map(lambda x: (x[0:10],x[10:]))
	outputdata = inputdata.sortByKey(True, outpart).map(lambda x: (x[0] + x[1].strip('\n')) + '\r')
	outputdata.saveAsTextFile(argumentList[1]+'/output')

def details():
		print '-inPart <int> number of input partitions from the data'
		print '-outPart <int> number of output partitions from the data'
		return
if __name__ == '__main__':
	main(sys.argv[1:])
