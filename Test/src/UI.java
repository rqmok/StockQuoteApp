import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class UI extends Application {

    Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("Stock Tracker");
        //New layout GridPane. Allows us to organise our data as a grid
        GridPane grid = new GridPane();
        //adds padding around the window
        grid.setPadding(new Insets(10,10,10,10));
        //set vertical spacing
        grid.setVgap(8);
        //set horizontal spacing
        grid.setHgap(10);

        //label
        Label label1 = new Label("Enter Stock Name: ");
        //place this label in column one, row one
        GridPane.setConstraints(label1, 0, 0);

        //Text input field
        TextField stockNameInput = new TextField();
        stockNameInput.setPromptText("Stock Name");
        GridPane.setConstraints(stockNameInput, 0,1);

        //Create Submission button
        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton,1,1);

        //add children to grid
        grid.getChildren().addAll(label1, stockNameInput, submitButton);
        //create scene with our grid pane
        Scene scene = new Scene(grid, 400, 400);
        //add scene to stage
        window.setScene(scene);

        //show window (our stage) to the user
        window.show();


    }


}
