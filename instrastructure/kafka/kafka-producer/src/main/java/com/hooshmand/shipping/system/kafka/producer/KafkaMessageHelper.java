package com.hooshmand.shipping.system.kafka.producer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooshmand.shipping.booking.service.domain.exception.BookingDomainException;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaMessageHelper {
	private final ObjectMapper objectMapper;

	public KafkaMessageHelper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public <T> T getOrderEventPayload(String payload, Class<T> outputType) {
		try {
			return objectMapper.readValue(payload, outputType);
		} catch (JsonProcessingException e) {
			log.error("Could not read {} object!", outputType.getName(), e);
			throw new BookingDomainException("Could not read " + outputType.getName() + " object!", e);
		}
	}

	public <T,U> ListenableFutureCallback<SendResult<String, T>>
	getKafkaCallback(String responseTopicName,
					 T avroModel, U outboxMessage,
					 BiConsumer<U, OutboxStatus> outboxCallback,
					 String bookingId,
					 String avroModelName) {
		return new ListenableFutureCallback<SendResult<String, T>>() {
			@Override
			public void onFailure(Throwable ex) {
				log.error("Error while sending {} with message: {} and outbox type: {} to topic {}",
						avroModelName, avroModel.toString(), outboxMessage.getClass().getName(), responseTopicName, ex);
				outboxCallback.accept(outboxMessage, OutboxStatus.FAILED);
			}

			@Override
			public void onSuccess(SendResult<String, T> result) {
				RecordMetadata metadata = result.getRecordMetadata();
				log.info("Received successful response from Kafka for booking id: {}" +
								" Topic: {} Partition: {} Offset: {} Timestamp: {}",
						bookingId,
						metadata.topic(),
						metadata.partition(),
						metadata.offset(),
						metadata.timestamp());
				outboxCallback.accept(outboxMessage, OutboxStatus.COMPLETED);
			}
		};
	}
}
