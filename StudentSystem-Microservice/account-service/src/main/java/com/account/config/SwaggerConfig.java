package com.account.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;





@Configuration
@EnableSwagger2
public class SwaggerConfig {
//	  @Value("${appInfo.host}")
//	  private String host;
//
//	  @Value("${appInfo.basePath}")
//	  private String basePath;

	  private String basePackage = "com.account.controller";

	  @Bean
	  public Docket allAPI() {
		   return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage(basePackage))
	                .paths(PathSelectors.any())
	                .build().apiInfo(metaData());
	  }

//	  private ApiInfo getApiInfo() {
//	    return new ApiInfo("Services For Account Management ", "Account APIs", "1.0", "",
//	      new Contact("AIS Java Team", "https://www.ais.com.vn", "contact@ais.com.vn"), "API license",
//	      "https://www.ais.com.vn", Collections.emptyList());
//	  }
	  
	    private ApiInfo metaData() {
	        return new ApiInfoBuilder()
	                .title("Services For Account Management")
	                .description("\"Account APIs \"")
	                .version("1.0")
	                .license("Apache 2.0")
	                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
	                .contact(new Contact("AIS Java Team", "https://www.ais.com.vn", "contact@ais.com.vn"))
	                .build();
	    }
}
