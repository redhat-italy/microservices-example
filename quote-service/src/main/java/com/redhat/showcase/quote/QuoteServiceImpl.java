/**
 * Copyright 2005-2016 Red Hat, Inc.
 * <p>
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.redhat.showcase.quote;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.showcase.quote.model.Quote;
import com.redhat.showcase.quote.model.QuoteSearch;
import com.redhat.showcase.quote.store.QuoteRepository;
import com.redhat.showcase.quote.util.ApplicationContextHolder;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@Api("/quote")
public class QuoteServiceImpl implements QuoteService {


    public Quote getQuote(String symbol) {
        QuoteRepository repository = ApplicationContextHolder.getContext().getBean(QuoteRepository.class);
        return repository.findBySymbol(symbol);
    }

    @Override
    public List<Quote> getQuotes(String filter) {
        QuoteRepository repository = ApplicationContextHolder.getContext().getBean(QuoteRepository.class);

        QuoteSearch quoteSearch;

        List<Quote> quotesSearchResult = new ArrayList<Quote>();

        try {
            quoteSearch = new ObjectMapper().readValue(filter, QuoteSearch.class);
            List<String> quoteTerms = quoteSearch.getTerms();

            for (String aName : quoteTerms) {
                Quote quoteSymbolResult = repository.findBySymbol(aName);
                System.out.println("Symbol for [" + aName + "] -> " + quoteSymbolResult);

                if (quoteSymbolResult != null) {
                    quotesSearchResult.add(quoteSymbolResult);
                }

                List<Quote> nameSearchResult = repository.findByName(aName);

                for (Quote quote : nameSearchResult) {
                    System.out.println("Name [" + aName + "]  -> " + quote);
                }

                quotesSearchResult.addAll(nameSearchResult);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quotesSearchResult;
    }

    @Override
    public Quote getCompany(String symbol, String name) {
        return new Quote("aaa", "bbb", 111);
    }


}
