import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.ArrayList;


public class ListViewController extends Controller {

    private ListView<LineChart> listView;
    private ArrayList<Monitor> monitorList;

    public ListViewController(){

    }

    @Override
    public void update(ArrayList<Monitor> monitors) {
        if (monitors == null) {
            return;
        }

        //Empty out the list of monitors
        monitorList.clear();

        monitorList = new ArrayList<>();

        listView = new ListView<>();

        //Create an observable list of LineCharts. This is what our listiew will display
        ObservableList<LineChart> stockCharts = FXCollections.observableArrayList();

        //Make a list of all the graph monitors
        for (Monitor monitor : monitors) {
            if (monitor.getMonitorType() == Monitor.monitorTypes.GRAPH_MONITOR) {
                monitorList.add(monitor);
            }
        }

        //Now we must create a linechart for each monitor in our list
        //Start a counter which will allow us to assign id's to the Linecharts we create
        int i = 0;
        //iterate through the monitors
        for (Monitor graphMonitor: monitorList){
            //Create the axis of the linechart
            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            //Create a line chart and assign it's axis
            LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

            //Give the line chart an ID
            String chartID = Integer.toBinaryString(i);
            lineChart.setId(chartID);

            //Create a series. We will fill this with data from the StockData class
            XYChart.Series<Number, Number> series = new LineChart.Series<>();

            //Get the collection of stock data for the specific stock item corresponding to our monitor
            ArrayList<StockData> stockDataList = graphMonitor.getStock().getStockData();
            //For each individual piece of stock data, add to the series
            for(StockData stockData: stockDataList){
                ArrayList<String> quoteData = stockData.getQuoteData();
                ArrayList<String> fieldData = stockData.getFieldNames();
                int x = Integer.parseInt(quoteData.get(3));
                int y = Integer.parseInt(quoteData.get(1));
                String title = fieldData.get(0);
                String xCord = fieldData.get(3);
                String yCord = fieldData.get(1);
                //Create a new data set with the extracted info
                XYChart.Data<Number, Number> data = new LineChart.Data<>(x, y);
                //Add the data set to the series
                series.getData().add(data);
            }
            //Add the series to the LineChart
            lineChart.getData().add(series);
            //Set the size limits
            lineChart.setMaxSize(800,200);
            //Add the LineChart to our observable list
            stockCharts.add(lineChart);
            //iterate counter
            i ++;

        }
        //return add the observable list into the ListView
        listView.setItems(stockCharts);

    }

    public ListView<LineChart> getListView(){return listView;
    }

    @Override
    public ArrayList<Monitor> getSelectedMonitors() {

        return null;
    }
}