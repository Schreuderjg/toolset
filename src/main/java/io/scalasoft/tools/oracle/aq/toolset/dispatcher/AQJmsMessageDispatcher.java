package io.scalasoft.tools.oracle.aq.toolset.dispatcher;

import io.scalasoft.tools.oracle.aq.toolset.properties.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AQJmsMessageDispatcher {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private CustomProperties properties;

    public void sendMessages() {
        Map<String, String> headers = properties.getHeaders();
        Map<String, String> payloads = properties.getPayloads();

        System.out.println("Sending an text message.");
        jmsTemplate.convertAndSend("Q_TEST","<test>hallo<test>");

        System.out.println("Sending an byte[] message.");
        jmsTemplate.convertAndSend("Q_TEST","<test>hallo<test>".getBytes());
    }
}