package com.examples.test.configuration


import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource
import org.springframework.stereotype.Repository

import com.examples.persistence.mock.MockDao


@Configuration
@ImportResource(['classpath:com/examples/persistence/datasource-context.xml',
				 'classpath:com/examples/persistence/jpa-context.xml'])

@ComponentScan(['com.carfax.ia.persistence'])
public class TestConfig implements ApplicationContextAware{	

	ApplicationContext applicationContext

	
	/*@Repositories
	MockEventRepository eventRep() {
		
	}*/
	
	@Bean
	MockDao mockDao() {		
		new MockDao();
	}
}
