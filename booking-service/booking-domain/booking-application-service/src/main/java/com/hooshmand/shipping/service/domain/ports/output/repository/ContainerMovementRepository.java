package com.hooshmand.shipping.service.domain.ports.output.repository;

import com.hooshmand.shipping.booking.service.domain.entity.ContainerMovement;

public interface ContainerMovementRepository {
	ContainerMovement save(ContainerMovement containerMovement);
}
