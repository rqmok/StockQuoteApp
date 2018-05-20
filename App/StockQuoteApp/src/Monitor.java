
public class Monitor {
    //Construct enums that allow us to differentiate between monitor types
    public enum monitorTypes {
        TABLE_MONITOR("Table Monitor"),
        GRAPH_MONITOR("Graph Monitor");

        private final String monitor;

        monitorTypes(String monitor) {
            this.monitor = monitor;
        }

        public String toString() {
            return this.monitor;
        }
    }

    private Stock stock;
    private monitorTypes monitorType;
    //Function that allows caller to determine the type of the monitor
    public monitorTypes getMonitorType() {
        return this.monitorType;
    }

    public Stock getStock() { return this.stock; }

    //Assign the monitor a stock object
    public void setStock(Stock stock) {
        if (stock != null) {
            this.stock = stock;
        }
    }

    public Monitor(monitorTypes type) {
        this.monitorType = type;
    }

    //Constructor function that takes in the type of monitor and its associated stock
    public Monitor(monitorTypes type, Stock stock) {
        this.monitorType = type;
        this.setStock(stock);
    }

}