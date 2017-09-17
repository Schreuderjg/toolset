package io.scalasoft.tools.oracle.aq.toolset;

import io.scalasoft.tools.oracle.aq.toolset.dispatcher.AQJmsMessageDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "io.scalasoft.tools.oracle.aq.toolset")
@SpringBootApplication
public class AQTestApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(AQTestApplication.class, args);

		AQJmsMessageDispatcher messageDispatcher = (AQJmsMessageDispatcher) context.getBean("messageDispatcher");

		messageDispatcher.sendAllMessages();
	}
}