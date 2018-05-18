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

    class dataIndex {
        public static final int INDEX_SYMBOL = 0;
        public static final int INDEX_LAST_TRADE = 1;
        public static final int INDEX_DATE = 2;
        public static final int INDEX_TIME = 3;
        public static final int INDEX_CHANGE = 4;
        public static final int INDEX_OPEN = 5;
        public static final int INDEX_DAY_HIGH = 6;
        public static final int INDEX_DAY_LOW = 7;
        public static final int INDEX_VOLUME = 8;
    };

    StockData getStockData(String symbol) throws Exception;

    serviceTypes getServiceType();

    ArrayList<String> getFieldNames();

}
