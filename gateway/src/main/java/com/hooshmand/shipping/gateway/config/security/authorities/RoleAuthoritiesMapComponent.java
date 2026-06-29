package com.hooshmand.shipping.gateway.config.security.authorities;


import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class RoleAuthoritiesMapComponent {

	public List<String> getRolesAuthorities(List<String> roles){
		var adminAuthorities = BookingRoleAuthoritiesMap.userAuthorities();
		List<String> authorities= new ArrayList<>();
		for (String role : roles) {
			authorities.addAll(adminAuthorities.getOrDefault(role, Set.of()));
		}
		return authorities;
	}

}
