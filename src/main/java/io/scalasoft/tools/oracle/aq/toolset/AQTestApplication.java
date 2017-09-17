package io.scalasoft.tools.oracle.aq.toolset;

import io.scalasoft.tools.oracle.aq.toolset.properties.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsTemplate;

import java.util.Map;

@ComponentScan(value = "io.scalasoft.tools.oracle.aq.toolset")
@SpringBootApplication
public class AQTestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AQTestApplication.class, args);


		CustomProperties myProps = (CustomProperties) context.getBean("myProperties");


	}
}