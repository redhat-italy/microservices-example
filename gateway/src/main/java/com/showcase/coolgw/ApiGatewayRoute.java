/**
 * JBoss, Home of Professional Open Source
 * Copyright 2016, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.showcase.coolgw;

import com.showcase.coolgw.model.Account;
import com.showcase.coolgw.model.Quote;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static org.apache.http.client.methods.RequestBuilder.post;

@Component
public class ApiGatewayRoute extends RouteBuilder {
	private static final Logger LOG = LoggerFactory.getLogger(ApiGatewayRoute.class);

	@Autowired
	private Environment env;

    @Override
    public void configure() throws Exception {
    	try {
    		getContext().setTracing(Boolean.parseBoolean(env.getProperty("ENABLE_TRACER", "false")));
		} catch (Exception e) {
			LOG.error("Failed to parse the ENABLE_TRACER value: {}", env.getProperty("ENABLE_TRACER", "false"));
		}


        JacksonDataFormat accountFormatter = new ListJacksonDataFormat();
        accountFormatter.setUnmarshalType(Account.class);

        restConfiguration().component("servlet")
            .bindingMode(RestBindingMode.auto)
            .apiContextPath("/api-docs").contextPath("/api").apiProperty("host", "")
            .apiProperty("api.title", "Microservice API Gateway")
            .apiProperty("api.version", "1.0.0")
            .apiProperty("api.description", "The API of the gateway which fronts the various backend microservices")
            .apiProperty("api.contact.name", "Red Hat Developers")
            .apiProperty("api.contact.email", "developers@redhat.com")
            .apiProperty("api.contact.url", "https://developers.redhat.com");

        rest("/account/").description("Customer account Service")
            .produces(MediaType.APPLICATION_JSON_VALUE)

        // Handle CORS Pre-flight requests
        .options("/{accountId}")
            .route().id("accountOptions").end()
        .endRest()

        .get("/{accountId}").description("Get Customer account")
            .bindingMode(RestBindingMode.json)
            .param().name("accountId").type(RestParamType.path).description("The ID of the account to show").dataType("string").endParam()
            .outType(Account.class)
            .route().id("accountRoute")
                .setBody(simple("null"))
                .log("Calling {{env:ACCOUNT_ENDPOINT:localhost:8082}}")
                .removeHeaders("CamelHttp*")
                .setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
                .setHeader(Exchange.HTTP_URI, simple("http://{{env:ACCOUNT_ENDPOINT:localhost:8082}}/api/account/${header.accountId}"))
                .hystrix().id("Account Service")
                    .hystrixConfiguration()
                        .executionTimeoutInMilliseconds(5000).circuitBreakerSleepWindowInMilliseconds(10000)
                    .end()
                    .to("http4://DUMMY")
                .onFallback()
                    .to("direct:accountFallback")
                .end()
               // .log("Received response ${body}")
                .choice().when(body().isNull()).to("direct:accountFallback").end()
                .setHeader("CamelJacksonUnmarshalType", simple(Account.class.getName()))
                .unmarshal().json(JsonLibrary.Jackson, Account.class)
                //.setHeader("Content-Type", simple(MediaType.APPLICATION_JSON_VALUE))

            .end()
        .endRest();

        from("direct:accountFallback")
                .id("accountFallbackRoute")
                .transform()
                .constant(Collections.singletonList(new Account()))
                .marshal().json(JsonLibrary.Jackson, List.class);


        rest("/quote/").description("Customer account Service")
                .produces(MediaType.APPLICATION_JSON_VALUE)
        .options("/lastvalue/{symbol}")
                .route().id("quoteOptions").end()
                .endRest()


        .get("/lastvalue/{symbol}").description("Get Customer account")
                .bindingMode(RestBindingMode.json)
                .param().name("symbol").type(RestParamType.path).description("The quote symbol to search").dataType("string").endParam()
                .outType(Quote.class)
                .route().id("quoteRoute")
                    .setBody(simple("null"))
                    .log("Calling {{env:QUOTE_ENDPOINT:localhost:8084}}")
                    .removeHeaders("CamelHttp*")
                    .setHeader(Exchange.HTTP_METHOD, HttpMethods.GET)
                    .setHeader(Exchange.HTTP_URI, simple("http://{{env:QUOTE_ENDPOINT:localhost:8084}}/api/quote/lastvalue/${header.symbol}"))
                    .hystrix().id("Quote Service")
                        .hystrixConfiguration()
                            .executionTimeoutInMilliseconds(5000).circuitBreakerSleepWindowInMilliseconds(10000)
                        .end()
                        .to("http4://DUMMY")
                    .onFallback()
                        .to("direct:quoteFallback")
                    .end()
                   // .log("Received response ${body}")
                    .choice().when(body().isNull()).to("direct:accountFallback").end()
                    .setHeader("CamelJacksonUnmarshalType", simple(Quote.class.getName()))
                    .unmarshal().json(JsonLibrary.Jackson, Quote.class)
                    //.setHeader("Content-Type", simple(MediaType.APPLICATION_JSON_VALUE))
            .end()
        .endRest();

        from("direct:quoteFallback")
                .id("quoteFallbackRoute")
                .transform()
                .constant(Collections.singletonList(new Quote()))
                .marshal().json(JsonLibrary.Jackson, List.class);


    }



}
