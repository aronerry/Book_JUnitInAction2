package com.google.gwt.sample.stockwatcher.client;

import java.util.ArrayList;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.ui.FlexTable;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class StockWatcherTest extends GWTTestCase {

    /**
     * Must refer to a valid module that sources this class.
     */
    @Override
    public String getModuleName() {
        return "com.google.gwt.sample.stockwatcher.StockWatcher";
    }

    /**
     * Add as many tests as you like.
     */
    public void testStockPrices() {
        final StockWatcher stockWatcher = new StockWatcher();
        final ArrayList<String> stocks = stockWatcher.getStocks();
        stocks.add("S1");
        stocks.add("S2");
        stocks.add("S3");
        stockWatcher.refreshWatchList();
        // Setup an asynchronous event handler.
        Timer timer = new Timer() {
            private void assertStockPrice(FlexTable stocksFlexTable, int row, String price, String change) {
                assertEquals(price, stocksFlexTable.getText(row, 1));
                assertEquals(change, stocksFlexTable.getText(row, 2));
            }

            @Override
            public void run() {
                Throwable lastRefreshThrowable = stockWatcher.getLastRefreshThrowable();
                if (lastRefreshThrowable != null) {
                    this.throwUnchekedException(lastRefreshThrowable);
                }
                // do some validation logic
                FlexTable stocksFlexTable = stockWatcher.getStocksFlexTable();
                assertEquals("Symbol", stocksFlexTable.getText(0, 0));
                assertEquals("Price", stocksFlexTable.getText(0, 1));
                assertEquals("Change", stocksFlexTable.getText(0, 2));
                this.assertStockPrice(stocksFlexTable, 1, "101.00", "+0.01 (+0.01%)");
                this.assertStockPrice(stocksFlexTable, 2, "102.00", "+0.02 (+0.02%)");
                this.assertStockPrice(stocksFlexTable, 3, "103.00", "+0.03 (+0.03%)");
                // tell the test system the test is now done
                StockWatcherTest.this.finishTest();
            }

            private void throwUnchekedException(Throwable lastRefreshThrowable) {
                String msg = lastRefreshThrowable.toString();
                if (lastRefreshThrowable instanceof StatusCodeException) {
                    msg = "HTTP status code " + ((StatusCodeException) lastRefreshThrowable).getStatusCode() + ": " + msg;
                }
                throw new IllegalStateException(msg, lastRefreshThrowable);
            }
        };

        // Set a delay period significantly longer than the
        // event is expected to take.
        this.delayTestFinish(5000);

        // Schedule the event and return control to the test system.
        timer.schedule(100);
    }
}
