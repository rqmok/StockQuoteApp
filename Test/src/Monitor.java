public abstract class Monitor {

    public enum monitorTypes {
        STOCK_QUOTE_WS_MONITOR
    }

    public abstract String getSymbolLabelText();
    public abstract String getLastTradeLabelText();
    public abstract void setLastTradeLabelText(String lastTrade);
    public abstract String getDateLabelText();
    public abstract void setDateLabelText(String date);
    public abstract String getTimeLabelText();
    public abstract void setTimeLabelText(String time);

    public abstract void setAllLabelText(String lastTrade, String date ,String time);


}