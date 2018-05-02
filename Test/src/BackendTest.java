import stockquoteservice.*;

import java.util.List;

import javax.xml.ws.Service;

public class BackendTest {

    public static void main(String[] args) throws Exception {

        StockQuoteWS SQservice = new StockQuoteWS();
        StockQuoteWSPortType SQPort = SQservice.getStockQuoteWSSOAP11PortHttp();

        String[] testStocks = {"CBA", "NAB", "BHP"}; // Array of stock symbols to test with

        List fieldNames = SQPort.getFieldNames().getReturn();
        while (true) {
            for (String stock : testStocks) {
                List quoteData = SQPort.getQuote(stock);
                int i = 0;
                for (Object fieldName : fieldNames) {
                    System.out.println(fieldName + ": " + quoteData.get(i++));
                }
                System.out.println();
            }
            System.out.println("######################");
            long SleepTime = 60 * 1000L;
            Thread.sleep(SleepTime);
        }
    }


}
