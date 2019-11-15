package com.springwebiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class SpringWebivApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebivApplication.class, args);
	}
	
	
	/**
	 * @return
	 */
	@Bean
	public Validator validator()
	{
		return new LocalValidatorFactoryBean();
	}
	

}
