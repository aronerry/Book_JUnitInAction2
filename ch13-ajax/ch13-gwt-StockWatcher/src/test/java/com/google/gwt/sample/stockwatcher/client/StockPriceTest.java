package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class StockPriceTest extends GWTTestCase {

    /**
     * Must refer to a valid module that sources this class.
     */
    @Override
    public String getModuleName() {
        return "com.google.gwt.sample.stockwatcher.StockWatcher";
    }

    /**
     * Verify that the instance fields in the StockPrice class are set
     * correctly.
     */
    public void testStockPrice3ArgConstructor() {
        String symbol = "XYZ";
        double price = 70.0;
        double change = 2.0;
        double changePercent = 100.0 * change / price;

        StockPrice sp = new StockPrice(symbol, price, change);
        assertNotNull(sp);
        assertEquals(symbol, sp.getSymbol());
        assertEquals(price, sp.getPrice(), 0.001);
        assertEquals(change, sp.getChange(), 0.001);
        assertEquals(changePercent, sp.getChangePercent(), 0.001);
    }
}
