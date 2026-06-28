package com.hooshmand.shipping.service.domain.ports.input.message.listener.containermovement;

import com.hooshmand.shipping.service.domain.dto.message.ContainerMovementModel;

public interface ContainerMovementListener {
	void containerMovementStarted(ContainerMovementModel containerMovementModel);
}
