public class StockUpdaterClock implements Runnable {

    private UpdateStockDataDelegate updateStockDataDelegate;

    // Interval for update stock data
    private long intervalInSeconds;

    // The stock service that needs to be updated
    private StockService.serviceTypes serviceType;

    public StockUpdaterClock(UpdateStockDataDelegate updateStockDataDelegate, long intervalInSeconds, StockService.serviceTypes serviceType) {
        this.updateStockDataDelegate = updateStockDataDelegate;
        this.intervalInSeconds = intervalInSeconds;
        this.serviceType = serviceType;
    }

    public void run() {
        while (true) {
            updateStockDataDelegate.updateStockData(serviceType);

            try {
                Thread.sleep(intervalInSeconds * 1000);
            } catch (InterruptedException e) {
                continue;
            }
        }
    }

}
