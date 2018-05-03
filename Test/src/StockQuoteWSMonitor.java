
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

    public String getSymbolLabelText(){
        return this.symbolLabel.get();
    }
    public String getLastTradeLabelText(){
        return this.lastTradeLabel.get();
    }
    public void setLastTradeLabelText(String lastTrade){
        this.lastTradeLabel.set(lastTrade);
    }
    public String getDateLabelText(){
        return this.dateLabel.get();
    }
    public void setDateLabelText(String date){
        this.dateLabel.set(date);
    }
    public String getTimeLabelText(){
        return this.timeLabel.get();
    }
    public void setTimeLabelText(String time){
        this.timeLabel.set(time);
    }
    public void setAllLabelText(String lastTrade, String date ,String time){
        this.lastTradeLabel.set(lastTrade);
        this.dateLabel.set(date);
        this.timeLabel.set(time);
    }


}