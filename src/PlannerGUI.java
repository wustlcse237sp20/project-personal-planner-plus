package src;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PlannerGUI extends Application
{
    private List<Event> events;
    private boolean showDetails = true, freezeCursor = true;
    private int calendarItem_lastChange =-1;
    private final String baseDetailMode = "Detail Mode ";
    private final Rectangle2D screenSize = Screen.getPrimary().getBounds();
    private ComboBox tagBox;
    private ListView calendarListView;
    private Label calendarItemDetails;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        loadEvents();

        // Create details label
        calendarItemDetails = new Label(baseDetailMode + "OFF");

        // Setup list of events
        calendarListView = createCalendarListView();
        ScrollPane calendarScrollPane = listViewtoScrollPane(calendarListView);

        // Setup two listeners on the calendarItems: the first fires on any change e.g. the arrow keys; the second for the double-click; both are needed
        calendarListView.getSelectionModel().selectedIndexProperty().addListener(new InvalidationListener() {
        @Override
            public void invalidated(Observable observable) {
                calendarItemDetailChanged(
                        ((ReadOnlyIntegerProperty) observable).getValue(),
                        calendarItemDetails,
                        calendarListView);
            }
        });
        calendarListView.setOnMouseClicked(EventObject -> {
            if(events.size() > 0){
				try{
				calendarItemDetailClick(
					((IndexedCell) (EventObject.getTarget())).getIndex(),
					calendarItemDetails,
					calendarListView);
				}
				catch(Exception e){
					System.out.println("Non-item clicked");
				}	
			}
		});

        // Create search tool and listener
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Query...");
        searchBar.setMinWidth(screenSize.getWidth() / 2.0-10); // search max 2/3 width
        searchBar.textProperty().addListener((observable, oldQuery, newQuery) -> {
            tagBox.setValue("all");
            searchCalendar(searchBar.getText(), calendarListView);
        });

        // Create add button and listener
        Button newItemBtn = new Button();
        newItemBtn.setText("New Event");
        newItemBtn.setMinWidth(screenSize.getWidth() / 6.0-15); // button max 1/3 width
        newItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showAddEvent(calendarListView);
            }
        });
        
        // Create edit button and listener
        Button editItemBtn = new Button();
        editItemBtn.setText("Edit Event");
        editItemBtn.setMinWidth(screenSize.getWidth() / 6.0-15); // button max 1/3 width
        editItemBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
				if(events.size() > 0){	
					showEditEvent(calendarListView);
				}
            }
        });

        // Create tag box and listener
        tagBox = new ComboBox();
        tagBox.getItems().add("all");
        tagBox.setMinWidth(screenSize.getWidth() / 6.0 - 10);
        for (String tag : Planner.getTagSet()){
            tagBox.getItems().add(tag);
        }
        tagBox.setValue("all");
        tagBox.setVisibleRowCount(5);
        tagBox.valueProperty().addListener(new ChangeListener<String>(){
            @Override public void changed(ObservableValue ov, String t, String t1) {
            reloadEvents(t1);
        }
        });

        // Create hBox for the top row
        HBox firstRow = new HBox();
        firstRow.setPadding(new Insets(10, 10, 10, 10)); // padding of hbox
        firstRow.setSpacing(10); // space between children
        firstRow.getChildren().addAll(searchBar, newItemBtn, editItemBtn, tagBox);

        // Create layout, add items to it
        BorderPane layout = new BorderPane();
        layout.setTop(firstRow);  
        layout.setCenter(calendarScrollPane);
        layout.setBottom(calendarItemDetails);

        // Create a scene object with listeners 
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
                            calendarItemDetails.setText(baseDetailMode +  "OFF");
                        }
                        break;
                }
            }
        });

        // Display the layout
        showLayout(primaryStage, scene, "Personal Planner +"); 
    }

    public void loadEvents(){
        Planner.initializeVars();
        Planner.loadData();
        events = Planner.getFilteredEvents("all");
    }

    public void reloadEvents(String filter){
        events = Planner.getFilteredEvents(filter);
        calendarListView.getItems().clear();
        for (Event calendarItem:events){
            calendarListView.getItems().add(calendarItem.toString());
        }
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

    private void showLayout(Stage primaryStage, Scene scene, String title){
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void calendarItemDetailChanged(int calendarIndex_Change, Label calendarItemDetails, ListView calendarListView){
        if(calendarIndex_Change >= 0) {
            Event clickedEvent = events.get(calendarIndex_Change);
            calendarItemDetails.setText(clickedEvent.getDetailsString());
            calendarItem_lastChange = calendarIndex_Change;
            showDetails = true;
            freezeCursor = true;
        }
    }

     private void calendarItemDetailClick(int calendarIndexClick, Label calendarItemDetails, ListView calendarListView){
        if(calendarItem_lastChange  == calendarIndexClick && !freezeCursor){ 
            showDetails = !showDetails;
            if(showDetails){
                Event clickedEvent = events.get(calendarIndexClick);
                calendarItemDetails.setText(baseDetailMode+ "ON: " + clickedEvent.getDetailsString());
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

    private void searchCalendar(String query, ListView calendarListView) {
        // Reset calendarListView
        reloadEvents("all");

        // Execute search
        int index = 0;
        for (Event event : events) {
            if (!event.toString().contains(query)) {
                calendarListView.getItems().remove(index);
            }
            index++;
        }
    }

    // showAddEvent() and showEditEvent() adapted from:
    // quickprogrammingtips.com/java/how-to-open-a-new-window-in-javafx.html
    private void showAddEvent(ListView calendarListView) {
 
        TextField nameField = new TextField();
        nameField.setPromptText("enter event name");

        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("enter start date and time");
        startDatePicker.setOnAction(new EventHandler() {
        	@Override
        	public void handle(javafx.event.Event event) {
			
        		}
        });
        
        TextField startTimeField = new TextField();
        startTimeField.setPromptText("enter start time (\"hh:mm\", 24-hour clock)");

        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("enter end date and time");
        endDatePicker.setOnAction(new EventHandler() {
        	@Override
        		public void handle(javafx.event.Event event) {
        		}
        });
        TextField endTimeField = new TextField();
        endTimeField.setPromptText("enter start time (\"hh:mm\", 24-hour clock)");

        TextField textTags = new TextField();
        textTags.setPromptText("enter tag(s), separated by commas");
 
        TextField detailText = new TextField();
        detailText.setPromptText("enter description");
        
        Button btnAdd = new Button();
        btnAdd.setText("Add event");
 
        Stage stage = new Stage();

        Label errLabel = new Label();
        errLabel.setTextFill(Color.web("#fa1111"));
        
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	boolean inputValid = true;
	            String name = nameField.getText();

	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	            
	            String startTimeText = startTimeField.getText();
	            // convert e.g. 1:23 to 01:23
	            if(startTimeText.length() == 4 && startTimeText.charAt(1) == ':') {
	            	startTimeText = "0"+startTimeText;
	            }
	            
	            LocalDateTime start = null;
	            LocalDate startDate = startDatePicker.getValue();
	            
	            // input validation: in case start date is empty
	            if(startDate == null) {
	            	inputValid = false;
	            	startDatePicker.setStyle("-fx-background-color: #fc9e9d");
                	errLabel.setText("at least one field is empty/invalid");
	            }
	            else {
	            	startDatePicker.setStyle("-fx-background-color: white");
	            }
	            
	            // input validation: in case startTime parsing throws exception
	            try {
	            	LocalTime startTime = LocalTime.parse(startTimeText, formatter);
	            	if(startDate != null) {
	            		start = startDate.atTime(startTime);	            	
	            	}
	            	startTimeField.setStyle("-fx-background-color: white"); // only happens if no exception!
	            }
	            catch(DateTimeParseException e) {
	            	inputValid = false;
	            	startTimeField.setStyle("-fx-background-color: #fc9e9d");
                	errLabel.setText("at least one field is empty/invalid");
	            }
	            
	            String endTimeText = endTimeField.getText();
	            if(endTimeText.length() == 4 && endTimeText.charAt(1) == ':') {
	            	endTimeText = "0"+endTimeText;
	            }
	            
	            LocalDateTime end = null;
	            LocalDate endDate = endDatePicker.getValue();
	            
	            // input validation: in case end date is empty
	            if(endDate == null) {
	            	inputValid = false;
	            	endDatePicker.setStyle("-fx-background-color: #fc9e9d");
                	errLabel.setText("at least one field is empty/invalid");
	            }
	            else {
					startDatePicker.setStyle("-fx-background-color: white");
				}
	            
	            // input validation: in case endTime parsing throws exception
	            try {
		            LocalTime endTime = LocalTime.parse(endTimeText, formatter);
		            if(endDate != null) {
		            	end = endDate.atTime(endTime);
		            }
	            	endTimeField.setStyle("-fx-background-color: white");

	            }
	            catch(DateTimeParseException e) {
	            	inputValid = false;
	            	endTimeField.setStyle("-fx-background-color: #fc9e9d");
                	errLabel.setText("at least one field is empty/invalid");
	            }
	            
	            List<String> tags = Arrays.asList(textTags.getText().split(",")); //@TODO: Sanitize input
                List<String> validTags = new ArrayList();
	            String details = detailText.getText();
	            // input validation: in case event does not have name
	            if(name.equals("")) {
	            	inputValid = false;
	            	nameField.setStyle("-fx-background-color: #fc9e9d");
                	errLabel.setText("at least one field is empty/invalid");
	            }
	            else {
	            	nameField.setStyle("-fx-background-color: white");
	            }
	            for(int i = 0; i < tags.size(); i++) {
                    String tag = tags.get(i);
                    if(tag.length() > 0 && !tag.equals("all")){
                        validTags.add(tag);
                    }
                }
	            for(int i = 0; i < validTags.size(); i++) {
	            	String tag = validTags.get(i);
	            	if(tag.charAt(0) == ' '){
                		tag = tag.substring(1);
    	         	}
    	        	if(tag.charAt(tag.length() - 1) == ' ') {
    	        		tag = tag.substring(0, tag.length()-1);
    	           	}
    	            validTags.set(i, tag);
	            }
	            Iterator<String> itr = validTags.iterator();
                while (itr.hasNext()) {
                    String tag = itr.next();
                    if (tag.equals("all")) {
                        itr.remove();
                    }
                }
                if(inputValid && end.isBefore(start)) {
                	inputValid = false;
                	errLabel.setText("the event cannot end before it begins!");
                }
	            if(inputValid) {
	            	Planner.addEvent(name, start, end, validTags, details);
	            	
	            	// Reset calendarListView
	            	calendarListView.getItems().clear();
	            	for (Event calendarItem:events){
	            		calendarListView.getItems().add(calendarItem.toString());           
	            	}

                    tagBox.getItems().clear();
                    tagBox.getItems().add("all");
                    for (String tag : Planner.getTagSet()){
                        tagBox.getItems().add(tag);
                    }
                    tagBox.setValue("all");
	            	stage.close(); // return to main window
                    calendarItemDetails.setText(baseDetailMode +  "OFF");        	
	            }
            }
        });
        VBox box = new VBox();
        Label label = new Label("New event:");
        box.getChildren().addAll(
            label, 
            nameField, 
            startDatePicker, 
            startTimeField,
            endDatePicker, 
            endTimeField, 
            textTags, 
            detailText,
            errLabel,
            btnAdd);
        Scene scene = new Scene(box, 250, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showEditEvent(ListView calendarListView) {
    	final int currIndex = calendarListView.getSelectionModel().selectedIndexProperty().getValue();
        Event eventToEdit = null;
    	if(currIndex > -1){
            eventToEdit = events.get(currIndex);
        }
    	
    	if(eventToEdit != null) {
    		TextField nameField = new TextField();
    		nameField.setText(eventToEdit.getName());
    		
    		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    		DatePicker startDatePicker = new DatePicker(eventToEdit.getStartDateTime().toLocalDate());
    		startDatePicker.setOnAction(new EventHandler() {
    			@Override
    			public void handle(javafx.event.Event event) {
    				
    			}
    		});	

    		TextField startTimeField = new TextField();
    		startTimeField.setText(eventToEdit.getStartDateTime().format(timeFormatter));
    		
    		DatePicker endDatePicker = new DatePicker(eventToEdit.getEndDateTime().toLocalDate());
    		endDatePicker.setOnAction(new EventHandler() {
    			@Override
    			public void handle(javafx.event.Event event) {
    			}
    		});
    		
    		TextField endTimeField = new TextField();
    		endTimeField.setText(eventToEdit.getEndDateTime().format(timeFormatter));
    		
    		TextField textTags = new TextField();
    		StringBuilder sb = new StringBuilder();
    		List<String> eventTags = eventToEdit.getTags();
    		if(eventTags.size() > 0) {
    			for(int i = 0; i < eventTags.size() - 1; i++) {
    				sb.append(eventTags.get(i));
    				sb.append(", ");
    			}
    			sb.append(eventTags.get(eventTags.size()-1));
    			textTags.setText(sb.toString());    			
    		}
    		else {
    	        textTags.setPromptText("enter tag(s), separated by commas");
    		}
    		
    		TextField detailText = new TextField();
    		if(eventToEdit.getDetails().length() > 0) {
    			detailText.setText(eventToEdit.getDetails());
    		}
    		else {
    			detailText.setPromptText("enter description");    			
    		}
    		
    		Button btnAdd = new Button();
    		btnAdd.setText("Edit event");
    		
    		Stage stage = new Stage();
    		
    		final int eventId = eventToEdit.getId();

            Label errLabel = new Label();
            errLabel.setTextFill(Color.web("#fa1111"));
    		
    		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    			public void handle(ActionEvent event) {
    				boolean inputValid = true;
    				String name = nameField.getText();
    				
    				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    				
    				String startTimeText = startTimeField.getText();
    				// convert e.g. 1:23 to 01:23
    				if(startTimeText.length() == 4 && startTimeText.charAt(1) == ':') {
    					startTimeText = "0"+startTimeText;
    				}
    				
    				LocalDateTime start = null;
    				LocalDate startDate = startDatePicker.getValue();
    				
    				// input validation: in case start date is empty
    				if(startDate == null) {
    					inputValid = false;
    					startDatePicker.setStyle("-fx-background-color: #fc9e9d");
                    	errLabel.setText("at least one field is empty/invalid");
    				}
    				else {
    					startDatePicker.setStyle("-fx-background-color: white");
    				}
    				
    				// input validation: in case startTime parsing throws exception
    				try {
    					LocalTime startTime = LocalTime.parse(startTimeText, formatter);
    					if(startDate != null) {
    						start = startDate.atTime(startTime);	            	
    					}
    					startTimeField.setStyle("-fx-background-color: white"); // only happens if no exception!
    				}
    				catch(DateTimeParseException e) {
    					inputValid = false;
    					startTimeField.setStyle("-fx-background-color: #fc9e9d");
                    	errLabel.setText("at least one field is empty/invalid");
    				}
    				
    				String endTimeText = endTimeField.getText();
    				if(endTimeText.length() == 4 && endTimeText.charAt(1) == ':') {
    					endTimeText = "0"+endTimeText;
    				}
    				
    				LocalDateTime end = null;
    				LocalDate endDate = endDatePicker.getValue();
    				
    				// input validation: in case end date is empty
    				if(endDate == null) {
    					inputValid = false;
    					endDatePicker.setStyle("-fx-background-color: #fc9e9d");
                    	errLabel.setText("at least one field is empty/invalid");
    				}
    				else {
    					startDatePicker.setStyle("-fx-background-color: white");
    				}
    				
    				// input validation: in case endTime parsing throws exception
    				try {
    					LocalTime endTime = LocalTime.parse(endTimeText, formatter);
    					if(endDate != null) {
    						end = endDate.atTime(endTime);
    					}
    					endTimeField.setStyle("-fx-background-color: white");
    					
    				}
    				catch(DateTimeParseException e) {
    					inputValid = false;
    					endTimeField.setStyle("-fx-background-color: #fc9e9d");
                    	errLabel.setText("at least one field is empty/invalid");
    				}
    				
    				List<String> tags = Arrays.asList(textTags.getText().split(",")); //@TODO: Sanitize input
    				List<String> validTags = new ArrayList();
    				String details = detailText.getText();
    				// input validation: in case event does not have name
    				if(name.equals("")) {
    					inputValid = false;
    					nameField.setStyle("-fx-background-color: #fc9e9d");
                    	errLabel.setText("at least one field is empty/invalid");
    				}
    				else {
    					nameField.setStyle("-fx-background-color: white");
    				}
    				for(int i = 0; i < tags.size(); i++) {
    					String tag = tags.get(i);
    					if(tag.length() > 0 && !tag.equals("all")){
    						validTags.add(tag);
    					}
    				}
    				for(int i = 0; i < validTags.size(); i++) {
    					String tag = validTags.get(i);
    					if(tag.charAt(0) == ' '){
    						tag = tag.substring(1);
    					}
    					if(tag.charAt(tag.length() - 1) == ' ') {
    						tag = tag.substring(0, tag.length()-1);
    					}
    					validTags.set(i, tag);
    				}
    				Iterator<String> itr = validTags.iterator();
    				while (itr.hasNext()) {
    					String tag = itr.next();
    					if (tag.equals("all")) {
    						itr.remove();
    					}
    				}
    				if(inputValid && end.isBefore(start)) {
                    	inputValid = false;
                    	errLabel.setText("the event cannot end before it begins!");
                    }
    				if(inputValid) {
    					Event replacementEvent = new Event(name, start, end, validTags, details, eventId);
    					Planner.setEvent(currIndex, replacementEvent);
    					
    					// Reset calendarListView
    					calendarListView.getItems().clear();
    					for (Event calendarItem:events){
    						calendarListView.getItems().add(calendarItem.toString());           
    					}
    					
    					tagBox.getItems().clear();
    					tagBox.getItems().add("all");
    					for (String tag : Planner.getTagSet()){
    						tagBox.getItems().add(tag);
    					}
    					tagBox.setValue("all");
    					stage.close(); // return to main window	   
                        calendarItemDetails.setText(baseDetailMode +  "OFF");         	
    				}
    			}
    		});
    		VBox box = new VBox();
    		Label label = new Label("New event:");
    		box.getChildren().addAll(
    				label, 
    				nameField, 
    				startDatePicker, 
    				startTimeField,
    				endDatePicker, 
    				endTimeField, 
    				textTags, 
    				detailText,
    				errLabel,
    				btnAdd);
    		Scene scene = new Scene(box, 250, 500);
    		stage.setScene(scene);
    		stage.show();
    	}
    }
    
    @Override
    public void stop(){
        Planner.setEvents(events);
        Planner.writeData();
    }
}
