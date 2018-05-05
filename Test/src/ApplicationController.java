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

public class ApplicationController extends Application {

    Stage window;
    TableView<StockData> mainTable;
    //declare new variable of type TextField. This is what we will provide users to input data
    TextField stockSymbol;
    //List of stocks
    Stock[] stocks;

    public static void main(String[] args){
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Stock Tracker App");

        //Making Columns
        //This column is for the symbol label
        TableColumn<StockData, String> symbolColumn = new TableColumn<>("Stock Symbol");
        symbolColumn.setMinWidth(200);
        //Makes it take the column value from the monitor variables
        symbolColumn.setCellValueFactory(new PropertyValueFactory<>("symbolLabel"));

        //This column is for the last trade label
        TableColumn<StockData, String> tradeColumn = new TableColumn<>("Trade Symbol");
        tradeColumn.setMinWidth(200);
        //Makes it take the column value from the monitor variables
        tradeColumn.setCellValueFactory(new PropertyValueFactory<>("lastTradeLabel"));

        //This column is for the date label
        TableColumn<StockData, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setMinWidth(200);
        //Makes it take the column name from the monitor variables
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateLabel"));

        //This column is for the time label
        TableColumn<StockData, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setMinWidth(200);
        //Makes it take the column name from the monitor variables
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("timeLabel"));

        mainTable = new TableView<>();
        //feed our observable list into the table
        mainTable.setItems(getAllStockData());
        //Put the columns into our table
        mainTable.getColumns().addAll(symbolColumn,tradeColumn,dateColumn,timeColumn);

        //Create TextField for entering stock names
        stockSymbol = new TextField();
        stockSymbol.setPromptText("Enter Stock Symbol");
        stockSymbol.setMinWidth(100);

        //Create button for adding new stock tracker
        Button stockAddButton = new Button("Add New Stock");
        //Define action for clicking button
        stockAddButton.setOnAction(e -> addNewTracker());

        //Create button for Deleting stock tracker
        Button stockDeleteButton = new Button("Delete Stock");
        //Define action for clicking button
        stockDeleteButton.setOnAction(e -> deleteTracker());

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


    //This is our observable list filled with StockData objects. Our table will be display this data and change to
    //reflect any changes made to this data
    public ObservableList<StockData> getAllStockData(){
        ObservableList<StockData> stockData = FXCollections.observableArrayList();
        return stockData;
    }
}
