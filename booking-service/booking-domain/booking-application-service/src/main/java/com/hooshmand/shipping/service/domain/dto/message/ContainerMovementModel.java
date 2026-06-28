package com.hooshmand.shipping.service.domain.dto.message;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ContainerMovementModel {
	private String id;
	private String type;
	private String name;
}
