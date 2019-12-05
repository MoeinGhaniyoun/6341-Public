#!/bin/bash

javac Counter.java

while true
do
  timeout -v 3 java Counter
done
