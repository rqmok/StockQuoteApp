
import stockquoteservice.*;

import java.util.ArrayList;
import java.util.List;
//The purpose of this class is to take the information from the stock service and transform it so that it is usable by
//our application. This will be achieved by storing the data in a StockData object. This object will thus be used by the
//system to process the data. Furthermore, this class also exists to ensure that no erroneous or unexpected data is
//returned to this system
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
        if (quoteDataList.get(dataIndex.INDEX_LAST_TRADE).toString().contains("Unset")
                && quoteDataList.get(dataIndex.INDEX_DATE).toString().contains("Unset")) {
            throw new Exception(quoteDataList.get(dataIndex.INDEX_SYMBOL).toString() + " cannot be found!");
        }

        // Check if an internal error has occured
        // There should be less than 4 elements in the returned array if internal error has occurred
        if (quoteDataList.size() < 4) {
            throw new Exception(quoteDataList.get(dataIndex.INDEX_SYMBOL).toString());
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
    //Method that calls the stock service to retrieve the set of field names
    public ArrayList<String> getFieldNames() {
        List fieldNamesList = SQPort.getFieldNames().getReturn();

        return new ArrayList<String>(fieldNamesList);
    }

}
