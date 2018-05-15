import java.util.ArrayList;

public class Model {

    private ArrayList<Stock> stocks;
    private ArrayList<StockService> services;
    private ArrayList<Monitor> monitors;

    public Model() {
        // Initialise array lists for holding data
        stocks = new ArrayList<>();
        services = new ArrayList<>();
        monitors = new ArrayList<>();

        // Create new services
        services.add(new StockQuoteWSAdapter());
        services.add(new StockQuoteTLSAdapter());
    }

    public Monitor addMonitor(String symbol, StockService.serviceTypes serviceType, Monitor.monitorTypes monitorType) {
        // Check for empty symbol text
        if (symbol == null || symbol.length() == 0) {
            System.out.println("Model: symbol text is empty or null.");
            return null;
        }

        // Check if service exists
        StockService service = findService(serviceType);
        if (service == null) {
            System.out.println("Model: service not found.");
            return null;
        }

        // As the service for data
        StockData data = null;
        try {
            data = service.getStockData(symbol);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

        // Check if data was received
        if (data == null) {
            System.out.println("Model: service returned null data");
            return null;
        }

        // Data was successfully retrieved.
        // Check if stock already exists
        Stock stock = findStock(symbol);
        if (stock == null) {
            // Stock does not exist. Create a new one
            stock = new Stock(data);

            // Keep track of stock
            stocks.add(stock);

            // attach the stock service to this new stock
            stock.setStockService(service);
        }

        // Finally, create a new monitor
        Monitor monitor = new Monitor(monitorType, stock);

        // Keep track of the new monitor
        monitors.add(monitor);

        return monitor;
    }

    public void removeMonitor(Monitor monitor) {
        if (monitor == null) {
            return;
        }

        this.monitors.remove(monitor);
    }

    public ArrayList<Monitor> getMonitors() {
        return monitors;
    }

    public void updateStockData() {
        for (Stock stock : stocks) {
            stock.updateStockData();
        }
    }

    // ****** Helper Functions ******
    // Helper function to find a stock
    private Stock findStock(String stockSymbol) {
        for (Stock stock : stocks) {
            if (stock.getLastStockData().getQuoteData().get(0).contains(stockSymbol)) {
                return stock;
            }
        }

        return null;
    }

    // Helper function to find a service
    private StockService findService(StockService.serviceTypes serviceType) {
        for (StockService currentService : services) {
            if (currentService.getServiceType() == serviceType) {
                return currentService;
            }
        }

        return null;
    }

    // Helper function to get field names
    public ArrayList<String> getFieldNames() {
        StockService service = findService(StockService.serviceTypes.STOCK_QUOTE_WS_SERVICE);
        return service.getFieldNames();
    }

}
