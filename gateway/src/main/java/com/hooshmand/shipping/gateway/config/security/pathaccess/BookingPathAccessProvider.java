package com.hooshmand.shipping.gateway.config.security.pathaccess;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class BookingPathAccessProvider extends PathAccessProvider {

	public static final String PREFIX = "/booking";

	private final PathAccess.PathAccessCreator pathAccessCreator = new PathAccess.PathAccessCreator(PREFIX);

	@Override
	@SneakyThrows
	public List<PathAccess> getPathAccesses() {
		return List.of(
				post("create", "")
		);
	}

}
