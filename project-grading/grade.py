#! /usr/bin/python
import os
import re
import string
import sys
import subprocess



def check_results(test):
    #This is the true answer
    ref_output_file = open("./" + test + "output_ref", 'r')


    #This is the execution result
    raw_output_file = open("./" + test + "output", 'r')

    #Do some preprocessing
    raw_answer = ref_output_file.read().strip()
    raw_output = raw_output_file.read().strip()

    ref_output_file.close()
    raw_output_file.close()

    if raw_answer == raw_output:
        print 'case ' + str(test) + ': pass'
    else:
        print 'case ' + str(test) + ': fail'
        print("answer: \n")
        print(raw_answer)
        print("output: \n")
        print(raw_output)



# os.system('diff good_output' + str(case_index) +
#						' good_answer' + str(case_index))

# Run the program against all the input test cases
def test_program():
    cmd = "cd quandary-skeleton"
    subprocess.call(cmd, shell=True)
    if os.path.isfile('Makefile') or os.path.isfile('makefile'):
        cmd = "make"
        subprocess.call(cmd, shell=True)
    #sub_cmd = "./quandary"
    for test in os.listdir("../quandary-examples"):
        test_file =  "../quandary-examples/" + test
        res_file = test_file + "output"
        res_file_ref = test_file + "output_ref"
    
        cur_cmd = "./quandary  " + test_file + "  > " + res_file
        print("command: " + cur_cmd)
        os.system(cur_cmd)


        cur_cmd = "../quandary-ref/quandary  " + test_file + "  > " + res_file_ref
        print("command: " + cur_cmd)
        os.system(cur_cmd)

        # Get the info out of the output file
        check_results(test)


# Iterate over the directories
for dir_name in os.listdir("."):
    # Only consider directories
    if not os.path.isdir("./" + dir_name):
        continue
    # Ignore input and output folder
    if dir_name == "input" or dir_name == "output":
        continue
    # Dive into the directory
    os.chdir("./" + dir_name)
    # Test the program
    sys.stderr.write("Testing [" + dir_name + "]")
    test_program()
    sys.stderr.write("\n")
    # Go back to root directory
    os.chdir("..")

