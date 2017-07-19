package com.redhat.showcase.quote.store;

import java.util.List;

import com.redhat.showcase.quote.model.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuoteRepository extends MongoRepository<Quote, String> {

    public Quote findBySymbol(String symbol);

    public List<Quote> findByName(String name);

}