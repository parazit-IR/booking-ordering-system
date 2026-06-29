package com.hooshmand.shipping.gateway.util;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collections;
import java.util.List;

public class SecurityContextUtil {

	private SecurityContextUtil() {
	}

	public static String extractClaimFromJwt(String claimName) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof Jwt jwtPrincipal) {
			Object claim = jwtPrincipal.getClaims().get(claimName);
			return claim == null ? null : claim.toString();
		} else if (principal instanceof DefaultOAuth2AuthenticatedPrincipal oauthToken){
			Object claim = oauthToken.getAttributes().get(claimName);
			return claim == null ? null : claim.toString();
		}
		return principal.toString();
	}

	public static List<String> extractClaimFromJwtAsList(String claimName) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Object claim = null;
		if (principal instanceof Jwt jwtPrincipal) {
			claim = jwtPrincipal.getClaims().get(claimName);
		} else if (principal instanceof DefaultOAuth2AuthenticatedPrincipal oauthToken){
			claim = oauthToken.getAttributes().get(claimName);
		}
		if (claim instanceof List) {
			return (List<String>) claim;
		}
		return Collections.emptyList();
	}
}
