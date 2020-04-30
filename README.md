# Personal-Planner-Plus

A Java-based personal planner that allows users to manage their tasks and deadlines

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

## Instructions
- Events are deleted by selecting and pressing backspace

## What user stories were completed this iteration?
- Ability to view Event Details
- Ability to create new events
- Ability to edit events
- Ability to delete events

## What user stories do you intend to complete next iteration?
- Search Events by title
- Create event tags and filter events by tag
- View events by month

## Is there anything that you implemented but doesn't currently work?

- We currently have a bug that occurs when events list in the GUI is not filled and the user clicks a part of the list that is not an event.  This causes an error on the console.

## Resources

[JavaFX](https://docs.oracle.com/javafx/2/get_started/jfxpub-get_started.htm)

