
public class Monitor {

    public enum monitorTypes {
        TABLE_MONITOR("Table Monitor"),
        GRAPH_MONITOR("Graph Monitor");

        private final String monitor;

        monitorTypes(String monitor) {
            this.monitor = monitor;
        }

        public String getMonitor() {
            return this.monitor;
        }
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