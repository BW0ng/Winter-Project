#!/bin/sh

cd ~/Winter-Project/
javac -d classes/ -cp "lib/pty4j-0.5.jar:lib/guava-14.0.1.jar:lib/jna.jar:lib/jsch-0.151.jar:lib/junit-4.10.jar:lib/jzlib-1.1.1.jar:lib/log4j-1.2.14.jar:lib/purejavacomm-0.0.17.jar:lib/jediterm-pty-2.0.jar" src/*.java

java -cp ".:lib/pty4j-0.5.jar:lib/guava-14.0.1.jar:lib/jna.jar:lib/jsch-0.151.jar:lib/junit-4.10.jar:lib/jzlib-1.1.1.jar:lib/log4j-1.2.14.jar:lib/purejavacomm-0.0.17.jar:lib/jediterm-pty-2.0.jar:classes/" IDE
