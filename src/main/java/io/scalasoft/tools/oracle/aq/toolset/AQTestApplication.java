package io.scalasoft.tools.oracle.aq.toolset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;


@SpringBootApplication
public class AQTestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AQTestApplication.class, args);

		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

		System.out.println("Sending an text message.");
		jmsTemplate.convertAndSend("Q_TEST","<test>hallo<test>");

		System.out.println("Sending an byte[] message.");
		jmsTemplate.convertAndSend("Q_TEST","<test>hallo<test>".getBytes());
	}
}