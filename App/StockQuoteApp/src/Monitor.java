import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Monitor {

    public enum monitorTypes {
        STOCK_QUOTE_WS_MONITOR
    }

    private Stock stock;
    protected monitorTypes monitorType;

    public monitorTypes getMonitorType() {
        return this.monitorType;
    }

    public Stock getStock() { return this.stock; }

    public void setStock(Stock stock) {
        if (stock != null) {
            this.stock = stock;
        }
    }

    // Getters for constructing the table view
    public StringProperty symbolProperty() {
        return new SimpleStringProperty(this.stock.getStockData().getSymbol());
    }
    public StringProperty lastTradeProperty() { return new SimpleStringProperty(this.stock.getStockData().getLastTrade()); }
    public StringProperty dateProperty() {
        return new SimpleStringProperty(this.stock.getStockData().getDate());
    }
    public StringProperty timeProperty() {
        return new SimpleStringProperty(this.stock.getStockData().getTime());
    }

}