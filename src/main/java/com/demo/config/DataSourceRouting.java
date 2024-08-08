package com.demo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import com.demo.utility.DataSourceContextHolder;
import com.demo.utility.DataSourceEnum;

@Component
public class DataSourceRouting extends AbstractRoutingDataSource {

	@Value("${spring.datasource.routing.targetDataSources.dependo.url}")
	private String dependo_url;
	@Value("${spring.datasource.routing.targetDataSources.dependo.username}")
	private String dependo_username;
	@Value("${spring.datasource.routing.targetDataSources.dependo.password}")
	private String dependo_password;

	@Value("${spring.datasource.routing.targetDataSources.m2w.url}")
	private String m2w_url;
	@Value("${spring.datasource.routing.targetDataSources.m2w.username}")
	private String m2w_username;
	@Value("${spring.datasource.routing.targetDataSources.m2w.password}")
	private String m2w_password;

	@Value("${spring.datasource.routing.targetDataSources.atpl.url}")
	private String atpl_url;
	@Value("${spring.datasource.routing.targetDataSources.atpl.username}")
	private String atpl_username;
	@Value("${spring.datasource.routing.targetDataSources.atpl.password}")
	private String atpl_password;


	private final DataSourceContextHolder dataSourceContextHolder;

	public DataSourceRouting(DataSourceContextHolder dataSourceContextHolder) {
		this.dataSourceContextHolder = dataSourceContextHolder;
		afterPropertiesSet();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceContextHolder.getBranchContext();
	}

	@Override
	public void afterPropertiesSet() {
		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(DataSourceEnum.dependo, dataSourceDependoDataSource());
		dataSourceMap.put(DataSourceEnum.m2w, dataSourceM2WDataSource());
		dataSourceMap.put(DataSourceEnum.atpl, dataSourceAtplDataSource());
		this.setTargetDataSources(dataSourceMap);
		this.setDefaultTargetDataSource(dataSourceDependoDataSource());
		super.afterPropertiesSet();
	}

	public DataSource dataSourceDependoDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(dependo_url);
		dataSource.setUsername(dependo_username);
		dataSource.setPassword(dependo_password);
		return dataSource;
	}

	public DataSource dataSourceM2WDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(m2w_url);
		dataSource.setUsername(m2w_username);
		dataSource.setPassword(m2w_password);
		return dataSource;
	}

	public DataSource dataSourceAtplDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(atpl_url);
		dataSource.setUsername(atpl_username);
		dataSource.setPassword(atpl_password);
		return dataSource;
	}
}
