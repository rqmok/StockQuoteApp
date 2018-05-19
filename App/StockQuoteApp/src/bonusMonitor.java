import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

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


        int Counter = 0;
        System.out.println("helloooo");
        for (Monitor monitor : monitors) {
            StockData currentData = monitor.getStock().getLastStockData();
            Label stockLabel = new Label(currentData.getQuoteData().get(0));
            int currentPrice = Double.valueOf(currentData.getQuoteData().get(1)).intValue();

            System.out.println(currentPrice);

            StockData orignalData = monitor.getStock().getStockData().get(0);
            int originalPrice = Double.valueOf(orignalData.getQuoteData().get(1)).intValue();

            System.out.println(originalPrice);

            int changeInValue = ((currentPrice - originalPrice)/ originalPrice)*100;


            Label change = new Label(Integer.toString(changeInValue));
            ListView<Label> listView = new ListView<>();

            ObservableList<Label> labels = FXCollections.observableArrayList();

            listView.setPrefWidth(100);
            //listView.setMaxWidth(80);

            listView.setOrientation(Orientation.HORIZONTAL);

            labels.addAll(stockLabel, change);

            listView.setItems(labels);



            vBox.getChildren().add(listView);

            Counter ++;

        }



    }


    public VBox getGridPane(){
        return vBox;
    }


    @Override
    public ArrayList<Monitor> getSelectedMonitors() {
        return null;
    }
}
