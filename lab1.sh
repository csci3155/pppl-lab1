#!/usr/bin/env bash

SCRIPT_PATH=$(cd `dirname $0` && pwd)
cd ${SCRIPT_PATH}

sbt "runMain jsy.student.Lab1 $@"
