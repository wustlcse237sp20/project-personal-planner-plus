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
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception 
    {

        ArrayList<String> tags = new ArrayList<String>();
        Event newEvent = new Event("Test", new Date(), new Date(), tags, 0);

        primaryStage.setTitle("Personal Planner +");

        ListView eventsList = new ListView();
        for (int i=0; i< 50; ++i){
            eventsList.getItems().add(monthToString(newEvent.getStartDate().getMonth()) + " " + newEvent.getStartDate().getDay() +"\t" +newEvent.getName());
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(eventsList);
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.pannableProperty().set(true);
        
        StackPane root = new StackPane();
        root.getChildren().add(scrollPane);
        primaryStage.setScene(new Scene(root, 600, 700));
        primaryStage.show();
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
}
