package src;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.scene.control.Button;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlannerGUI extends Application
{

    ArrayList<Event> events;
    int calendarItem_LastClicked =-1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        events = loadEvents();     

        //Create details label
        Label calendarItemDetails = new Label("test");

        // Setup list of events
        ListView calendarListView = createCalendarListView();
        ScrollPane calendarScrollPane = listViewtoScrollPane(calendarListView);
        calendarListView.setOnMouseClicked(
            EventObject -> showCalendarItemDetails( ((IndexedCell)(EventObject.getTarget())).getIndex(), calendarItemDetails )
        );

        // Create layout, add items to it, display it
        StackPane layout = new StackPane();
        layout.getChildren().addAll(calendarScrollPane, calendarItemDetails);
        showLayout(primaryStage, layout);
    }

    public ArrayList<Event> loadEvents(){
        // @TODO: Replace dummy data on backend integration
        ArrayList<Event> events = new ArrayList<Event>();
        for(int i = 0; i < 50; i++){
            Event newEvent = new Event("Test" + String.valueOf(i), LocalDateTime.now(), LocalDateTime.now().plusHours(1), new ArrayList<String>(), "dummyDetails" + String.valueOf(i), 0);
            events.add(newEvent);
        }
        return events;
    }

    private ListView createCalendarListView(){
        ListView calendarListView = new ListView();
        for (Event event:events){
            calendarListView.getItems().add(event.toString());           
        }
        return calendarListView;
    }

    private ScrollPane listViewtoScrollPane(ListView calendarListView){
        ScrollPane calendarScrollPane = new ScrollPane();
        calendarScrollPane.setContent(calendarListView);
        calendarScrollPane.fitToWidthProperty().set(true);
        calendarScrollPane.pannableProperty().set(true);
        return calendarScrollPane;
    }

    private void showLayout(Stage primaryStage, StackPane layout){
        primaryStage.setTitle("Personal Planner +");
        primaryStage.setScene(new Scene(layout, 600, 700));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void showCalendarItemDetails(int calendarIndex, Label calendarItemDetails){
        if(calendarIndex != calendarItem_LastClicked){
            Event clickedEvent = events.get(calendarIndex);
            calendarItemDetails.setText(clickedEvent.getDetails());
            calendarItem_LastClicked = calendarIndex;
        }
        else{
            calendarItemDetails.setText("");
            calendarItem_LastClicked = -1;
        }
    }
}
