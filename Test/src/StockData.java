
public class StockData {

    private String symbol;
    private float lastTrade;
    private String date;
    private String time;

    public StockData(String symbol, float lastTrade, String date, String time) {
        // Check no null values
        if (symbol == null || date == null || time == null) {
            // Print message and do not create an object
            System.console().printf("StockData: Null value provided in constructor.");
            return;
        }

        // Check float value
        if (lastTrade < 0) {
            // Print message and do not create an object
            System.console().printf("StockData: lastTrade is negative.");
            return;
        }

        // Values are fine. Create the object.
        this.symbol = symbol;
        this.lastTrade = lastTrade;
        this.date = date;
        this.time = time;
    }

    public String getSymbol() { return this.symbol; }
    public float getLastTrade() { return this.lastTrade; }
    public String getDate() { return this.date; }
    public String getTime() { return this.time; }

}
