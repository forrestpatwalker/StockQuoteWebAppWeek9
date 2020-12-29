package com.origamisoftware.teach.advanced.servlet;

import com.origamisoftware.teach.advanced.model.StockQuote;
import com.origamisoftware.teach.advanced.services.ServiceFactory;
import com.origamisoftware.teach.advanced.services.StockService;
import com.origamisoftware.teach.advanced.services.StockServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StockSearchServlet extends HttpServlet {

    private static final String STOCK_SYMBOL = "stockSymbol";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StockService stockService = ServiceFactory.getStockService();
        String symbol = request.getParameter(STOCK_SYMBOL);
        String startDate = request.getParameter(START_DATE);
        String endDate = request.getParameter(END_DATE);
        List<StockQuote> stockQuoteList = null;
        try {
            stockQuoteList = stockService.getQuote(symbol, parseDate(startDate), parseDate(endDate));
        } catch (ParseException | StockServiceException e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession();

        session.setAttribute("quotes", stockQuoteList);

        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher =
                servletContext.getRequestDispatcher("/stockquoteResults.jsp");
        dispatcher.forward(request, response);

    }

    private Calendar parseDate(String strDate) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = simpleDateFormat.parse(strDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}

