package com.hooshmand.shipping.service.domain.dto.create;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@lombok.extern.jackson.Jacksonized
public class CreateBookingCommand {
	@NotNull
	private final List<BookingItem> items;
}
