#!/bin/sh

jarfile="./ConsoleCalculator.jar"

if [[ -s "$jarfile" ]]; then
    java -jar $jarfile
else
    echo "Error! $jarfile not found! Make sure you have compiled the project with 'ant jar' first!"
fi
