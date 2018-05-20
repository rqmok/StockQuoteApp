import java.util.ArrayList;

public class Stock {
    //Array list that stores all the stock data associated with this particular stock
    private ArrayList<StockData> data = new ArrayList<>();
    private StockService stockService;

    //Constructor function
    //Takes in initial set of stockData
    public Stock(StockData data) {
        if (data == null) {
            System.out.println("Stock: Provided null StockData to constructor.");
            return;
        }

        this.data.add(data);
    }
    //Constructor function
    //Create a stock object with both its inital set of data along with its associated service
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
    //Return the stockData associated with this stock. This array list holds all the pieces of stock data the application
    //has retrieved since the creation of this stock object.
    public ArrayList<StockData> getStockData() {
        return data;
    }
    //Return most recent set of stock data
    public StockData getLastStockData() {
        return this.data.get(this.data.size() - 1);
    }
    //Add a another set of stockData to this stock
    public void addStockData(StockData data) {
        if (data != null) {
            this.data.add(data);
        }
    }
    //Return the stock service associated with this stock
    public StockService getStockService() {
        return this.stockService;
    }
    //Assign a stock service to this stock
    public void setStockService(StockService service) {
        if (service == null) {
            System.console().printf("Stock: Provided null stock service.");
            return;
        }

        this.stockService = service;
    }
    //Method for updating the stock
    //Using the label of this stock along with its associated stock service, a call will be made to the stock service
    //to retrieve the most recent set of stockData associated with this stock. This stock will subsequently be updated
    //with the retrieved data
    public void updateStockData() {
        if (this.stockService == null)
            return;
        //Get associated service
        StockService service = this.getStockService();
        //Get label of stock
        String symbol = this.getLastStockData().getQuoteData().get(0);
        try {
            //call the stock service and update data, provide no exception occurs
            this.addStockData(service.getStockData(symbol));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
