#!/bin/sh

cd src
`javac -d ../classes/ *.java`
`java -cp ../classes/ IDE`
