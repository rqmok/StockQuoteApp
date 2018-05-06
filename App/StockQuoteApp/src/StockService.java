
public interface StockService {

    public enum serviceTypes {
        STOCK_QUOTE_WS_SERVICE
    }

    StockData getStockData(String symbol) throws Exception;

    public serviceTypes getServiceType();

}
