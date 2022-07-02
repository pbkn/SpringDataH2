package com.example.springdatah2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI openApi() {
		return new OpenAPI().info(new Info().title("Employee H2DB").description("Employee H2DB implementation")
				.version("v1.0")
				.contact(new Contact().name("Prabhakaran Ravichandran").url("https://whysurfswim.com/pbkn")
						.email("ravichandran.prabhakaran@gmail.com"))
				.termsOfService(
						"https://www.termsofservicegenerator.net/live.php?token=lEFiWquocZTVyHHPD78tqtic8BavjIxU")
				.license(new License().name("Attribution 4.0 International (CC BY 4.0)")
						.url("https://creativecommons.org/licenses/by/4.0/")));
	}
}
