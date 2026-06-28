package com.hooshmand.shipping.service.domain.outbox.scheduler.containermovement;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hooshmand.shipping.booking.service.domain.exception.BookingDomainException;
import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementEventPayload;
import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;
import com.hooshmand.shipping.service.domain.ports.output.repository.ContainerMovementOutboxRepository;
import com.hooshmand.shipping.system.domain.valuobject.BookingStatus;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import com.hooshmand.shipping.system.saga.SagaStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.hooshmand.shipping.system.saga.booking.SagaConstants.BOOKING_SAGA_NAME;


@Slf4j
@Component
public class ContainerMovementOutboxHelper {

	private final ContainerMovementOutboxRepository containerMovementOutboxRepository;
	private final ObjectMapper objectMapper;

	public ContainerMovementOutboxHelper(ContainerMovementOutboxRepository containerMovementOutboxRepository,
										 ObjectMapper objectMapper) {
		this.containerMovementOutboxRepository = containerMovementOutboxRepository;
		this.objectMapper = objectMapper;
	}

	@Transactional
	public void saveContainerMovementOutboxMessage(BookingContainerMovementEventPayload bookingContainerMovementEventPayload,
	                                               BookingStatus bookingstatus,
	                                               SagaStatus sagaStatus,
	                                               OutboxStatus outboxStatus,
	                                               UUID sagaId) {
		save(BookingContainerMovementOutboxMessage.builder()
				.id(UUID.randomUUID())
				.sagaId(sagaId)
				.createdAt(bookingContainerMovementEventPayload.getCreatedAt())
				.type(BOOKING_SAGA_NAME)
				.payload(createPayload(bookingContainerMovementEventPayload))
				.bookingStatus(bookingstatus)
				.sagaStatus(sagaStatus)
				.outboxStatus(outboxStatus)
				.build());

	}

	@Transactional
	public void save(BookingContainerMovementOutboxMessage bookingContainerMovementOutboxMessage) {
		BookingContainerMovementOutboxMessage response = containerMovementOutboxRepository.save(bookingContainerMovementOutboxMessage);
		if (response == null) {
			log.error("Could not save BookingContainerMovementOutboxMessage with outbox id: {}", bookingContainerMovementOutboxMessage.getId());
			throw new BookingDomainException("Could not save BookingContainerMovementOutboxMessage with outbox id: " +
					bookingContainerMovementOutboxMessage.getId());
		}
		log.info("BookingContainerMovementOutboxMessage saved with outbox id: {}", bookingContainerMovementOutboxMessage.getId());
	}

	private String createPayload(BookingContainerMovementEventPayload containerMovementEventPayload) {
		try {
			return objectMapper.writeValueAsString(containerMovementEventPayload);
		} catch (JsonProcessingException e) {
			log.error("Could not create BookingContainerMovementEventPayload object for booking id: {}",
					containerMovementEventPayload.getBookingId(), e);
			throw new BookingDomainException("Could not create BookingContainerMovementEventPayload object for booking id: " +
					containerMovementEventPayload.getBookingId(), e);
		}
	}

	public Optional<List<BookingContainerMovementOutboxMessage>>
	getContainerMovementOutboxMessageByOutboxStatusAndSagaStatus(OutboxStatus outboxStatus,
																 SagaStatus... sagaStatus) {
		return containerMovementOutboxRepository.findByTypeAndOutboxStatusAndSagaStatus(
				BOOKING_SAGA_NAME,
				outboxStatus,
				sagaStatus);
	}
}
