import java.util.ArrayList;

public interface StockService {

    enum serviceTypes {
        STOCK_QUOTE_WS_SERVICE("Live Web Service"),
        STOCK_QUOTE_TLS_SERVICE("Time Lapse Service");

        private final String service;

        serviceTypes(String service) {
            this.service = service;
        }

        public String getService() {
            return this.service;
        }
    }

    StockData getStockData(String symbol) throws Exception;

    serviceTypes getServiceType();

    ArrayList<String> getFieldNames();

}
