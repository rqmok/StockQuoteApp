
import javafx.beans.property.SimpleStringProperty;

public class StockQuoteWSMonitor extends Monitor{


    private SimpleStringProperty symbolLabel;
    private SimpleStringProperty lastTradeLabel;
    private SimpleStringProperty dateLabel;
    private SimpleStringProperty timeLabel;

    public monitorTypes monitorType = monitorTypes.STOCK_QUOTE_WS_MONITOR;

    StockQuoteWSMonitor(SimpleStringProperty symbol, SimpleStringProperty lastTrade, SimpleStringProperty date, SimpleStringProperty time){

        this.symbolLabel = symbol;
        this.lastTradeLabel =lastTrade;
        this.dateLabel = date;
        this.timeLabel = time;

    }
    
}