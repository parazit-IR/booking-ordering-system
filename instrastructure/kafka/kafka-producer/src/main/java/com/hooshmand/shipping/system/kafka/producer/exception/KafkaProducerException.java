package com.hooshmand.shipping.system.kafka.producer.exception;

public class KafkaProducerException extends RuntimeException {

	public KafkaProducerException(String message) {
		super(message);
	}
}