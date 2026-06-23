package com.hooshmand.shipping.service.domain.dto.create;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@lombok.extern.jackson.Jacksonized
public class BookingItem {
	@NotNull
	private final Integer quantity;
	@NotNull
	private final BigDecimal price;
}
