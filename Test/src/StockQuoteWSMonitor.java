
public class StockQuoteWSMonitor extends Monitor {

    public StockQuoteWSMonitor(ApplicationControllerInterface applicationControllerInterface){

        // Setup basic properties
        this.monitorType = monitorTypes.STOCK_QUOTE_WS_MONITOR;
        this.applicationController = applicationControllerInterface;

    }

}