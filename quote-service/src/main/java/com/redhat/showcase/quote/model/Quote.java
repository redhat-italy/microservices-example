package com.redhat.showcase.quote.model;

import org.springframework.data.annotation.Id;

public class Quote {

    /**
     * Example quote object:
     * <p>
     * [{"symbol":"AAPL","name":"Apple","lastTradeValue":11523.25}]
     */

    private String symbol;
    private String name;
    private double lastTradeValue;

    @Id
    public String id;

    public Quote() {
    }

    public Quote(String symbol, String name, double value) {
        this.symbol = symbol;
        this.name = name;
        this.lastTradeValue = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getLastTradeValue() {
        return lastTradeValue;
    }

    public void setLastTradeValue(double lastTradeValue) {
        this.lastTradeValue = lastTradeValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "Quote [id=%s, symbol='%s', name='%s', lastTradeValue='%s']",
                id, symbol, name, lastTradeValue);
    }
}
