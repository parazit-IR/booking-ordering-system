package com.hooshmand.shipping.booking.service.domain;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
	@Bean
	public BookingDomainService orderDomainService() {
		return new BookingDomainServiceImpl();
	}
}
