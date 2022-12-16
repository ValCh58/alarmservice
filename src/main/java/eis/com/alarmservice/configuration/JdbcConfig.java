package eis.com.alarmservice.configuration;

import javax.sql.DataSource;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource({ "classpath:application.properties" })
public class JdbcConfig {
	private Environment env;
    
	public JdbcConfig() {}

	@Autowired
	public JdbcConfig(Environment env) {
		super();
		this.env = env;
	}
	
	@Primary
	@Bean(name = "myJdbcConnectPrimary")
	public DataSource dataJdbcSourcePrimary() {
        final DriverManagerDataSource dataSourcePrimary = new DriverManagerDataSource();
        dataSourcePrimary.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSourcePrimary.setUrl(env.getProperty("spring.datasource.url"));//jdbc:sqlite:alarmstorage0.sqlite
        dataSourcePrimary.setUsername(env.getProperty("spring.jdbc-datasource.user"));
        dataSourcePrimary.setPassword(env.getProperty("spring.jdbc-datasource.password"));
        return dataSourcePrimary;
    }
	

	@Bean(name = "myJdbcConnect")
	public DataSource dataJdbcSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        if (SystemUtils.IS_OS_WINDOWS) {
        	dataSource.setUrl(env.getProperty("spring.jdbc-datasource.url"));//jdbc:sqlite:c://alarmstorage/alarmstorage.sqlite
	    }
	    else if (SystemUtils.IS_OS_LINUX) {
	    	dataSource.setUrl(env.getProperty("spring.jdbc-linux-datasource.url"));//jdbc:sqlite:/home/alarmstorage/alarmstorage.sqlite  
	    }
        dataSource.setUsername(env.getProperty("spring.jdbc-datasource.user"));
        dataSource.setPassword(env.getProperty("spring.jdbc-datasource.password"));
        return dataSource;
    }
}
