package com.google.gwt.sample.stockwatcher.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StockWatcher implements EntryPoint {

    private static final int REFRESH_INTERVAL = 5000; // ms
    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable stocksFlexTable;
    private HorizontalPanel addPanel = new HorizontalPanel();
    private TextBox newSymbolTextBox = new TextBox();
    private Button addStockButton = new Button("Add");
    private Label lastUpdatedLabel = new Label();
    private ArrayList<String> stocks = new ArrayList<String>();
    private StockPriceServiceAsync stockPriceSvc = GWT.create(StockPriceService.class);

    private Throwable lastRefreshThrowable;

    /**
     * Add stock to FlexTable. Executed when the user clicks the addStockButton
     * or presses enter in the newSymbolTextBox.
     */
    void addStock() {
        final String symbol = this.newSymbolTextBox.getText().toUpperCase().trim();
        this.newSymbolTextBox.setFocus(true);

        // Stock code must be between 1 and 10 chars that are numbers, letters,
        // or dots.
        if (!symbol.matches("^[0-9a-zA-Z\\.]{1,10}$")) {
            Window.alert("'" + symbol + "' is not a valid symbol.");
            this.newSymbolTextBox.selectAll();
            return;
        }

        this.newSymbolTextBox.setText("");

        // Don't add the stock if it's already in the table.
        if (this.getStocks().contains(symbol)) {
            return;
        }

        // Add the stock to the table.
        int row = this.getStocksFlexTable().getRowCount();
        this.getStocks().add(symbol);
        this.getStocksFlexTable().setText(row, 0, symbol);
        this.getStocksFlexTable().setWidget(row, 2, new Label());
        this.getStocksFlexTable().getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
        this.getStocksFlexTable().getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
        this.getStocksFlexTable().getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");

        // Add a button to remove this stock from the table.
        Button removeStockButton = new Button("x");
        removeStockButton.addStyleDependentName("remove");
        removeStockButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int removedIndex = StockWatcher.this.getStocks().indexOf(symbol);
                StockWatcher.this.getStocks().remove(removedIndex);
                StockWatcher.this.getStocksFlexTable().removeRow(removedIndex + 1);
            }
        });
        this.getStocksFlexTable().setWidget(row, 3, removeStockButton);

        // Get the stock price.
        this.refreshWatchList();

    }

    public Throwable getLastRefreshThrowable() {
        return this.lastRefreshThrowable;
    }

    ArrayList<String> getStocks() {
        return this.stocks;
    }

    FlexTable getStocksFlexTable() {
        if (this.stocksFlexTable == null) {
            this.stocksFlexTable = new FlexTable();
            this.stocksFlexTable.setText(0, 0, "Symbol");
            this.stocksFlexTable.setText(0, 1, "Price");
            this.stocksFlexTable.setText(0, 2, "Change");
            this.stocksFlexTable.setText(0, 3, "Remove");

            // Add styles to elements in the stock list table.
            this.stocksFlexTable.setCellPadding(6);
            this.stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
            this.stocksFlexTable.addStyleName("watchList");
            this.stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
            this.stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
            this.stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
        }
        return this.stocksFlexTable;
    }

    /**
     * Entry point method.
     */
    public void onModuleLoad() {
        // Assemble Add Stock panel.
        this.addPanel.add(this.newSymbolTextBox);
        this.addPanel.add(this.addStockButton);
        this.addPanel.addStyleName("addPanel");

        // Assemble Main panel.
        this.mainPanel.add(this.getStocksFlexTable());
        this.mainPanel.add(this.addPanel);
        this.mainPanel.add(this.lastUpdatedLabel);

        // Associate the Main panel with the HTML host page.
        RootPanel.get("stockList").add(this.mainPanel);

        // Move cursor focus to the input box.
        this.newSymbolTextBox.setFocus(true);

        // Setup timer to refresh list automatically.
        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                StockWatcher.this.refreshWatchList();
            }
        };
        refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

        // Listen for mouse events on the Add button.
        this.addStockButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                StockWatcher.this.addStock();
            }
        });

        // Listen for keyboard events in the input box.
        this.newSymbolTextBox.addKeyPressHandler(new KeyPressHandler() {
            public void onKeyPress(KeyPressEvent event) {
                if (event.getCharCode() == KeyCodes.KEY_ENTER) {
                    StockWatcher.this.addStock();
                }
            }
        });

    }

    void refreshWatchList() {
        // Initialize the service proxy.
        if (this.stockPriceSvc == null) {
            this.stockPriceSvc = GWT.create(StockPriceService.class);
        }

        // Set up the callback object.
        AsyncCallback<StockPrice[]> callback = new AsyncCallback<StockPrice[]>() {
            public void onFailure(Throwable caught) {
                StockWatcher.this.setLastRefreshThrowable(caught);
            }

            public void onSuccess(StockPrice[] result) {
                StockWatcher.this.updateTable(result);
            }
        };

        // Make the call to the stock price service.
        this.stockPriceSvc.getPrices(this.getStocks().toArray(new String[0]), callback);
    }

    void setLastRefreshThrowable(Throwable lastRefreshThrowable) {
        this.lastRefreshThrowable = lastRefreshThrowable;
    }

    void setStocks(ArrayList<String> stocks) {
        this.stocks = stocks;
    }

    void setStocksFlexTable(FlexTable stocksFlexTable) {
        this.stocksFlexTable = stocksFlexTable;
    }

    /**
     * Update a single row in the stock table.
     * 
     * @param price
     *            Stock data for a single row.
     */
    private void updateTable(StockPrice price) {
        // Make sure the stock is still in the stock table.
        if (!this.getStocks().contains(price.getSymbol())) {
            return;
        }

        int row = this.getStocks().indexOf(price.getSymbol()) + 1;

        // Format the data in the Price and Change fields.
        String priceText = NumberFormat.getFormat("#,##0.00").format(price.getPrice());
        NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
        String changeText = changeFormat.format(price.getChange());
        String changePercentText = changeFormat.format(price.getChangePercent());

        // Populate the Price and Change fields with new data.
        this.getStocksFlexTable().setText(row, 1, priceText);
        this.getStocksFlexTable().setWidget(row, 2, new Label());
        Label changeWidget = (Label) this.getStocksFlexTable().getWidget(row, 2);
        changeWidget.setText(changeText + " (" + changePercentText + "%)");

        // Change the color of text in the Change field based on its value.
        String changeStyleName = "noChange";
        if (price.getChangePercent() < -0.1f) {
            changeStyleName = "negativeChange";
        } else if (price.getChangePercent() > 0.1f) {
            changeStyleName = "positiveChange";
        }

        changeWidget.setStyleName(changeStyleName);
    }

    /**
     * Update the Price and Change fields all the rows in the stock table.
     * 
     * @param prices
     *            Stock data for all rows.
     */
    void updateTable(StockPrice[] prices) {
        for (StockPrice price : prices) {
            this.updateTable(price);
        }

        // Display timestamp showing last refresh.
        this.lastUpdatedLabel.setText("Last update : " + DateTimeFormat.getMediumDateTimeFormat().format(new Date()));

    }

}
