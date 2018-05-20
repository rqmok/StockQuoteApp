import java.util.ArrayList;
//The StockData objects will be responsible for storing the data retrieved from the stock services. After the adapter
//receives information from a stock service, a Stock Data object will be created using that information. Thus, the data
//returned from the services is processed in our system in the form of this object.
public class StockData {
    //variable for storing data retrieved from the stock service
    private ArrayList<String> fieldNames;
    private ArrayList<String> quoteData;
    //Constructor function to create the stock data object. It will take in the list of quotes returned by the service
    //along with the list of field names.
    public StockData(ArrayList<String> fieldNames, ArrayList<String> quoteData) {
        // Check for empty parameters
        if (fieldNames.size() < 0 || quoteData.size() < 0)
            return;

        this.fieldNames = fieldNames;
        this.quoteData = quoteData;
    }
    //Getter to return field names
    public ArrayList<String> getFieldNames() {
        return fieldNames;
    }
    //Getter to return quote data
    public ArrayList<String> getQuoteData() {
        return quoteData;
    }
}
