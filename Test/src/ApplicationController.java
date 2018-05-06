import javafx.application.Application;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ApplicationController extends Application implements UpdateStockDataDelegate {

    private Stage window;
    private TableView<Monitor> mainTable;

    //declare new variable of type TextField. This is what we will provide users to input data
    private TextField stockSymbolTextField;

    // List of stocks
    private ArrayList<Stock> stocks = new ArrayList<>();

    // List of available stock services
    private ArrayList<StockService> services = new ArrayList<>();

    // Keep a record of all Monitors
    private ArrayList<Monitor> monitors = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    // Helper function for creating a new column for the table view
    private TableColumn<Monitor, String> createTableColumn(String title, String property) {
        // Create a new column
        TableColumn<Monitor, String> column = new TableColumn<>(title);
        column.setMinWidth(200);
        // The property to get from StockData object
        column.setCellValueFactory(new PropertyValueFactory<>(property));

        return column;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Stock Tracker App");

        // Create a new service
        services.add(new StockQuoteWSAdapter());

        // Create new table
        mainTable = new TableView<>();

        // Make columns
        // Stock Symbol
        TableColumn<Monitor, String> symbolColumn = createTableColumn("Stock Symbol", "symbol");
        // Last Trade
        TableColumn<Monitor, String> tradeColumn = createTableColumn("Last Trade", "lastTrade");
        // Date
        TableColumn<Monitor, String> dateColumn = createTableColumn("Date", "date");
        // Time
        TableColumn<Monitor, String> timeColumn = createTableColumn("Time", "time");

        //feed our observable list into the table
        //mainTable.setItems(getAllStockData());
        //Put the columns into our table
        mainTable.getColumns().addAll(symbolColumn, tradeColumn, dateColumn, timeColumn);

        //Create TextField for entering stock names
        stockSymbolTextField = new TextField();
        stockSymbolTextField.setPromptText("Enter Stock Symbol");
        stockSymbolTextField.setMinWidth(100);

        //Create button for adding new stock tracker
        Button stockAddButton = new Button("Add New Stock");
        //Define action for clicking button
        stockAddButton.setOnAction(e -> addNewMonitor());

        //Create button for Deleting stock tracker
        Button stockDeleteButton = new Button("Delete Stock");
        //Define action for clicking button
        stockDeleteButton.setOnAction(e -> deleteMonitor());

        //Create new HBox for buttons and text field
        HBox hBox = new HBox();
        //Creating Insets for that the items are not right on the border of the HBox
        hBox.setPadding(new Insets(10,10,10,10));
        //Set spacing between items in the HBox
        hBox.setSpacing(10);
        //Add our items to the HBox
        hBox.getChildren().addAll(stockSymbolTextField,stockAddButton,stockDeleteButton);

        //Create our scene. Our scene will be a VBox, which will allow us to vertically stack elements in the scene
        VBox vBox = new VBox();
        vBox.getChildren().addAll(mainTable, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

        // Create a new updater
        StockUpdaterClock updater = new StockUpdaterClock(this, 5 * 60);
        // start the updater
        updater.beginUpdates();
    }

    // Helper function to find a stock
    private Stock findStock(String stockSymbol) {
        for (Stock stock : stocks) {
            if (stock.getStockData().getSymbol().contains(stockSymbol)) {
                return stock;
            }
        }

        return null;
    }

    // Helper function to find a service
    private StockService findService(StockService.serviceTypes serviceType) {
        for (StockService currentService : services) {
            if (currentService.getServiceType() == serviceType) {
                return currentService;
            }
        }

        return null;
    }

    //Function for adding a new tracker
    public void addNewMonitor(){
        // Get test from textfield
        String symbolText = stockSymbolTextField.getText();

        //Clear the text field
        stockSymbolTextField.clear();

        // Check for empty text field
        if (symbolText == null || symbolText.length() == 0) {
            System.out.println("ApplicationController: symbol text is null");
            return;
        }

        // First check the service for data
        StockService service = findService(StockService.serviceTypes.STOCK_QUOTE_WS_SERVICE);
        // Check if service was found.
        if (service == null) {
            System.out.println("ApplicationController: service not found");
            return;
        }

        // Ask the service for data
        StockData data = null;
        try {
            data = service.getStockData(symbolText);
        } catch (Exception e) {
            // Catch any thrown exceptions
            System.out.println(e.getMessage());
            return;
        }
        // Check if data was received
        if (data == null) {
            System.out.println("ApplicationController: service return null data.");
            return;
        }

        // Data was successfully retrieved.
        // Check if stock already exists
        Stock stock = findStock(symbolText);
        if (stock == null) {
            // Create a new stock
            stock = new Stock(data);

            // Keep track of stock
            stocks.add(stock);

            // attach the stock service
            stock.setStockService(service);
        }

        // Create a new monitor
        Monitor monitor = new StockQuoteWSMonitor();
        // Add the monitor to array list
        monitors.add(monitor);

        // Assign the stock to the monitor
        monitor.setStock(stock);

        reloadData();
    }

    //Function for deleting tracker
    public void deleteMonitor() {
        //Two variables, one for storing the tracker selected by the user and the other stores all the trackers
        //This allows us to delete any trackers from the table that are selected
        ObservableList<Monitor> selectedTracker, allTrackers;
        allTrackers = mainTable.getItems();
        //Get item selected by user
        selectedTracker = mainTable.getSelectionModel().getSelectedItems();

        for (Monitor selectedMonitor : selectedTracker) {
            monitors.remove(selectedMonitor);
        }

        //For every product selected, delete from the table
        selectedTracker.forEach(allTrackers::remove);
    }


    //This is our observable list filled with StockData objects. Our table will be display this data and change to
    //reflect any changes made to this data
    public ObservableList<Monitor> getAllStockData(){
        ObservableList<Monitor> stockDataList = FXCollections.observableArrayList();

        for (Monitor monitor : monitors) {
            stockDataList.add(monitor);
        }

        return stockDataList;
    }

    public void reloadData() {
        // Get current data
        ObservableList<Monitor> stockDataList = getAllStockData();

        mainTable.setItems(stockDataList);
    }

    public void updateStockData() {
        // Go through each stock
        for (Stock stock : stocks) {
            // use stock service to get new data
            StockService service = stock.getStockService();
            StockData data = null;
            try {
                data = service.getStockData(stock.getStockData().getSymbol());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            // Update it in the stock
            stock.setStockData(data);
        }

        // Finally, reload all table view data
        this.reloadData();

        System.out.println("Updated at " + LocalDateTime.now().toString());
    }
}
