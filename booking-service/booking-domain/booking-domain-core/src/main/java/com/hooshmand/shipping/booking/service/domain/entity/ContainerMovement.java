package com.hooshmand.shipping.booking.service.domain.entity;

import com.hooshmand.shipping.system.domain.entity.AggregateRoot;
import com.hooshmand.shipping.system.domain.valuobject.ContainerMovementId;

public class ContainerMovement extends AggregateRoot<ContainerMovementId> {

	public ContainerMovement(ContainerMovementId containerMovementId,
							 String type,
							 String name) {
		super.setId(containerMovementId);
		this.type = type;
		this.name = name;
	}

	public ContainerMovement(ContainerMovementId containerMovementId) {
		super.setId(containerMovementId);
	}

	private String type;
	private String name;

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}
