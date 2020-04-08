#!/bin/bash
cd ..
mkdir bin
javac -d bin -cp src src/*.java
java -cp bin src.PlannerGUI