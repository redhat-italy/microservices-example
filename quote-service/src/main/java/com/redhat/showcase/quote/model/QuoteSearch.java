package com.redhat.showcase.quote.model;

import java.util.ArrayList;
import java.util.List;

public class QuoteSearch {
    private List<String> terms = new ArrayList<String>();

    public QuoteSearch(){

    }
    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }
}
