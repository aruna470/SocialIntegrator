package com.eyerubic.socialintegrator;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class SocialintegratorApplicationTests {

	@Test
	void contextLoads() { //NOSONAR
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
