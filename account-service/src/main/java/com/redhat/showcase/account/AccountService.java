/*
 * Copyright 2005-2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.redhat.showcase.account;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class AccountService {

    private final AtomicInteger counter = new AtomicInteger();

    private final Random amount = new Random();

    public Account generateAccount() {
        Account account = new Account();
        //account.setId(counter.incrementAndGet());
        account.setName("Jack");
        account.setSurname("Black");
        return account;
    }

    public Account rowToAccount(Map<String, Object> row) {
        Account account = new Account();
        account.setId((Integer) row.get("id"));
        account.setName((String) row.get("item"));
        account.setSurname((String) row.get("amount"));
        return account;
    }
}
