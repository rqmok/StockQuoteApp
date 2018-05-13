import java.util.ArrayList;

public class Stock {

    private ArrayList<StockData> data = new ArrayList<>();
    private StockService stockService;

    public Stock(StockData data) {
        if (data == null) {
            System.out.println("Stock: Provided null StockData to constructor.");
            return;
        }

        this.data.add(data);
    }

    public Stock(StockData data, StockService service) {
        if (data == null) {
            System.out.println("Stock: Provided null StockData to constructor.");
            return;
        }

        if (service == null) {
            System.out.println("Stock: Provided null StockService to constructor.");
            return;
        }

        this.data.add(data);
        this.stockService = service;
    }

    public ArrayList<StockData> getStockData() {
        return data;
    }

    public StockData getLastStockData() {
        return this.data.get(this.data.size() - 1);
    }

    public void addStockData(StockData data) {
        if (data != null) {
            this.data.add(data);
        }
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

    public void updateStockData() {
        if (this.stockService == null)
            return;

        StockService service = this.getStockService();
        String symbol = this.getLastStockData().getQuoteData().get(0);
        try {
            this.addStockData(service.getStockData(symbol));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
