import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

        vBox.getChildren().clear();

        //For each monitor
        ArrayList<List<String>> primaryList = new ArrayList<>();

        for (Monitor monitor : monitors) {
            //Get the required data
            StockData currentData = monitor.getStock().getLastStockData();
            String stockLabel = currentData.getQuoteData().get(0);
            Double currentPrice = Double.valueOf(currentData.getQuoteData().get(1));


            StockData orignalData = monitor.getStock().getStockData().get(0);
            Double originalPrice = Double.valueOf(orignalData.getQuoteData().get(1));



            Double changeInValue = ((currentPrice - originalPrice)/ originalPrice)*100;
            //convert to 3 decimal places
            DecimalFormat df = new DecimalFormat("#.###");
            changeInValue = Double.valueOf(df.format(changeInValue));



           // Label change = new Label(Integer.toString(changeInValue));

            List<String> monitorInfo = new ArrayList<>();
            monitorInfo.add(stockLabel);
            monitorInfo.add(Double.toString(currentPrice));
            monitorInfo.add(Double.toString(changeInValue));

            primaryList.add(monitorInfo);


        }
            //Bubble sort
            //The Bubble sort will iterate through the primary list and sort it in descending order
            //This will allow us to determine our top 5 and bottom 5 stocks
            for (int i = 0; i < primaryList.size(); i++){
                for (int j = 1; j < (primaryList.size() - i); j++) {
                    Double a = Double.parseDouble(primaryList.get(j-1).get(2));
                    Double b = Double.parseDouble(primaryList.get(j).get(2));
                    if ( a < b){
                        List temp = primaryList.get(j-1);
                        primaryList.set(j-1, primaryList.get(j));
                        primaryList.set(j, temp);

                    }

                }

            }



        //List for storing top 5
        List<List<String>> topFive = new ArrayList<>();



        for (int i = 0; i < primaryList.size() && i < 5 ; i++){

            System.out.println(primaryList.get(i).get(2));
            topFive.add(primaryList.get(i));

        }

        //Reverse our array so we can find the bottom 5 values
        Collections.reverse(primaryList);



        //List for storing top 5
        List<List<String>> bottomFive = new ArrayList<>();

        for (int i = 0; i < primaryList.size() && i < 5 ; i++){

            System.out.println(primaryList.get(i).get(2));
            bottomFive.add(primaryList.get(i));

        }





        });

    }


    public VBox getGridPane(){
        return vBox;
    }

    //likely will delete but keep for now
    public class Tuple<S, T, R> {
        public final S Label;
        public final T Current;
        public final R Change;

        public Tuple(S x, T y, R z) {
            this.Label = x;
            this.Current = y;
            this.Change = z;
        }


    }



    @Override
    public ArrayList<Monitor> getSelectedMonitors() {
        return null;
    }
}
