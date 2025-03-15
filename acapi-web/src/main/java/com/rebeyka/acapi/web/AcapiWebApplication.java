package com.rebeyka.acapi.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

@SpringBootApplication
@EnableHypermediaSupport(type = HypermediaType.HAL_FORMS)
public class AcapiWebApplication {

	  public static void main(String[] args) {
		    SpringApplication.run(AcapiWebApplication.class, args);
		  }

}
