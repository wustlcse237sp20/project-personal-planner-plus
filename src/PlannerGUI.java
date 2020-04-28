package src;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlannerGUI extends Application
{
    List<Event> events;
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
        loadEvents();     

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
                            showCalendarItemDetailsChange(Math.max(currIndex -1, 0), calendarItemDetails, calendarListView);
                            System.out.println("del");
                        }
                    case A:
                    	showAddEvent();
                    break;
                }
            }
        });
        layout.setTop(calendarScrollPane);
        layout.setBottom(calendarItemDetails);
        showLayout(primaryStage, layout, scene);

        ComboBox tagBox = new ComboBox();
        tagBox.getItems().add("all");
        for (String tag : Planner.getTagSet()){
            tagBox.getItems().add(tag);
        }
    }

    public void loadEvents(){
        Planner.initializeVars();
        Planner.loadData();
        events = Planner.getEvents();
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
            calendarItemDetails.setText(clickedEvent.getDetails());
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

    private void showAddEvent() {
//    	https://www.quickprogrammingtips.com/java/how-to-open-a-new-window-in-javafx.html
        Stage stage = new Stage();
        
        VBox box = new VBox();
 
        Label label = new Label("New event:");
 
        
        TextField nameField = new TextField();
        nameField.setPromptText("enter event name");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("enter start date and time");
        startDatePicker.setOnAction(new EventHandler() {
			@Override
			public void handle(javafx.event.Event event) {		
			}
        });
        TimeSpinner startSpinner = new TimeSpinner();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("enter end date and time");
        endDatePicker.setOnAction(new EventHandler() {
			@Override
			public void handle(javafx.event.Event event) {			
			}
        });
        TimeSpinner endSpinner = new TimeSpinner();

        TextField textTags = new TextField();
        textTags.setPromptText("enter tag(s), separated by commas");
 
        TextField detailText = new TextField();
        detailText.setPromptText("enter description");

        
        Button btnAdd = new Button();
        btnAdd.setText("Add event");
 
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	String name = nameField.getText();
            	LocalDateTime start = startDatePicker.getValue().atTime(startSpinner.getValue());
            	LocalDateTime end = endDatePicker.getValue().atTime(endSpinner.getValue());
            	List<String> tags = Arrays.asList(textTags.getText().split(","));
            	String details = detailText.getText();
            	
            	Planner.addEvent(name, start, end, tags, details);
            	
            	stage.close(); // return to main window
            }
        });
 
        box.getChildren().add(label);
        box.getChildren().add(nameField);
        box.getChildren().add(startDatePicker);
        box.getChildren().add(startSpinner);
        box.getChildren().add(endDatePicker);
        box.getChildren().add(endSpinner);
        box.getChildren().add(textTags);
        box.getChildren().add(detailText);
        box.getChildren().add(btnAdd);
        Scene scene = new Scene(box, 250, 500);
        stage.setScene(scene);
        stage.show();    	
    }
     
    @Override
    public void stop(){
        Planner.setEvents(events);
        Planner.writeData();
    }
}