
public class StockQuoteWSMonitor extends Monitor{

  //For now we only have one monitor type
    public monitorTypes monitorType = monitorTypes.STOCK_QUOTE_WS_MONITOR;

    private ApplicationControllerInterface applicationControllerInterface;

    public StockQuoteWSMonitor(ApplicationControllerInterface applicationControllerInterface){

        this.applicationControllerInterface = applicationControllerInterface;

    }


    @Override
    public void update() {

        this.applicationControllerInterface.reloadData();

    }
}