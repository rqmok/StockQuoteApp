
import stockquotetimelapse.*;

import java.util.ArrayList;
import java.util.List;
//The purpose of this class is to take the information from the stock service and transform it so that it is usable by
//our application. This will be achieved by storing the data in a StockData object. This object will thus be used by the
//system to process the data. Furthermore, this class also exists to ensure that no erroneous or unexpected data is
//returned to this system
public class StockQuoteTLSWrapper implements StockService {

    private serviceTypes serviceType = serviceTypes.STOCK_QUOTE_TLS_SERVICE;
    //Set up to call the stock service
    StockQuoteTimeLapseService SQservice;
    StockQuoteTimeLapseServicePortType SQPort;

    public StockQuoteTLSWrapper() {
        SQservice = new StockQuoteTimeLapseService();
        SQPort = SQservice.getStockQuoteTimeLapseServiceHttpSoap11Endpoint();
    }
    //makes a call the service to retrieve the data
    public StockData getStockData(String symbol) throws Exception {
        //Ensure valid symbol is provided
        if (symbol == null || symbol.length() <= 0) {
            throw new Exception("StockQuoteTLSWrapper: Provided empty symbol");
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
        //Convert to dollars
        for (int index : dollarIndexes) {
            Double newValue = Double.valueOf(quoteData.get(index)) / 100;
            quoteData.set(index, String.valueOf(newValue));
        }

        // Create new stock data with array lists
        StockData data = new StockData(fieldNames, quoteData);

        return data;
    }
    //Getter for returning service type
    public serviceTypes getServiceType() {
        return this.serviceType;
    }
    //call service to get set of field names
    public ArrayList<String> getFieldNames() {
        List fieldNamesList = SQPort.getFieldNames().getReturn();

        return new ArrayList<String>(fieldNamesList);
    }
    //call service to get set of symbols
    public ArrayList<String> getSymbols() {
        List symbolsList = SQPort.getSymbols().getReturn();

        return new ArrayList<String>(symbolsList);
    }
}
