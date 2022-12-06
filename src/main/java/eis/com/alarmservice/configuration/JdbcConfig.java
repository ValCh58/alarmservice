package eis.com.alarmservice.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

	@Bean(name = "myJdbcConnect")
	public DataSource dataJdbcSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.jdbc-datasource.url"));
        dataSource.setUsername(env.getProperty("spring.jdbc-datasource.user"));
        dataSource.setPassword(env.getProperty("spring.jdbc-datasource.password"));
        return dataSource;
    }
}
