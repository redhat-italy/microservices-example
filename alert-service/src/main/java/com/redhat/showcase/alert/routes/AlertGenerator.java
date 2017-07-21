package com.redhat.showcase.alert.routes;

import com.redhat.showcase.alert.model.Alert;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
class AlertGenerator extends RouteBuilder {
    
    @Override
    public void configure() {

        from("timer:new-order?delay=5s&period={{quickstart.generateOrderPeriod:2s}}")
                .routeId("generate-alert")
                .transform()
                .simple("RHT ${date:now:yyyyMMddmmss}")
                .transform()
                .constant(new Alert("RHT"))
                .marshal().json(JsonLibrary.Jackson, Alert.class)
                .to("activemq:queue:alerts")
                .log("Inserted new alert ${body}");
    }
}