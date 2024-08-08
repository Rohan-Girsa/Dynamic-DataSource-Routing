package com.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableJpaRepositories(basePackages = "com.demo.repository", transactionManagerRef = "transactionManager", entityManagerFactoryRef = "entityManager")
@EnableTransactionManagement
@RequiredArgsConstructor
public class DataSourceConfig {

	private final DataSourceRouting dataSourceRouting;

	@Bean
	@Primary
	public DataSource dataSource() {
		return dataSourceRouting;
	}

	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).packages("com.demo.entity").persistenceUnit("default").build();
	}

	@Bean(name = "transactionManager")
	public JpaTransactionManager transactionManager(
			@Autowired @Qualifier("entityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}
