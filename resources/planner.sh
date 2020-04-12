#!/bin/bash
currentLocation=`echo "${PWD##*/}"`

if [ "$currentLocation" == "resources" ] 
then
    cd ..
    
    if [ ! -d "bin" ] # If bin directory doesn't exist...
    then
        mkdir bin
    fi
    
    javac -d bin -cp src src/*.java
    java -cp bin src.PlannerGUI

    cd resources
else
    echo "Error Running Script: Please ensure that you run this script from the resources folder of this Git repository and then try again."
fi