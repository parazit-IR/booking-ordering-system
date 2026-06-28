package com.hooshmand.shipping.system.booking.service.messaging.listener.kafka;

import com.hooshmand.shipping.service.domain.ports.input.message.listener.containermovement.ContainerMovementListener;
import com.hooshmand.shipping.system.booking.service.messaging.mapper.BookingMessagingDataMapper;
import com.hooshmand.shipping.system.kafka.booking.avro.model.ContainerMovementAvroModel;
import com.hooshmand.shipping.system.kafka.consumer.KafkaConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class ContainerMovementKafkaListener implements KafkaConsumer<ContainerMovementAvroModel> {

	private final ContainerMovementListener containerMovementListener;
	private final BookingMessagingDataMapper bookingMessagingDataMapper;

	public ContainerMovementKafkaListener(ContainerMovementListener containerMovementListener, BookingMessagingDataMapper bookingMessagingDataMapper) {
		this.containerMovementListener = containerMovementListener;
		this.bookingMessagingDataMapper = bookingMessagingDataMapper;
	}


	@Override
	@KafkaListener(id = "${kafka-consumer-config.customer-group-id}", topics = "${booking-service.container-movement-topic-name}")
	public void receive(@Payload List<ContainerMovementAvroModel> messages,
	                    @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
	                    @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
	                    @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
		log.info("{} number of customer create messages received with keys {}, partitions {} and offsets {}",
				messages.size(),
				keys.toString(),
				partitions.toString(),
				offsets.toString());

		messages.forEach(containerMovementAvroModel -> {
			containerMovementListener.containerMovementStarted(bookingMessagingDataMapper
					.containerMovementAvroModeltoContainerMovementModel(containerMovementAvroModel));
		});

	}
}
