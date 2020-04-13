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
// import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlannerGUI extends Application
{

     ArrayList<Event> events;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        events = loadEvents();     

        // Set title of GUI window
        primaryStage.setTitle("Personal Planner +");

        // Setup list of events
        ListView calendarListView = createCalendarListView();
        ScrollPane calendarScrollPane = listViewtoScrollPane(calendarListView);
        calendarListView.setOnMouseClicked(
            EventObject -> showCalendarItemDetails(( ((IndexedCell)(EventObject.getTarget())).getIndex() ))
        );

        //Create scrollable details pane
        // TextField details = new TextField();
        // ScrollPane detailsScrollPane = new ScrollPane();
        // detailsScrollPane.setContent(details);
        // detailsScrollPane.fitToWidthProperty().set(true);
        // detailsScrollPane.pannableProperty().set(true);
        
        StackPane layout = new StackPane();
        layout.getChildren().add(calendarScrollPane);

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
        primaryStage.setScene(new Scene(layout, 600, 700));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void showCalendarItemDetails(int calendarIndex){
        Event clickedEvent = events.get(calendarIndex);
        System.out.println(clickedEvent.getDetails());
    }
}
