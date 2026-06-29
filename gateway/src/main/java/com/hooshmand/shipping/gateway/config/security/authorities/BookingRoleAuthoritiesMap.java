package com.hooshmand.shipping.gateway.config.security.authorities;

import java.util.Map;
import java.util.Set;

public class BookingRoleAuthoritiesMap {
	public static Map<String, Set<String>> userAuthorities() {
		return RoleAuthoritiesBuilder.builder()
				.role("user")
				.hasAuthority("sys.admin.agency.delete").build();
	}
}
