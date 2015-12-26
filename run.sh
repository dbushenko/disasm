#!/bin/sh
java -cp /home/dim/Work/JVM/disasm/target/classes/:/home/dim/Work/JVM/disasm/gson-2.5.jar com.sun.tools.javap.Main $1 >> $1.json
