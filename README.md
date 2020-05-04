# Personal-Planner-Plus

A Java-based personal planner that allows users to manage their tasks and deadlines

For more information, please refer to the [Project Pitch](https://docs.google.com/presentation/d/1Cowe3ziwn9F5T3Z5tuv8b_cD5_qyVIpTpGVDr7sPgRk/edit#slide=id.gd814cf7d3_0_5)

## Getting Started

- Please Note: JavaFX is not included on all JavaJDK releases; this program is only compatible with Java versions 8 - 10 
- Clone or download this Git repository to your local machine
    - ```git clone https://github.com/wustlcse237sp20/project-personal-planner-plus.git```
- Open command line, and navigate to the repository
    - ```cd project-personal-planner-plus```
- Run the following commands:
    - ```mkdir bin```
    - ```javac -d bin -cp src src/*.java```
    - ```java -cp bin src.PlannerGUI```
    - Note: On Mac, you may receive an error message from System Preferences, which can be disregarded
- The app will then launch. Note that:
    - Events are deleted by selecting and pressing backspace
    - Times must be entered in a "HH:mm" format, with a 24 hour clock
    - Events need to have a name, start date/time, and end date/time; the tags are comma-separated, and the tags and description are both optional
    - The bar that is selected when the program opens is the search bar; it must be clear to ensure all events are being displayed

## What user stories were completed this iteration?
- Search Events by title
- Ability to edit events
- Create event tags and filter events by tag


## Is there anything that you implemented but doesn't currently work?

N/A


## Resources

[JavaFX](https://docs.oracle.com/javase/8/javase-clienttechnologies.htm)

