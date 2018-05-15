
import stockquoteservice.*;

import java.util.ArrayList;
import java.util.List;

public class StockQuoteWSAdapter implements StockService {

    private serviceTypes serviceType = serviceTypes.STOCK_QUOTE_WS_SERVICE;

    StockQuoteWS SQservice;
    StockQuoteWSPortType SQPort;

    public StockQuoteWSAdapter() {
        // Initialise stock service once so it won't have to be initialised again (if object is not deleted)
        SQservice = new StockQuoteWS();
        SQPort = SQservice.getStockQuoteWSSOAP11PortHttp();
    }

    public StockData getStockData(String symbol) throws Exception {

        // Get quote data from service
        List quoteDataList = SQPort.getQuote(symbol);

        // Check if the symbol exists or not
        if (quoteDataList.get(1).toString().contains("Unset")
                && quoteDataList.get(2).toString().contains("Unset")) {
            throw new Exception(quoteDataList.get(0).toString() + " cannot be found!");
        }

        // Check if an internal error has occured
        // There should be less than 4 elements in the returned array if internal error has occurred
        if (quoteDataList.size() < 4) {
            throw new Exception(quoteDataList.get(0).toString());
        }

        // Construct array lists
        ArrayList<String> fieldNames = getFieldNames();
        ArrayList<String> quoteData = new ArrayList<String>(quoteDataList);

        // Create a new StockData object
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

}
