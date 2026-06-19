package com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.adapter;

import com.hooshmand.shipping.service.domain.outbox.model.containermovement.BookingContainerMovementOutboxMessage;
import com.hooshmand.shipping.service.domain.ports.output.repository.ContainerMovementOutboxRepository;
import com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.mapper.ContainerMovementOutboxDataAccessMapper;
import com.hooshmand.shipping.system.booking.service.dataaccess.outbox.containermovement.repository.ContainerMovementOutboxJpaRepository;

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
}
