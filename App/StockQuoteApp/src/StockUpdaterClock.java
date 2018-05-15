public class StockUpdaterClock implements Runnable {

    private UpdateStockDataDelegate updateStockDataDelegate;

    // Interval for update stock data
    private long intervalInSeconds;

    public StockUpdaterClock(UpdateStockDataDelegate updateStockDataDelegate, long intervalInSeconds) {
        this.updateStockDataDelegate = updateStockDataDelegate;
        this.intervalInSeconds = intervalInSeconds;

    }

    public void run() {
        while (true) {
            updateStockDataDelegate.updateStockData();

            try {
                Thread.sleep(intervalInSeconds * 1000);
            } catch (InterruptedException e) {
                continue;
            }
        }
    }

}
