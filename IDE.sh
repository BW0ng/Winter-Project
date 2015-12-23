#!/bin/sh

cd ~/Winter-Project/src
`javac -d ../classes/ *.java`
`java -cp ../classes/ IDE`
