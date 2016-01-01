#!/bin/sh
cd ~/Winter-Project/
javac -d classes/ src/*.java

java -cp classes/ IDE
