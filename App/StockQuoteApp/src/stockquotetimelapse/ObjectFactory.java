
package stockquotetimelapse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the stockquotetimelapse package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetStockQuoteSymbol_QNAME = new QName("http://StockQuoteService", "symbol");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: stockquotetimelapse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetFieldNamesResponse }
     * 
     */
    public GetFieldNamesResponse createGetFieldNamesResponse() {
        return new GetFieldNamesResponse();
    }

    /**
     * Create an instance of {@link GetSymbolsResponse }
     * 
     */
    public GetSymbolsResponse createGetSymbolsResponse() {
        return new GetSymbolsResponse();
    }

    /**
     * Create an instance of {@link GetStockQuote }
     * 
     */
    public GetStockQuote createGetStockQuote() {
        return new GetStockQuote();
    }

    /**
     * Create an instance of {@link GetStockQuoteResponse }
     * 
     */
    public GetStockQuoteResponse createGetStockQuoteResponse() {
        return new GetStockQuoteResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://StockQuoteService", name = "symbol", scope = GetStockQuote.class)
    public JAXBElement<String> createGetStockQuoteSymbol(String value) {
        return new JAXBElement<String>(_GetStockQuoteSymbol_QNAME, String.class, GetStockQuote.class, value);
    }

}
