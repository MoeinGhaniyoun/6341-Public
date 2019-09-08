#! /usr/bin/python
import os
import re
import string
import sys
import subprocess

# Iterate over the directories
for afile in os.listdir("."):
	zipfile = afile.replace(" ", "_")

	cmd = "mv \"" + afile + "\""  + " " + zipfile
	print("cmd : " + cmd)
	subprocess.call(cmd, shell=True)

	print("  zipfile name is " + zipfile)
	namelen = len(zipfile)

	postfix = zipfile[namelen-3:]

	print("postfix is " + postfix)
	if postfix == "zip":
		foldername = zipfile[:namelen-4]
		#print(" folder name: " + foldername)

		cmd = "mkdir " + foldername
		#print(" dir cmd: " + cmd)
		subprocess.call(cmd, shell=True)

		cmd = "unzip " + zipfile + " -d ./" + foldername
		#print("unzip cmd:" + cmd)
		subprocess.call(cmd, shell=True)

		cmd = " rm " + zipfile
		subprocess.call(cmd, shell=True)

	elif postfix == "tar" or postfix == "tgz":
		foldername = zipfile[:namelen-4]
		#print(" folder name: " + foldername)

		cmd = "mkdir " + foldername
		#print(" dir cmd: " + cmd)
		subprocess.call(cmd, shell=True)

		cmd = "tar -zvxf " + zipfile + " -C ./" + foldername
		#print("unzip cmd:" + cmd)
		subprocess.call(cmd, shell=True)

		cmd = " rm " + zipfile
		subprocess.call(cmd, shell=True)

	else:
		continue

