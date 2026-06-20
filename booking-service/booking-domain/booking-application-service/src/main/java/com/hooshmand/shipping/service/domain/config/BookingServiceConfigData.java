package com.hooshmand.shipping.service.domain.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "booking-service")
public class BookingServiceConfigData {
	private String containerMovementRequestTopicName;
	private String containerMovementResponseTopicName;
}
