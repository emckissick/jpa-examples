package com.examples.test.configuration
import javax.annotation.Resource
import javax.sql.DataSource

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager


@Configuration
@ImportResource(['classpath:com/examples/persistence/datasource-context.xml'])

@ComponentScan(['com.carfax.ia.persistence'])
public class JpaConfig implements ApplicationContextAware{	
	ApplicationContext applicationContext;
	
	@Resource
	DataSource emDataSource;

	@Bean
   public LocalContainerEntityManagerFactoryBean emFactory(){
      LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
      factoryBean.setDataSource( emDataSource );
      factoryBean.setPackagesToScan("com.examples.entity");
      
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
         {
            // JPA properties ...
         }
      };
      factoryBean.setJpaVendorAdapter( vendorAdapter );
		DefaultPersistenceUnitManager defaultPU = new DefaultPersistenceUnitManager();
		defaultPU.setDefaultPersistenceUnitName("applicationPU");
		factoryBean.setPersistenceUnitManager(defaultPU);
      return factoryBean;
   }
   
    
   @Bean
   public PlatformTransactionManager transactionManager(){
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory( 
       emFactory().getObject() );      
      return transactionManager;
   }
   
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
      return new PersistenceExceptionTranslationPostProcessor();
   }	
}