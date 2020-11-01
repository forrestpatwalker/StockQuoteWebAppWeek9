package com.origamisoftware.teach.advanced.servlet;

import com.origamisoftware.teach.advanced.model.StockQuote;
import org.junit.Before;
import org.junit.Test;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class to test the StockSearchServlet class
 *
 * @author Forrest Walker
 */
public class StockSearchServletTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static ServletContext context;

    @Before
    public void setUp() {

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        context = mock(ServletContext.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        session = new FakeSession();

        when(request.getParameter("symbol")).thenReturn("AAPL");
        when(request.getParameter("from")).thenReturn("2018-10-01");
        when(request.getParameter("until")).thenReturn("2018-11-05");
        when(request.getParameter("interval")).thenReturn("daily");

        when(request.getSession()).thenReturn(session);

        when(context.getRequestDispatcher("/stockquoteResults.jsp")).thenReturn(dispatcher);
    }

    @Test
    public void testDoPostPositive() throws Exception {

        StockSearchServlet stockSearch = new StockSearchServlet() {
            public ServletContext getServletContext() {
                return context;
            }
        };

        stockSearch.doPost(request, response);

        List<StockQuote> stockQuoteList = (List<StockQuote>) session.getAttribute("stockQuoteList");

        assertEquals("stockQuoteList has 30 items", 30, stockQuoteList.size());
    }

    @Test
    public void testDoPostNegative() throws Exception {

        StockSearchServlet stockSearch = new StockSearchServlet() {
            public ServletContext getServletContext() {
                return context;
            }
        };

        stockSearch.doPost(request, response);

        List<StockQuote> stockQuoteList = (List<StockQuote>) session.getAttribute("stockQuoteList");

        assertFalse("stockQuoteList has no items", stockQuoteList.size() == 0);
    }

}
