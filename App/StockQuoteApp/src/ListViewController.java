import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;


public class ListViewController extends Controller {

    private ListView<LineChart> listView;
    private ArrayList<Monitor> monitorList;
    Map<String, Monitor> monitorDictionary = new HashMap<String, Monitor>();

    public ListViewController(){
        listView = new ListView<>();
    }

    public void update(ArrayList<Monitor> monitors) {
        if (monitors == null) {
            return;
        }

        Platform.runLater(() -> {
            // Clear current list
            listView.getItems().clear();

            // Reset previous data
            monitorList = new ArrayList<>();
            monitorDictionary.clear();

            //Create an observable list of LineCharts. This is what our listview will display
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
            for (Monitor graphMonitor : monitorList) {
                //Get data fields so we can name our graph axes
                ArrayList<String> fields = graphMonitor.getStock().getLastStockData().getFieldNames();
                //Get stock name so we can give our graph a title
                ArrayList<String> quotesData = graphMonitor.getStock().getLastStockData().getQuoteData();
                //Create variables with necessary data
                String title = quotesData.get(0);
                String xCord = fields.get(3);
                String yCord = fields.get(1);

                //To set the scale for our graph, we need to recover the original x/y value because we need a consistent
                //set of x/y values to delineate the size of the axis
                ArrayList<String> origInfo = graphMonitor.getStock().getStockData().get(0).getQuoteData();
                //Extract said values
                int xValue = toMins(origInfo.get(3));
                int yValue = Double.valueOf(origInfo.get(1)).intValue();

                //Create the axis of the linechart
                NumberAxis xAxis = new NumberAxis(xValue-5,xValue+50,1);
                xAxis.setLabel(xCord);
                NumberAxis yAxis = new NumberAxis(yValue-100,yValue+100,5);
                yAxis.setLabel(yCord);

                //Create a line chart and assign it's axis
                LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
                
                //Give the chart a title
                lineChart.setTitle(title);

                //Give the line chart an ID
                //String chartID = Integer.toBinaryString(i);
                String chartID = Integer.toString(i);
                lineChart.setId(chartID);
                monitorDictionary.put(chartID, graphMonitor);

                //Create a series. We will fill this with data from the StockData class
                XYChart.Series<Number, Number> series = new LineChart.Series<>();

                //Get the collection of stock data for the specific stock item corresponding to our monitor
                ArrayList<StockData> stockDataList = graphMonitor.getStock().getStockData();
                //For each individual piece of stock data, add to the series
                for (StockData stockData : stockDataList) {
                    ArrayList<String> quoteData = stockData.getQuoteData();
                    ArrayList<String> fieldData = stockData.getFieldNames();
                    //int x = Integer.parseInt(quoteData.get(3));
                    //int y = Integer.parseInt(quoteData.get(1));

                    int x = toMins(quoteData.get(3));
                    int y = Double.valueOf(quoteData.get(1)).intValue();

                 

                    //Create a new data set with the extracted info
                    XYChart.Data<Number, Number> data = new LineChart.Data<>(x, y);
                    //Add the data set to the series
                    series.getData().add(data);
                }
                //Add the series to the LineChart
                lineChart.getData().add(series);
                //Set the size limits
                lineChart.setMaxSize(800, 200);
                //Add the LineChart to our observable list
                stockCharts.add(lineChart);
                //iterate counter
                i++;

            }

            // Return add the observable list into the ListView
            listView.setItems(stockCharts);
        });

    }

    public ListView<LineChart> getListView(){
        return listView;
    }

    //THIS IS A TEMPORARY FUNCTION FOR CONVERTING TO THE TIME FORMAT TO AN INT
    private static int toMins(String s) {
        String[] hourMin = s.split(":");

        int hour = Integer.parseInt(hourMin[0]);
        int mins = Integer.parseInt(hourMin[1]);
        int hoursInMins = hour * 60;

        return hoursInMins + mins;
    }


    public ArrayList<Monitor> getSelectedMonitors() {
        ArrayList<Monitor> selectedMonitors = new ArrayList<>();
        for (LineChart lineChart : listView.getSelectionModel().getSelectedItems()) {
            String ID = lineChart.getId();

            Monitor monitor = monitorDictionary.get(ID);

            selectedMonitors.add(monitor);
        }

        return selectedMonitors;
    }
}