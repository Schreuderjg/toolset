package io.scalasoft.tools.oracle.aq.toolset.dispatcher;

import io.scalasoft.tools.oracle.aq.toolset.properties.CustomProperties;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AQJmsMessageDispatcher {

    final JmsTemplate jmsTemplate;
    final CustomProperties properties;

    public AQJmsMessageDispatcher(JmsTemplate jmsTemplate, CustomProperties properties) {
        this.jmsTemplate = jmsTemplate;
        this.properties = properties;
    }

    public void sendAllMessages() {
        // Amount of files is determined by amount of payloads devidec by 2 (2 parameters per payload).
        IntStream fileNumbers = IntStream.range(1, properties.getPayloads().size() / 2);

        fileNumbers.forEach(n -> sendMessage(n));
    }

    private void sendMessage(int i) {
        final Set<Map.Entry<String, String>> headersForThisMessage = properties.getHeaders().entrySet()
                .stream().filter(e -> e.getKey().startsWith(i + ".")).collect(Collectors.toSet());
        final Set<Map.Entry<String, String>> payloadsForThisMessage = properties.getPayloads().entrySet()
                .stream().filter(e -> e.getKey().startsWith(i + ".")).collect(Collectors.toSet());

        if (headersForThisMessage.isEmpty() || payloadsForThisMessage.isEmpty()) {
            Optional<Map.Entry<String, String>> path =  payloadsForThisMessage.stream()
                    .filter(p -> p.getKey().equals("path")).findFirst();
            Optional<Map.Entry<String, String>> typeMessage =  payloadsForThisMessage.stream()
                    .filter(p -> p.getKey().equals("type")).findFirst();

            if(path.isPresent() && typeMessage.isPresent()) {
                System.out.println("Sending file: " + path.get().getValue());
                System.out.println("Sending file type: " + typeMessage.get().getValue());

                readFileAndSendContent(typeMessage.get().getValue(), path.get().getValue(), headersForThisMessage);
            } else {
                System.out.println("Could not send file(" + i + "), type or path missing");
            }
        }
    }

    private void readFileAndSendContent(String type, String path, Set<Map.Entry<String, String>> headers)  {
        if(Files.exists(Paths.get(path))) {
            try {
                if ("BYTES".equalsIgnoreCase(type)) {
                    sendAndAddHeaders(Files.readAllBytes(Paths.get(path)), headers);
                } else {
                    StringBuilder builder = new StringBuilder();
                    Files.readAllLines(Paths.get(path)).stream().forEach(s -> builder.append(s));
                    sendAndAddHeaders(builder.toString(), headers);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File does not exist: " + path);
        }
    }

    private void sendAndAddHeaders(final Object payload, final Set<Map.Entry<String, String>> headers) {
        jmsTemplate.convertAndSend("Q_TEST", payload, message -> {
            fillHeader(message, headers);
            return message;
        });
    }

    private void fillHeader(Message message, Set<Map.Entry<String, String>> headers) {
        headers.stream().forEach(h -> {
            try {
                message.setStringProperty(h.getKey(), h.getValue());
            } catch (JMSException e) {
                System.out.println("Could not add header: " + h.getKey() + " value: " + h.getValue());
                e.printStackTrace();
            }
        });
    }
}