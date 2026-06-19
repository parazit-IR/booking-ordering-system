package com.hooshmand.shipping.booking.service.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = { "com.hooshmand.shipping.system.booking.service.dataaccess", "com.hooshmand.shipping.system.dataaccess" })
@EntityScan(basePackages = { "com.hooshmand.shipping.system.order.service.dataaccess", "com.hooshmand.shipping.system.dataaccess"})
@SpringBootApplication(scanBasePackages = "com.hooshmand.shipping.system")
public class BookingServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookingServiceApplication.class, args);
	}
}
