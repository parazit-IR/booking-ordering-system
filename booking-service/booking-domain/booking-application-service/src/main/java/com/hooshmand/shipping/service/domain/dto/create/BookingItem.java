package com.hooshmand.shipping.service.domain.dto.create;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
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
