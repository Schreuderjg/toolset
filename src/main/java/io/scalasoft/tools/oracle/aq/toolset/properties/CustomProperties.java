package io.scalasoft.tools.oracle.aq.toolset.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties()
public class CustomProperties {

    private final Map<String,String> headers = new HashMap<>();
    private final Map<String,String> payloads = new HashMap<>();


    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getPayloads() {
        return payloads;
    }
}