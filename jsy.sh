#!/usr/bin/env bash

function executable {
  return `type $1 >/dev/null 2>&1`
}

executable nodejs
if [ $? -eq 0 ]; then
  NODE=nodejs
else
  NODE=node
fi  

executable ${NODE}
if [ $? -ne 0 ]; then
  echo "Error: Requires Node.js be installed."
  exit 1
fi	

ROOT=$(cd `dirname $0` && pwd)
JSYJS="${ROOT}/node/jsy.js"

${NODE} ${JSYJS} $@
