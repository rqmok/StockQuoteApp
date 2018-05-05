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


    }


    //This is our observable list filled with StockData objects. Our table will be display this data and change to
    //reflect any changes made to this data
    public ObservableList<StockData> getAllStockData(){
        ObservableList<StockData> stockData = FXCollections.observableArrayList();
        return stockData;
    }
}
