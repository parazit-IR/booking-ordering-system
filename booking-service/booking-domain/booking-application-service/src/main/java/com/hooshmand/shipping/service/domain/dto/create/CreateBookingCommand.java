package com.hooshmand.shipping.service.domain.dto.create;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@lombok.extern.jackson.Jacksonized
public class CreateBookingCommand {
	@NotNull
	private final List<BookingItem> items;
}
