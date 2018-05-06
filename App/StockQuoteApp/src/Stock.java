
public class Stock {

    private StockData data;
    private StockService stockService;

    public Stock(StockData stockData) {
        if (stockData == null) {
            System.console().printf("Stock: Provided null StockData to constructor.");
            return;
        }

        this.data = stockData;
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
