import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Date;
import java.util.ArrayList;

public class PlannerGUI extends Application
{

    Event newEvent;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        //dummy data.  delete on backend integration
        ArrayList<String> tags = new ArrayList<String>();
        newEvent = new Event("Test", new Date(), new Date(), tags, 0);

        primaryStage.setTitle("Personal Planner +");

        ListView eventsList = createListView();
        ScrollPane scrollPane = createScrollPaneWithEvents(eventsList);
        
        
        showRootElement(primaryStage, scrollPane);
    }

    private ListView createListView(){
        ListView eventsList = new ListView();
        for (int i=0; i< 50; ++i){
            eventsList.getItems().add(monthToString(newEvent.getStartDate().getMonth()) + " " + newEvent.getStartDate().getDate() +"\t" +newEvent.getName());
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

    private String monthToString(int month){
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            default:
                return "December";
        }
    }

    private void showRootElement(Stage primaryStage, ScrollPane child){
        StackPane root = new StackPane();
        root.getChildren().add(child);
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();
    }
}
