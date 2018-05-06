public class StockUpdaterClock {

    private UpdateStockDataDelegate updateStockDataDelegate;

    // Interval for update stock data
    private long intervalInSeconds;

    public StockUpdaterClock(UpdateStockDataDelegate updateStockDataDelegate, long intervalInSeconds) {
        this.updateStockDataDelegate = updateStockDataDelegate;
        this.intervalInSeconds = intervalInSeconds;
    }

    public void beginUpdates() {
        // Use an asynchronous thread to update.
        // It it is not asynchronous, the entire program freezes because of the while loop
        new Thread(() -> {
            while (true) {
                updateStockDataDelegate.updateStockData();

                try {
                    Thread.sleep(intervalInSeconds * 1000);
                } catch (InterruptedException e) {
                    continue;
                }
            }
        }).start();
    }

}
