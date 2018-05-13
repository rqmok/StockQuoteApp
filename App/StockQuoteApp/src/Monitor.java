
public class Monitor {

    public enum monitorTypes {
        TABLE_MONITOR,
        GRAPH_MONITOR
    }

    private Stock stock;
    private monitorTypes monitorType;

    public monitorTypes getMonitorType() {
        return this.monitorType;
    }

    public Stock getStock() { return this.stock; }

    public void setStock(Stock stock) {
        if (stock != null) {
            this.stock = stock;
        }
    }

    public Monitor(monitorTypes type) {
        this.monitorType = type;
    }

    public Monitor(monitorTypes type, Stock stock) {
        this.monitorType = type;
        this.setStock(stock);
    }

}