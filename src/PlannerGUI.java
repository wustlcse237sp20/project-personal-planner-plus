package src;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 
import javafx.scene.control.Button;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.input.KeyEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlannerGUI extends Application
{
    ArrayList<Event> events;
    boolean showDetails = true;
    boolean freezeCursor = true;
    int calendarItem_lastChange =-1;
    final String baseDetailMode = "Detail Mode ";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        events = loadEvents();     

        // Create details label
        Label calendarItemDetails = new Label(baseDetailMode + "OFF");

        // Setup list of events
        ListView calendarListView = createCalendarListView();
        ScrollPane calendarScrollPane = listViewtoScrollPane(calendarListView);

        // Setup two listeners on the calendarItems: the first accounts for the arrow keys, the second for the double-click
        calendarListView.getSelectionModel().selectedIndexProperty().addListener(new InvalidationListener() {
        @Override
            public void invalidated(Observable observable) {
                showCalendarItemDetailsChange(((ReadOnlyIntegerProperty)observable).getValue(), calendarItemDetails, calendarListView);
            }
        });
        calendarListView.setOnMouseClicked(
            EventObject -> showCalendarItemDetailsClick( ((IndexedCell)(EventObject.getTarget())).getIndex(), calendarItemDetails, calendarListView)
        );

        // Create layout, add items to it, create a scene object w listeners, display the layout
        BorderPane layout = new BorderPane();
        Scene scene = new Scene(layout);  
          scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case BACK_SPACE:  
                        int currIndex = calendarListView.getSelectionModel().selectedIndexProperty().getValue();
                        if(currIndex > -1){
                            calendarListView.getItems().remove(currIndex);
                            events.remove(currIndex);
                            System.out.println("del");
                        }
                    break;
                }
            }
        });
        layout.setTop(calendarScrollPane);
        layout.setBottom(calendarItemDetails);
        showLayout(primaryStage, layout, scene);
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

    private void showLayout(Stage primaryStage, BorderPane layout, Scene scene){
        primaryStage.setTitle("Personal Planner +");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void showCalendarItemDetailsChange(int calendarIndex_Change, Label calendarItemDetails, ListView calendarListView){
        if(calendarIndex_Change >= 0) {
            Event clickedEvent = events.get(calendarIndex_Change);
            calendarItemDetails.setText(baseDetailMode+ "ON: " + clickedEvent.getDetails());
            calendarItem_lastChange = calendarIndex_Change;
            showDetails = true;
            freezeCursor = true;
        }
    }

     private void showCalendarItemDetailsClick(int calendarIndexClick, Label calendarItemDetails, ListView calendarListView){
        if(calendarItem_lastChange  == calendarIndexClick && !freezeCursor){ 
            showDetails = !showDetails;
            if(showDetails){
                Event clickedEvent = events.get(calendarIndexClick);
                calendarItemDetails.setText(baseDetailMode+ "ON: " + clickedEvent.getDetails());
            }
            else{
                    calendarListView.getSelectionModel().clearSelection();
                    calendarItemDetails.setText(baseDetailMode +  "OFF");
            }
        }
        else{
            freezeCursor = false;
        }
    }
}