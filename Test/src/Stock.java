import java.util.ArrayList;

public class Stock {

    private StockData data;
    private ArrayList<Monitor> monitors;
    private StockService stockService;

    public Stock(StockData stockData) {
        if (stockData == null) {
            System.console().printf("Stock: Provided null StockData to constructor.");
            return;
        }

        this.data = stockData;
        this.monitors = new ArrayList<Monitor>();
    }

    public StockData getStockData() {
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

    public void setStockData(StockData stockData) {
        if (stockData != null) {
            this.data = stockData;
        }
    }

    public Monitor getMonitor(int index) {
        if (index >= monitors.size()) {
            System.console().printf("Stock: getMonitor: Index is out of range.");
            return null;
        }

        return monitors.get(index);
    }

    public ArrayList<Monitor> getAllMonitors() {
        // Create a copy of the array list
        ArrayList<Monitor> retMonitors = new ArrayList<Monitor>();
        for (Monitor monitor : this.monitors) {
            retMonitors.add(monitor);
        }

        // return the copy
        return retMonitors;
    }

    // TODO: implement add and remove monitor public functions
    public void addMonitor(Monitor monitor) {
        // Check monitor is not null
        if (monitor == null) {
            System.console().printf("Stock: Cannot add null monitor.");
            return;
        }

        this.monitors.add(monitor);
    }

    public void removeMonitor(Monitor monitor) {
        // Check monitor is not null
        if (monitor == null) {
            System.console().printf("Stock: Cannot add null monitor.");
            return;
        }

        // Use array list helper function to remove monitor
        this.monitors.remove(monitor);
    }

    public StockService getStockService() {
        return this.stockService;
    }

    public void setStockService(StockService service) {
        if (service == null) {
            System.console().printf("Stock: Provided null stock service.");
            return;
        }

        this.stockService = service;
    }

}
