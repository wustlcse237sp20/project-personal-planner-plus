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
        events = new ArrayList<Event>();
        // @TODO: Replace dummy data on backend integration
        for(int i = 0; i < 50; i++){
            Event newEvent = new Event("Test" + String.valueOf(i), LocalDateTime.now(), LocalDateTime.now().plusHours(1), new ArrayList<String>(), "dummyDetails" + String.valueOf(i), 0);
            events.add(newEvent);
        }
        primaryStage.setTitle("Personal Planner +");

        ListView eventsList = createListView();
        ScrollPane scrollPane = createScrollPaneWithEvents(eventsList);

        eventsList.setOnMouseClicked(
            EventTarget -> showCalendarItemDetails(EventTarget.getTarget().toString()) //.IndexedCell.getIndex().toString()
        );
        
        showRootElement(primaryStage, scrollPane);
    }

    private ListView createListView(){
        ListView eventsList = new ListView();
        for (Event event:events){
            eventsList.getItems().add(event.toString());           
        }
        return eventsList;
    }

    private ScrollPane createScrollPaneWithEvents(ListView eventsList){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(eventsList);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.pannableProperty().set(true);
        return scrollPane;
    }

    private void showRootElement(Stage primaryStage, ScrollPane child){
        StackPane root = new StackPane();
        root.getChildren().add(child);
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void showCalendarItemDetails(String s){
        System.out.println(s);
    }
}
