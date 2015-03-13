package com.google.gwt.sample.stockwatcher.client;

import java.io.Serializable;

public class StockPrice implements Serializable {

    private static final long serialVersionUID = 1L;
    private String symbol;
    private double price;
    private double change;

    public StockPrice() {
        // no init
    }

    public StockPrice(String symbol, double price, double change) {
        this.symbol = symbol;
        this.price = price;
        this.change = change;
    }

    public double getChange() {
        return this.change;
    }

    public double getChangePercent() {
        return 100.0 * this.change / this.price;
    }

    public double getPrice() {
        return this.price;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
