
import stockquoteservice.*;
import java.util.List;

public class StockQuoteWSAdapter implements StockService {

    private serviceTypes serviceType = serviceTypes.STOCK_QUOTE_WS_SERVICE;

    private static final int INDEX_SYMBOL = 0, INDEX_LAST_TRADE = 1, INDEX_DATE = 2, INDEX_TIME = 3;

    StockQuoteWS SQservice;
    StockQuoteWSPortType SQPort;

    public StockQuoteWSAdapter() {
        // Initialise stock service once so it won't have to be initialised again (if object is not deleted)
        SQservice = new StockQuoteWS();
        SQPort = SQservice.getStockQuoteWSSOAP11PortHttp();
    }

    public StockData getStockData(String symbol) throws Exception {

        // Get quote data from service
        List quoteData = SQPort.getQuote(symbol);

        // Check if the symbol exists or not
        if (quoteData.get(INDEX_LAST_TRADE).toString().contains("Unset")
                && quoteData.get(INDEX_DATE).toString().contains("Unset")) {
            throw new Exception(quoteData.get(INDEX_SYMBOL).toString() + " cannot be found!");
        }

        // Check if an internal error has occured
        // There should be less than 4 elements in the returned array if internal error has occurred
        if (quoteData.size() < 4) {
            throw new Exception(quoteData.get(INDEX_SYMBOL).toString());
        }

        // Create a new StockData object
        StockData data = new StockData(
                quoteData.get(INDEX_SYMBOL).toString(),
                quoteData.get(INDEX_LAST_TRADE).toString(),
                quoteData.get(INDEX_DATE).toString(),
                quoteData.get(INDEX_TIME).toString()
        );

        return data;
    }

    public serviceTypes getServiceType() {
        return this.serviceType;
    }

}
