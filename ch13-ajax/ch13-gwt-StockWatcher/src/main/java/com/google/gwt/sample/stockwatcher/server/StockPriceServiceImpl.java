package com.google.gwt.sample.stockwatcher.server;

import com.google.gwt.sample.stockwatcher.client.StockPrice;
import com.google.gwt.sample.stockwatcher.client.StockPriceService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class StockPriceServiceImpl extends RemoteServiceServlet implements StockPriceService {

    private static final long serialVersionUID = 1L;
    private static final double BASE_FIXTURE = 100.0; // $100.00

    public StockPrice[] getPrices(String[] symbols) {
        StockPrice[] prices = new StockPrice[symbols.length];
        for (int i = 0; i < symbols.length; i++) {
            prices[i] = new StockPrice(symbols[i], BASE_FIXTURE + i + 1, ((double) i + 1) / 100);
        }
        return prices;
    }

}