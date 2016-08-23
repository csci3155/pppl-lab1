JSY=../../../../jsy.sh

TESTS=$(wildcard *.jsy)
ANSWERS=${TESTS:%.jsy=%.ans}

all: ${ANSWERS}
clean:
	rm -f ${ANSWERS}

%.ans: %.jsy
	${JSY} $< >$@
