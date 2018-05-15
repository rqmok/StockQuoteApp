import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

public class ApplicationController extends Application implements UpdateStockDataDelegate {

    private Stage window;

    //declare new variable of type TextField. This is what we will provide users to input data
    private TextField stockSymbolTextField;

    // Stores all out data
    Model model = new Model();

    // Stores all controllers
    ArrayList<Controller> controllers = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Stock Tracker App");

        // Ensure that the program terminates upon closing the window
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        // Create TextField for entering stock names
        stockSymbolTextField = new TextField();
        stockSymbolTextField.setPromptText("Enter Stock Symbol");
        stockSymbolTextField.setMinWidth(100);

        // Create button for adding new stock tracker
        Button stockAddButton = new Button("Add New Stock");
        // Define action for clicking button
        stockAddButton.setOnAction(e -> addNewMonitor());

        // Create button for Deleting stock tracker
        Button stockDeleteButton = new Button("Delete Stock");
        // Define action for clicking button
        stockDeleteButton.setOnAction(e -> deleteMonitor());

        // Create new HBox for buttons and text field
        HBox hBox = new HBox();
        // Creating Insets for that the items are not right on the border of the HBox
        hBox.setPadding(new Insets(10,10,10,10));
        // Set spacing between items in the HBox
        hBox.setSpacing(10);
        // Add our items to the HBox
        hBox.getChildren().addAll(stockSymbolTextField,stockAddButton,stockDeleteButton);

        // Create a new table view controller
        TableViewController tableViewController = new TableViewController(model.getFieldNames());
        controllers.add(tableViewController);
        TableView<Monitor> tableView = tableViewController.getTableView();

        //Add a ListViewController
        ListViewController listViewController = new ListViewController();
        controllers.add(listViewController);
        ListView<LineChart> listView = listViewController.getListView();


        // Create our scene. Our scene will be a VBox, which will allow us to vertically stack elements in the scene
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, listView, hBox);
        vBox.setVgrow(tableView, Priority.ALWAYS);


        // Create an event handler to handle key presses. This allows the user to add stock monitors with the ENTER key
        // and delete stock monitors with he DELETE key
        vBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case DELETE: deleteMonitor();
                    case ENTER: addNewMonitor();

                }
            }
        });

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

        // Create a new update
        StockUpdaterClock updater = new StockUpdaterClock(this, 5);
        // Create a new thread
        Thread t = new Thread(updater);
        t.start();
    }

    // Function for adding a new tracker
    public void addNewMonitor() {
        // Get symbol text
        String symbol = stockSymbolTextField.getText();

        // Remove text from text field, ready for new input
        stockSymbolTextField.clear();

        // Ask model to add monitor
        model.addMonitor(symbol, StockService.serviceTypes.STOCK_QUOTE_WS_SERVICE, Monitor.monitorTypes.TABLE_MONITOR);

        //Test: add of graph type monitor
        model.addMonitor(symbol, StockService.serviceTypes.STOCK_QUOTE_WS_SERVICE, Monitor.monitorTypes.GRAPH_MONITOR);

        // Update the controllers
        this.updateControllers();
    }

    // Function for deleting tracker
    public void deleteMonitor() {
        // Get selected monitors
        for (Controller controller : controllers) {
            ArrayList<Monitor> selectedMonitors = controller.getSelectedMonitors();

            // Check if any monitors were selected
            if (selectedMonitors == null || selectedMonitors.size() < 1) {
                return;
            }

            // Remove the selected monitors
            for (Monitor monitor : selectedMonitors) {
                // Ask the model to remove the monitor
                model.removeMonitor(monitor);
            }
        }

        // Update all view controllers
        this.updateControllers();
    }

    public void updateControllers() {
        // Update all view controllers
        for (Controller controller : controllers) {
            controller.update(model.getMonitors());
        }
    }

    public void updateStockData() {
        // Ask the model to update data
        model.updateStockData();

        // Updater the controllers
        this.updateControllers();
    }
}
