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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.redhat.showcase.quote.model.Quote;
import org.springframework.stereotype.Service;

import java.util.List;

@Path("/quote")
@Service
public interface QuoteService {

    @GET
    @Path("/lastvalue/{symbol}")
    @Produces(MediaType.APPLICATION_JSON)
    Quote getQuote(@PathParam("symbol") String symbol);

    @GET
    @Path("/quotes")
    @Produces(MediaType.APPLICATION_JSON)
    List<Quote> getQuotes(@QueryParam("q") String filter);

    @GET
    @Path("/company/{symbol, name}")
    @Produces(MediaType.APPLICATION_JSON)
    Quote getCompany(@PathParam("symbol") String symbol, @PathParam("name") String name);


}
