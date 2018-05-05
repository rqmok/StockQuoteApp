public abstract class Monitor {

    public enum monitorTypes {
        STOCK_QUOTE_WS_MONITOR
    }

    protected monitorTypes monitorType;
    protected ApplicationControllerInterface applicationController;

    public void update() {
        this.applicationController.reloadData();
    }

    public monitorTypes getMonitorType() {
        return this.monitorType;
    }

}