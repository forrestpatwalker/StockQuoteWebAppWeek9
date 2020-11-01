package com.origamisoftware.teach.advanced.apps.stockquote;

import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.model.database.QuoteDAO;
import com.origamisoftware.teach.advanced.util.DatabaseInitializationException;
import com.origamisoftware.teach.advanced.util.DatabaseUtils;
import com.origamisoftware.teach.advanced.xml.Stocks;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;

/**
 * A basic app that shows how to marshall and unmarshal XML instances.
 *
 * @author Forrest Walker
 */
public class JAXBApp {

    private static final String XML_FILE = "./src/main/resources/xml/stock_info.xml";


    /**
     * Run the JAXB application.
     * <p/>
     *
     * @param args potentially contains a list of arguments to be used in the application
     * -- empty for this particular application
     * @throws javax.xml.bind.JAXBException
     * @throws com.origamisoftware.teach.advanced.util.DatabaseInitializationException
     */
    public static <QuoteDAO> void main(String[] args) throws JAXBException, DatabaseInitializationException {
        //initialize database
        DatabaseUtils.initializeDatabase(DatabaseUtils.initializationFile);
        System.out.println(DatabaseUtils.initializationFile);

        // here is how to go from XML to Java
        JAXBContext jaxbContext = JAXBContext.newInstance(Stocks.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Stocks stocks = (Stocks) unmarshaller.unmarshal(new File(XML_FILE));

        //loop through each stock in the list of stocks returned from the unmarshaller
        for (int i = 0; i < stocks.getStock().size(); i++){
            //create new quote from each stock in stocks
//            QuoteDAO quote = new QuoteDAO(stocks.getStock().get(i)); Cant Figure this one out

            //open hibernate session and persist each quote, then close session
            Session session = DatabaseUtils.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
//            session.persist(quote); Requirement above not implemented
            transaction.commit();
            session.flush();
            session.close();
        }

        // here is how to go from Java to XML
        JAXBContext context = JAXBContext.newInstance(Stocks.class);
        Marshaller marshaller = context.createMarshaller();
        //for pretty-print XML in JAXB
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(stocks, System.out);
    }

}

