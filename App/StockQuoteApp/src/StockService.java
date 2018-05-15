import java.util.ArrayList;

public interface StockService {

    enum serviceTypes {
        STOCK_QUOTE_WS_SERVICE,
        STOCK_QUOTE_TLS_SERVICE
    }

    StockData getStockData(String symbol) throws Exception;

    serviceTypes getServiceType();

    ArrayList<String> getFieldNames();

}
