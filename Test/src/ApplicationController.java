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

import java.util.ArrayList;

public class ApplicationController extends Application implements ApplicationControllerInterface {

    Stage window;
    TableView<StockData> mainTable;

    //declare new variable of type TextField. This is what we will provide users to input data
    TextField stockSymbol;

    // List of stocks
    ArrayList<Stock> stocks;

    // List of available stock services
    ArrayList<StockService> services;

    public static void main(String[] args){
        launch(args);
    }

    // Helper function fore creating a new column for the table view
    private TableColumn<StockData, String> createTableColumn(String title, String property) {
        // Create a new column
        TableColumn<StockData, String> column = new TableColumn<>(title);
        column.setMinWidth(200);
        // The property to get from StockData object
        column.setCellValueFactory(new PropertyValueFactory<>(property));

        return column;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Stock Tracker App");

        // Create new table
        mainTable = new TableView<>();

        // Make columns
        // Stock Symbol
        TableColumn<StockData, String> symbolColumn = createTableColumn("Stock Symbol", "getSymbol");
        // Last Trade
        TableColumn<StockData, String> tradeColumn = createTableColumn("Last Trade", "getLastTrade");
        // Date
        TableColumn<StockData, String> dateColumn = createTableColumn("Date", "getDate");
        // Time
        TableColumn<StockData, String> timeColumn = createTableColumn("Time", "getTime");

        //feed our observable list into the table
        mainTable.setItems(getAllStockData());
        //Put the columns into our table
        mainTable.getColumns().addAll(symbolColumn, tradeColumn, dateColumn, timeColumn);

        //Create TextField for entering stock names
        stockSymbol = new TextField();
        stockSymbol.setPromptText("Enter Stock Symbol");
        stockSymbol.setMinWidth(100);

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
        hBox.getChildren().addAll(stockSymbol,stockAddButton,stockDeleteButton);

        //Create our scene. Our scene will be a VBox, which will allow us to vertically stack elements in the scene
        VBox vBox = new VBox();
        vBox.getChildren().addAll(mainTable, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();

    }

    //Function for adding a new tracker
    public void addNewMonitor(){
        //Create new monitor




        //Clear the text field
        stockSymbol.clear();
    }

    //Function for deleting tracker
    public void deleteMonitor(){
        //Two variables, one for storing the tracker selected by the user and the other stores all the trackers
        //This allows us to delete any trackers from the table that are selected
        ObservableList<StockData>selectedTracker,allTrackers;
        allTrackers = mainTable.getItems();
        //Get item selected by user
        selectedTracker = mainTable.getSelectionModel().getSelectedItems();

        //For every product selected, delete from the table
        selectedTracker.forEach(allTrackers::remove);
    }


    //This is our observable list filled with StockData objects. Our table will be display this data and change to
    //reflect any changes made to this data
    public ObservableList<StockData> getAllStockData(){
        ObservableList<StockData> stockData = FXCollections.observableArrayList();
        // TODO: Add the StockData

        return stockData;
    }

    @Override
    public void reloadData() {
        // TODO: Reload stock data
        //We update the observable list
        ObservableList<StockData> allTrackers = mainTable.getItems();
    }
}
