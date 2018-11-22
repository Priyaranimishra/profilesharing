package com.nelson.ecd.profitsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.Logger;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;


import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
*
* @author HCL ADPaaS - Auto generated.
* @version 1.0
* @since 2017-07-01
*
*/

/**************************
* DO NOT MODIFY THIS CLASS
**************************/

@SpringBootApplication
@EnableSwagger2

public class Application {

	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		LOGGER.info("\n----------------------------------------------------------\n\t" +
                "Spring-boot application Application is up and running.\n" +
                "----------------------------------------------------------");
	}

	@Bean
	public KieContainer kieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
	}
	
}
