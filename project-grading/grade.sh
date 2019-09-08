#!/bin/bash

do_all_tests() {
    while [ "$#" -ne 0 ]; do
      do_one_test $1 $2 $3
      shift 3
    done
    echo Total score: $SCORE out of $MAX_SCORE
}

do_one_test() {
    POINTS=$1
    PROGRAM=$2
    INPUT=$3
    echo -n "Testing $PROGRAM $INPUT, worth $POINTS points: "
    # Compare the last line if process returns nonzero code; otherwise compare last two lines
    REF_OUT=`$REF_IMPL $TESTCASE_DIR/$PROGRAM $INPUT | tail -1`
    if [ "$REF_OUT" != "Quandary process returned 0" ]; then
      SUB_OUT=`./quandary $TESTCASE_DIR/$PROGRAM $INPUT | tail -1`
    else
      REF_OUT=`$REF_IMPL $TESTCASE_DIR/$PROGRAM $INPUT | tail -2`
      SUB_OUT=`./quandary $TESTCASE_DIR/$PROGRAM $INPUT | tail -2`
    fi

    #echo REF_OUT is $REF_OUT # Enable for debugging
    #echo SUB_OUT is $SUB_OUT # Enable for debugging

    MAX_SCORE=$((MAX_SCORE + POINTS))
    if [ "$REF_OUT" == "$SUB_OUT" ]; then
      echo PASSED
      SCORE=$((SCORE + POINTS))
    else
      echo FAILED
    fi
}

if [ "$#" -ne 4 ]; then
    echo Usage: grade.sh SUBMISSION_TGZ REF_IMPL TESTCASE_LIST TESTCASE_DIR
    exit
fi

if [ -z "$JFLEX_DIR" ]; then
    echo JFLEX_DIR isn\'t set
    exit
fi
if [ -z "$CUP_DIR" ]; then
    echo CUP_DIR isn\'t set
    exit
fi

SUBMISSION_TGZ=$1

SUBMISSION_DIR=`mktemp -d -p .`

REF_IMPL=`realpath --relative-to=$SUBMISSION_DIR $2`
TESTCASE_LIST=`cat $3`
TESTCASE_DIR=`realpath --relative-to=$SUBMISSION_DIR $4`

# Extract the submitted .tgz to a new directory
echo Extracting submission to $SUBMISSION_DIR, will perform testing there
gzip -cd $SUBMISSION_TGZ | tar xf - -C $SUBMISSION_DIR

# Build the submitted project
cd $SUBMISSION_DIR
make clean
make
if [[ $? -ne 0 ]] ; then
    exit 1
fi

# Test each test case
do_all_tests $TESTCASE_LIST
