import java.util.ArrayList;

public class StockData {

    private ArrayList<String> fieldNames;
    private ArrayList<String> quoteData;

    public StockData(ArrayList<String> fieldNames, ArrayList<String> quoteData) {
        // Check for empty parameters
        if (fieldNames.size() < 0 || quoteData.size() < 0)
            return;

        this.fieldNames = fieldNames;
        this.quoteData = quoteData;
    }

    public ArrayList<String> getFieldNames() {
        return fieldNames;
    }

    public ArrayList<String> getQuoteData() {
        return quoteData;
    }
}
