package com.redhat.showcase.alert.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
class Websocket extends RouteBuilder {

    @Override
    public void configure() {
        from("activemq:queue:alerts")
                .log("Got new alert ${body}")
                .routeId("from-JMS-to-WebSocket")
                .to("websocket://0.0.0.0:8080/alerts?sendToAll=true&staticResources=classpath:webapp");
    }
}