package io.scalasoft.tools.oracle.aq.toolset;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @JmsListener(destination = "Q_TEST", containerFactory = "myFactory")
    public void receiveMessage(Object o) {
        System.out.println("Received: type: " + o.getClass().getName());
    }
}