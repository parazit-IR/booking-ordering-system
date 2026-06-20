package com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.adapter;

import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;
import com.hooshmand.shipping.service.domain.ports.output.repository.ContainerMovementOutboxRepository;
import com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.exception.ContainerMovementOutboxNotFoundException;
import com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.mapper.ContainerMovementOutboxDataAccessMapper;
import com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.repository.ContainerMovementOutboxJpaRepository;
import com.hooshmand.shipping.system.outbox.OutboxStatus;
import com.hooshmand.shipping.system.saga.SagaStatus;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ContainerMovementOutboxRepositoryImpl implements ContainerMovementOutboxRepository {

	private final ContainerMovementOutboxJpaRepository containerMovementOutboxJpaRepository;
	private final ContainerMovementOutboxDataAccessMapper containerMovementOutboxDataAccessMapper;

	public ContainerMovementOutboxRepositoryImpl(ContainerMovementOutboxJpaRepository containerMovementOutboxJpaRepository,
												 ContainerMovementOutboxDataAccessMapper containerMovementOutboxDataAccessMapper) {
		this.containerMovementOutboxJpaRepository = containerMovementOutboxJpaRepository;
		this.containerMovementOutboxDataAccessMapper = containerMovementOutboxDataAccessMapper;
	}

	@Override
	public BookingContainerMovementOutboxMessage save(BookingContainerMovementOutboxMessage bookingContainerMovementOutboxMessage) {
		return containerMovementOutboxDataAccessMapper
				.paymentOutboxEntityToOrderPaymentOutboxMessage(containerMovementOutboxJpaRepository
						.save(containerMovementOutboxDataAccessMapper
								.orderPaymentOutboxMessageToOutboxEntity(bookingContainerMovementOutboxMessage)));
	}

	@Override
	public Optional<List<BookingContainerMovementOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatus(String sagaType,
																										OutboxStatus outboxStatus,
																										SagaStatus[] sagaStatus) {

		return Optional.of(containerMovementOutboxJpaRepository.findByTypeAndOutboxStatusAndSagaStatusIn(sagaType,
						outboxStatus,
						Arrays.asList(sagaStatus))
				.orElseThrow(() -> new ContainerMovementOutboxNotFoundException("ContainerMovement outbox object " +
						"could not be found for saga type " + sagaType))
				.stream()
				.map(containerMovementOutboxDataAccessMapper::containerMovementOutboxEntityToBookingContainerMovementOutboxMessage)
				.collect(Collectors.toList()));
	}
}
