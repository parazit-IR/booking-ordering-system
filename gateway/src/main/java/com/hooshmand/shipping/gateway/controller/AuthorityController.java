package com.hooshmand.shipping.gateway.controller;

import com.hooshmand.shipping.gateway.config.security.authorities.BookingRoleAuthoritiesMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static com.hooshmand.shipping.gateway.Constants.JWT_USER_ROLES_CLAIM;
import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequestMapping("/authorities")
public class AuthorityController {

	@GetMapping
	public Map<String, List<String>> getPermissions() {
		return convertMapToList(getAuthorities());
	}

	private Map<String, List<String>> convertMapToList(Map<String, Set<String>> authoritiesMap) {
		return authoritiesMap.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> new ArrayList<>(entry.getValue().stream().sorted().collect(toList()))));
	}

	public static Map<String, Set<String>> getAuthorities() {
		Map<String, Set<String>> map = new HashMap<>();
		map.putAll(BookingRoleAuthoritiesMap.userAuthorities());
		return map;
	}


	@GetMapping("/roles")
	public List<String> getRoles(@AuthenticationPrincipal Jwt jwt) {
		return jwt.getClaim(JWT_USER_ROLES_CLAIM);
	}
}
