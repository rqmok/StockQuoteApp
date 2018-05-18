
import stockquotetimelapse.*;

import java.util.ArrayList;
import java.util.List;

public class StockQuoteTLSAdapter implements StockService {

    private serviceTypes serviceType = serviceTypes.STOCK_QUOTE_TLS_SERVICE;

    StockQuoteTimeLapseService SQservice;
    StockQuoteTimeLapseServicePortType SQPort;

    public StockQuoteTLSAdapter() {
        SQservice = new StockQuoteTimeLapseService();
        SQPort = SQservice.getStockQuoteTimeLapseServiceHttpSoap11Endpoint();
    }

    public StockData getStockData(String symbol) throws Exception {

        if (symbol == null || symbol.length() <= 0) {
            throw new Exception("StockQuoteTLSAdapter: Provided empty symbol");
        }

        // Get the quote data from service
        List quoteDataList = SQPort.getStockQuote(symbol);

        // Setup array lists
        ArrayList<String> fieldNames = getFieldNames();
        ArrayList<String> quoteData = new ArrayList<>(quoteDataList);

        // Indexes that need to be converted to dollars from cents
        int dollarIndexes[] = {
            dataIndex.INDEX_LAST_TRADE,
            dataIndex.INDEX_CHANGE,
            dataIndex.INDEX_OPEN,
            dataIndex.INDEX_DAY_HIGH,
            dataIndex.INDEX_DAY_LOW,
            dataIndex.INDEX_VOLUME
        };

        for (int index : dollarIndexes) {
            Double newValue = Double.valueOf(quoteData.get(index)) / 100;
            quoteData.set(index, String.valueOf(newValue));
        }

        // Create new stock data with array lists
        StockData data = new StockData(fieldNames, quoteData);

        return data;
    }

    public serviceTypes getServiceType() {
        return this.serviceType;
    }

    public ArrayList<String> getFieldNames() {
        List fieldNamesList = SQPort.getFieldNames().getReturn();

        return new ArrayList<String>(fieldNamesList);
    }

    public ArrayList<String> getSymbols() {
        List symbolsList = SQPort.getSymbols().getReturn();

        return new ArrayList<String>(symbolsList);
    }
}
