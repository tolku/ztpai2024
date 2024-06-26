package com.fodapi.configuration;

import com.fodapi.FodapiApplication;
import com.fodapi.controllers.RegisterController;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FodapiApplication.class, RegisterController.class);
	}

}
