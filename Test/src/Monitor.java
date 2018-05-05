public abstract class Monitor {

    public enum monitorTypes {
        STOCK_QUOTE_WS_MONITOR
    }

    public ApplicationControllerInterface applicationController;


    public abstract void update();
    public enum getMonitorType{}

}