import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
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
    // declare a service selection combo box
    private ComboBox<StockService.serviceTypes> serviceSelectionComboBox;
    // declare a symbols selection combo box for TLS service
    private ComboBox<String> symbolSelectionComboBox;
    //Declare the monitor selection combo box
    private ComboBox<Monitor.monitorTypes> monitorSelectionComboBox;

    // Flag to check whether the model is currently being modified.
    private Boolean resourcesBusy = false;

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

        // Create a dropdown for selecting service
        serviceSelectionComboBox = new ComboBox<>();
        serviceSelectionComboBox.getItems().setAll(StockService.serviceTypes.values());
        serviceSelectionComboBox.valueProperty().addListener(new ChangeListener<StockService.serviceTypes>() {
            @Override
            public void changed(ObservableValue<? extends StockService.serviceTypes> observable, StockService.serviceTypes oldValue, StockService.serviceTypes newValue) {
                switch (newValue) {
                    case STOCK_QUOTE_TLS_SERVICE:
                        stockSymbolTextField.setDisable(true);
                        symbolSelectionComboBox.setDisable(false);
                        break;
                    case STOCK_QUOTE_WS_SERVICE:
                        stockSymbolTextField.setDisable(false);
                        symbolSelectionComboBox.setDisable(true);
                        break;
                    default:
                        break;
                }
            }
        });

        // Create a dropdown for selecting symbol for TLS Service
        ArrayList<String> TLSSymbols = model.getTLSSymbols();
        symbolSelectionComboBox = new ComboBox<>();
        symbolSelectionComboBox.getItems().setAll(TLSSymbols);

        //Create a dropdown box for selecting monitor type
        monitorSelectionComboBox = new ComboBox<>();
        monitorSelectionComboBox.getItems().setAll(Monitor.monitorTypes.values());

        // Set defaults for combo boxes
        serviceSelectionComboBox.setValue(StockService.serviceTypes.STOCK_QUOTE_WS_SERVICE);
        symbolSelectionComboBox.setValue(TLSSymbols.get(0));
        monitorSelectionComboBox.setValue(Monitor.monitorTypes.TABLE_MONITOR);

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
        hBox.getChildren().addAll(stockSymbolTextField, serviceSelectionComboBox, symbolSelectionComboBox, monitorSelectionComboBox,stockAddButton,stockDeleteButton);

        // Create a new table view controller
        StocksTableViewController stocksTableViewController = new StocksTableViewController(model.getFieldNames());
        controllers.add(stocksTableViewController);
        TableView<Monitor> stocksTableView = stocksTableViewController.getTableView();
        stocksTableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        //Add a GraphListViewController
        GraphListViewController graphListViewController = new GraphListViewController();
        controllers.add(graphListViewController);
        ListView<LineChart> graphListView = graphListViewController.getListView();


        // Create our scene. Our scene will be a VBox, which will allow us to vertically stack elements in the scene
        HBox controllersHBox = new HBox();
        controllersHBox.getChildren().addAll(stocksTableView, graphListView);
        controllersHBox.setHgrow(stocksTableView, Priority.ALWAYS);
        controllersHBox.setHgrow(graphListView, Priority.ALWAYS);

        // Create a vbox to contain every other box
        VBox rootVBox = new VBox();
        rootVBox.getChildren().addAll(controllersHBox, hBox);
        rootVBox.setVgrow(controllersHBox, Priority.ALWAYS);


        // Create an event handler to handle key presses. This allows the user to add stock monitors with the ENTER key
        // and delete stock monitors with he DELETE key
        stockSymbolTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case DELETE: deleteMonitor();
                    case ENTER: addNewMonitor();

                }
            }
        });

        Scene scene = new Scene(rootVBox);
        window.setScene(scene);
        window.show();

        stocksTableView.setPrefWidth(window.getWidth()/3);

        // Create a new updater
        StockUpdaterClock WSUpdater = new StockUpdaterClock(this, 5 * 60, StockService.serviceTypes.STOCK_QUOTE_WS_SERVICE);
        // Create a new thread
        Thread WSThread = new Thread(WSUpdater);
        WSThread.start();

        // Create new update for lts
        StockUpdaterClock LTSUpdater = new StockUpdaterClock(this, 5, StockService.serviceTypes.STOCK_QUOTE_TLS_SERVICE);
        // Start a new thread
        Thread LTSThread = new Thread(LTSUpdater);
        LTSThread.start();
    }

    // Function for adding a new tracker
    public void addNewMonitor() {
        checkResourcesBusy();

        // Set the flag to let other methods know that the model is updating
        this.resourcesBusy = true;

        StockService.serviceTypes serviceType = this.serviceSelectionComboBox.getValue();
        Monitor.monitorTypes monitorType = this.monitorSelectionComboBox.getValue();
        String symbol = "";

        // Get the symbol from the appropriate field
        if (serviceType == StockService.serviceTypes.STOCK_QUOTE_WS_SERVICE) {
            symbol = stockSymbolTextField.getText();
            stockSymbolTextField.clear();
        }
        else if (serviceType == StockService.serviceTypes.STOCK_QUOTE_TLS_SERVICE) {
            symbol = symbolSelectionComboBox.getValue();
        }

        // Make sure symbol is not null
        if (symbol == null || symbol.length() < 1) {
            return;
        }

        // Ask the model to add the monitor
        model.addMonitor(symbol, serviceType, monitorType);

        // Update the controllers
        this.updateControllers();

        this.resourcesBusy = false;
    }

    // Function for deleting tracker
    public void deleteMonitor() {
        checkResourcesBusy();

        // Set the flag to let other methods know that the model is updating
        this.resourcesBusy = true;

        // Get selected monitors
        for (Controller controller : controllers) {
            ArrayList<Monitor> selectedMonitors = controller.getSelectedMonitors();

            // Remove the selected monitors
            for (Monitor monitor : selectedMonitors) {
                // Ask the model to remove the monitor
                model.removeMonitor(monitor);
            }
        }

        // Update all view controllers
        this.updateControllers();

        this.resourcesBusy = false;
    }

    public void updateControllers() {
        // Update all view controllers
        for (Controller controller : controllers) {
            controller.update(model.getMonitors());
        }
    }

    public void updateStockData(StockService.serviceTypes serviceType) {
        checkResourcesBusy();

        // Set the flag to let other methods know that the model is updating
        this.resourcesBusy = true;

        // Ask the model to update data
        model.updateStockData(serviceType);

        // Updater the controllers
        this.updateControllers();

        this.resourcesBusy = false;
    }

    private void checkResourcesBusy() {
        while (resourcesBusy) {
            // Check again after one second
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
