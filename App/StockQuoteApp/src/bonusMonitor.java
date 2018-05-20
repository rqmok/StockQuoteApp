import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class bonusMonitor extends Controller {

    private VBox vBox;

    public bonusMonitor(){
        vBox = new VBox();
    }

    @Override
    public void update(ArrayList<Monitor> monitors) {
        if (monitors == null) {
            return;
        }

        Platform.runLater(() -> {

            //Clear the vBox
            vBox.getChildren().clear();

            //Create main list for storing sets of data for each monitor
            ArrayList<List<String>> primaryList = new ArrayList<>();

            //For each monitor
            for (Monitor monitor : monitors) {
                //Get the required data
                StockData currentData = monitor.getStock().getLastStockData();
                //Get stock label
                String stockLabel = currentData.getQuoteData().get(StockService.dataIndex.INDEX_SYMBOL);
                //Get current price
                Double currentPrice = Double.valueOf(currentData.getQuoteData().get(StockService.dataIndex.INDEX_LAST_TRADE));

                //Retrieve original price
                StockData originalData = monitor.getStock().getStockData().get(0);
                Double originalPrice = Double.valueOf(originalData.getQuoteData().get(StockService.dataIndex.INDEX_LAST_TRADE));

                //Calculate change
                Double changeInValue = ((currentPrice - originalPrice)/ originalPrice)*100;
                //convert to 3 decimal places
                DecimalFormat df = new DecimalFormat("#.###");
                changeInValue = Double.valueOf(df.format(changeInValue));
                currentPrice  = Double.valueOf(df.format(currentPrice));

                //Create list object with fields
                List<String> monitorInfo = new ArrayList<>();
                monitorInfo.add(stockLabel);
                monitorInfo.add(Double.toString(currentPrice));
                monitorInfo.add(Double.toString(changeInValue));
                //Add list to primary set of lists
                primaryList.add(monitorInfo);


            }
            //Bubble sort
            //The Bubble sort will iterate through the primary list and sort it in descending order
            //This will allow us to determine our top 5 and bottom 5 stocks
            for (int i = 0; i < primaryList.size(); i++){
                for (int j = 1; j < (primaryList.size() - i); j++) {
                    Double a = Double.parseDouble(primaryList.get(j-1).get(StockService.dataIndex.INDEX_LAST_TRADE));
                    Double b = Double.parseDouble(primaryList.get(j).get(StockService.dataIndex.INDEX_LAST_TRADE));
                    if ( a < b){
                        List temp = primaryList.get(j-1);
                        primaryList.set(j-1, primaryList.get(j));
                        primaryList.set(j, temp);
                    }
                }
            }

            //List for storing top 5 stocks
            List<List<String>> topFive = new ArrayList<>();

            //Add top 5 stocks to our list
            for (int i = 0; i < primaryList.size() && i < 5 ; i++){
                topFive.add(primaryList.get(i));
            }

            //Reverse our array so we can find the bottom 5 values
            Collections.reverse(primaryList);

            //List for storing bottom 5 stocks
            List<List<String>> bottomFive = new ArrayList<>();
            //Add bottom 5 stocks to our list
            for (int i = 0; i < primaryList.size() && i < 5 ; i++){

                System.out.println(primaryList.get(i).get(2));
                bottomFive.add(primaryList.get(i));

            }

            //Put the top 5 stocks into listviews
            VBox topFiveBox = new VBox();

            for (int i = 0; i < topFive.size() ; i++){
                //Initialise the heading
                if (i == 0){
                    //Create haeding label
                    Label topFiveLabel = new Label("Top 5 Stock Gains");
                    topFiveBox.getChildren().addAll(topFiveLabel);
                }

                //create a list view object
                ListView<Label> listView = new ListView<>();
                listView.setOrientation(Orientation.HORIZONTAL);
                listView.setFixedCellSize(50);

                //Create an observable list of labels. We will feed this into our listview
                ObservableList<Label> labels = FXCollections.observableArrayList();
                Label name = new Label(topFive.get(i).get(0));
                Label currentPrice = new Label("$" + topFive.get(i).get(1));
                Label changePercentage = new Label(topFive.get(i).get(2) + "%");

                //change colour of label
                changePercentage.setTextFill(Color.web("#00FF00"));

                //Add labels to observable list
                labels.addAll(name,currentPrice,changePercentage);
                //Add observable list to listview
                listView.setItems(labels);
                //Add the listview to the vBox
                topFiveBox.getChildren().addAll(listView);
            }

            //Do the same for the bottom 5 stocks
            VBox bottomFiveBox = new VBox();

            for (int i = 0; i < bottomFive.size() ; i++){
                //Initialise the heading
                if (i == 0){
                    Label bottomFiveLabel = new Label("Worst 5 Stock Gains");
                    bottomFiveBox.getChildren().addAll(bottomFiveLabel);
                }

                //create a list view object
                ListView<Label> listView = new ListView<>();
                listView.setOrientation(Orientation.HORIZONTAL);
                listView.setFixedCellSize(50);

                //Create an observable list of labels. We will feed this into our listview
                ObservableList<Label> labels = FXCollections.observableArrayList();
                //Create labels for all our data
                Label name = new Label(bottomFive.get(i).get(0));
                Label currentPrice = new Label("$" + bottomFive.get(i).get(1));
                Label changePercentage = new Label(bottomFive.get(i).get(2) + "%");

                //change colour of label
                changePercentage.setTextFill(Color.web("#8B0000"));

                //Add labels to observable list
                labels.addAll(name,currentPrice,changePercentage);
                //Add observable list to listview
                listView.setItems(labels);
                //Add the listview to the vBox
                bottomFiveBox.getChildren().addAll(listView);
            }

            //Add data to main vBox
            vBox.getChildren().addAll(topFiveBox,bottomFiveBox);

        });

    }

    public VBox getVBox(){
        return vBox;
    }

    @Override
    public ArrayList<Monitor> getSelectedMonitors() {
        return null;
    }
}
