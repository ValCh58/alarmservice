package eis.com.alarmservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CnfModelMapper {
	
    @Bean
	public ModelMapper modelMapper() {
	return new ModelMapper();
	}

}
