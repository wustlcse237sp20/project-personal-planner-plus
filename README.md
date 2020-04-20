# Personal-Planner-Plus

A Java-based personal planner that allows its user to manage their tasks and deadlines

For more information, please refer to the [Project Pitch](https://docs.google.com/presentation/d/1Cowe3ziwn9F5T3Z5tuv8b_cD5_qyVIpTpGVDr7sPgRk/edit#slide=id.gd814cf7d3_0_5)

## Getting Started

To Run:
- Clone or download this Git repository to your local machine
    - ```git clone https://github.com/wustlcse237sp20/project-personal-planner-plus.git```
- Open command line, and navigate to the repository
    - ```cd project-personal-planner-plus```
- Run the following commands:
    - ```mkdir bin```
    - ```javac -d bin -cp src src/*.java```
    - ```java -cp bin src.PlannerGUI```
    - Note: On Mac, you may receive [an error message](./Resources/Error_Message.png), which can be disregarded

Events can be added by pressing the "a" key and deleted by selecting and pressing backspace. (Note: the display does not currently refresh after adding an event; to see an event after it's been added, exit and re-run the program.

## What user stories were completed this iteration?
- Delete events
- Persist data

## What user stories do you intend to complete next iteration?
- Add event
- Edit events

## Is there anything that you implemented but doesn't currently work?

The "Add Event" UI (and integration with backend) was implemented but the display does not refresh upon the event being added, so the feature still has some work to do.

## Resources

[JavaFX](https://docs.oracle.com/javafx/2/get_started/jfxpub-get_started.htm)

