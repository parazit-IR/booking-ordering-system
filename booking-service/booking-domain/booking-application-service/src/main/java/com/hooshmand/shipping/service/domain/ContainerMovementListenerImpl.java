package com.hooshmand.shipping.service.domain;

import com.hooshmand.shipping.booking.service.domain.entity.ContainerMovement;
import com.hooshmand.shipping.booking.service.domain.exception.BookingDomainException;
import com.hooshmand.shipping.service.domain.dto.message.ContainerMovementModel;
import com.hooshmand.shipping.service.domain.mapper.BookingDataMapper;
import com.hooshmand.shipping.service.domain.ports.input.message.listener.containermovement.ContainerMovementListener;
import com.hooshmand.shipping.service.domain.ports.output.repository.ContainerMovementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ContainerMovementListenerImpl implements ContainerMovementListener {

	private final ContainerMovementRepository containerMovementRepository;
	private final BookingDataMapper bookingDataMapper;

	public ContainerMovementListenerImpl(ContainerMovementRepository containerMovementRepository, BookingDataMapper bookingDataMapper) {
		this.containerMovementRepository = containerMovementRepository;
		this.bookingDataMapper = bookingDataMapper;
	}


	@Override
	public void containerMovementStarted(ContainerMovementModel containerMovementModel) {
		ContainerMovement containerMovement = containerMovementRepository.save(bookingDataMapper.containerMovementModelToContainerMovement(containerMovementModel));
		if (containerMovement == null) {
			log.error("Container movement could not be created in booking database with id: {}", containerMovement.getId());
			throw new BookingDomainException("Container movement could not be started in booking database with id " +
					containerMovementModel.getId());
		}
		log.info("Container movement is started in booking database with id: {}", containerMovement.getId());
	}
}
