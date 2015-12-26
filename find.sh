#!/bin/sh
find $1 -type f -name "*.class" -exec /home/dim/Work/JVM/disasm/run.sh '{}' \;
