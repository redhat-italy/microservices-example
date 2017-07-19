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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.redhat.showcase.quote.model.Quote;
import com.redhat.showcase.quote.store.QuoteRepository;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.swagger.Swagger2Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuoteRestApplication {

    @Autowired
    private QuoteRepository repository;

    @Autowired
    private Bus bus;

    List<Object> providers = new ArrayList<Object>();

    public static void main(String[] args) {
        SpringApplication.run(QuoteRestApplication.class, args);
    }

    @Bean
    public Server rsServer() {

        repository.deleteAll();

        // save a couple of customers
        repository.save(new Quote("RHT", "Red Hat", 100.00));
        repository.save(new Quote("AAPL", "Apple", 150.00));
        repository.save(new Quote("AMZN", "Amazon", 1010.00));
        repository.save(new Quote("GOOGL", "Google", 900.00));

        // setup CXF-RS
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setResourceClasses(Quote.class);

        //adding JacksonJsonProvider
        JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
        providers.add(jacksonJsonProvider);
        endpoint.setProviders(providers);
        endpoint.setBus(bus);
        endpoint.setServiceBeans(Arrays.<Object>asList(new QuoteServiceImpl()));
        endpoint.setAddress("/");
        endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
        return endpoint.create();
    }
}
