package com.origamisoftware.teach.advanced.model.database;

import com.origamisoftware.teach.advanced.xml.Stock;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Models the quote table
 */
@Entity
@Table(name = "quote", schema = "", catalog = "stocks")
public class QuoteDAO implements DatabasesAccessObject {
    private int id;
    private Timestamp time;
    private BigDecimal price;
    private StockSymbolDAO stockSymbolBySymbolId;

    public QuoteDAO(){

    }

    public QuoteDAO(Stock stock){
        this.stockSymbolBySymbolId = new StockSymbolDAO(stock);
        this.time = convertStringToTimestamp(stock.getTime());
        this.price = convertStringToBigDecimal(stock.getPrice());
    }

    @Id
    @Column(name = "id",  nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Digits(integer=5, fraction=2)
    @Column(name = "price", nullable = false, insertable = true, updatable = true, precision = 0)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuoteDAO quoteDAO = (QuoteDAO) o;

        if (id != quoteDAO.id) return false;
        if (price != quoteDAO.price) return false;
        if (time != null ? !time.equals(quoteDAO.time) : quoteDAO.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + price.hashCode();
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "symbol_id", referencedColumnName = "id", nullable = false)
    public StockSymbolDAO getStockSymbolBySymbolId() {
        return stockSymbolBySymbolId;
    }

    public void setStockSymbolBySymbolId(StockSymbolDAO stockSymbolBySymbolId) {
        this.stockSymbolBySymbolId = stockSymbolBySymbolId;
    }

    public static Timestamp convertStringToTimestamp(String dateEntered){
        /**
         * Create a new instance of SimpleDateFormat that will be used to
         * parse the string arguments to obtain desired start and end dates
         */
        SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try {
            date = simpleDateFormatter.parse(dateEntered);
        } catch (ParseException ex) {
            System.out.println("Incorrect format for date. System will exit.");
            System.exit(1);
        }
        Timestamp convertedDate = new Timestamp(date.getTime());

        return convertedDate;
    }

    public static BigDecimal convertStringToBigDecimal(String priceEntered){

        return new BigDecimal(priceEntered);
    }
}
