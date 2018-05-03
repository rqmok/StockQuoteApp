
import stockquoteservice.*;
import java.util.List;

public class StockQuoteWSAdapter implements StockService {

    private static final int INDEX_SYMBOL = 0, INDEX_LAST_TRADE = 1, INDEX_DATE = 2, INDEX_TIME = 3;

    StockQuoteWS SQservice;
    StockQuoteWSPortType SQPort;

    public StockQuoteWSAdapter() {
        // Initialise stock service once so it won't have to be initialised again (if object is not deleted)
        SQservice = new StockQuoteWS();
        SQPort = SQservice.getStockQuoteWSSOAP11PortHttp();
    }

    public StockData getStockData(String symbol) {

        // Get quote data from service
        List quoteData = SQPort.getQuote(symbol);

        // Create a new StockData object
        StockData data = new StockData(
                quoteData.get(INDEX_SYMBOL).toString(),
                quoteData.get(INDEX_LAST_TRADE).toString(),
                quoteData.get(INDEX_DATE).toString(),
                quoteData.get(INDEX_TIME).toString()
        );

        return data;
    }

}
