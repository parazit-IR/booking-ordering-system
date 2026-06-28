package com.hooshmand.shipping.system.booking.service.messaging.publisher.kafka;

import com.hooshmand.shipping.service.domain.config.BookingServiceConfigData;
import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementEventPayload;
import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;
import com.hooshmand.shipping.service.domain.ports.output.message.publisher.containermovement.ContainerMovementRequestMessagePublisher;
import com.hooshmand.shipping.system.booking.service.messaging.mapper.BookingMessagingDataMapper;
import com.hooshmand.shipping.system.kafka.booking.avro.model.ContainerMovementRequestAvroModel;
import com.hooshmand.shipping.system.kafka.producer.KafkaMessageHelper;
import com.hooshmand.shipping.system.kafka.producer.service.KafkaProducer;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;


@Slf4j
@Component
public class BookingContainerMovementEventKafkaPublisher implements ContainerMovementRequestMessagePublisher {

	private final BookingMessagingDataMapper bookingMessagingDataMapper;
	private final KafkaProducer<String, ContainerMovementRequestAvroModel> kafkaProducer;
	private final BookingServiceConfigData bookingServiceConfigData;
	private final KafkaMessageHelper kafkaMessageHelper;

	public BookingContainerMovementEventKafkaPublisher(BookingMessagingDataMapper bookingMessagingDataMapper,
													   KafkaProducer<String, ContainerMovementRequestAvroModel> kafkaProducer,
													   BookingServiceConfigData bookingServiceConfigData,
													   KafkaMessageHelper kafkaMessageHelper) {
		this.bookingMessagingDataMapper = bookingMessagingDataMapper;
		this.kafkaProducer = kafkaProducer;
		this.bookingServiceConfigData = bookingServiceConfigData;
		this.kafkaMessageHelper = kafkaMessageHelper;
	}

	@Override
	public void publish(BookingContainerMovementOutboxMessage containerMovementOutboxMessage,
						BiConsumer<BookingContainerMovementOutboxMessage, OutboxStatus> outboxCallback) {
		BookingContainerMovementEventPayload bookingContainerMovementEventPayload = kafkaMessageHelper.getBookingEventPayload(containerMovementOutboxMessage.getPayload(),
				BookingContainerMovementEventPayload.class);

		String sagaId = containerMovementOutboxMessage.getSagaId().toString();

		log.info("Received BookingContainerMovementEventPayload for booking id: {} and saga id: {}",
				bookingContainerMovementEventPayload.getBookingId(),
				sagaId);

		try {
			ContainerMovementRequestAvroModel containerMovementRequestAvroModel = bookingMessagingDataMapper
					.bookingContainerMovementEventToContainerMovementRequestAvroModel(sagaId, bookingContainerMovementEventPayload);

			kafkaProducer.send(bookingServiceConfigData.getContainerMovementRequestTopicName(),
					sagaId,
					containerMovementRequestAvroModel,
					kafkaMessageHelper.getKafkaCallback(bookingServiceConfigData.getContainerMovementRequestTopicName(),
							containerMovementRequestAvroModel,
							containerMovementOutboxMessage,
							outboxCallback,
							bookingContainerMovementEventPayload.getBookingId(),
							"containerMovementRequestAvroModel"));

			log.info("BookingContainerMovementEventPayload sent to Kafka for booking id: {} and saga id: {}",
					bookingContainerMovementEventPayload.getBookingId(), sagaId);
		} catch (Exception e) {
			log.error("Error while sending BookingContainerMovementEventPayload" +
							" to kafka with booking id: {} and saga id: {}, error: {}",
					bookingContainerMovementEventPayload.getBookingId(), sagaId, e.getMessage());
		}
	}
}
