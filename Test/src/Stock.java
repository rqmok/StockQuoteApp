import java.util.ArrayList;

public class Stock {

    private StockData data;
    private ArrayList<Monitor> monitors;

    public Stock(StockData stockData) {
        if (stockData == null) {
            System.console().printf("Stock: Provided null StockData to constructor.");
            return;
        }

        this.data = stockData;
        this.monitors = new ArrayList<Monitor>();
    }

    public StockData getStockData() { return this.data; }

    public void setStockData(StockData stockData) {
        // Create a copy of the stock data
        StockData retStockData = new StockData(
                this.data.getSymbol(),
                this.data.getLastTrade(),
                this.data.getDate(),
                this.data.getTime()
        );

        // Return the copy so this.data cannot be modified.
        return retStockData;
    }

    public Monitor getMonitor(int index) {
        if (index >= monitors.size) {
            System.console().printf("Stock: getMonitor: Index is out of range.");
            return null;
        }

        return monitors.get(index);
    }

    public ArrayList<Monitor> getAllMonitors() {
        // Create a copy of the array list
        ArrayList<Monitor> retMonitors = new ArrayList<Monitor>();
        for (monitor in this.monitors) {
            retMonitors.append(monitor);
        }

        // return the copy
        return retMonitors;
    }

    // TODO: implement add and remove monitor public functions
    public void addMonitor(Monitor monitor) {}
    public void removeMonitor(Monitor monitor) {}

}
