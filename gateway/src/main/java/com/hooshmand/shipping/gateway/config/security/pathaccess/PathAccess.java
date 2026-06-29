package com.hooshmand.shipping.gateway.config.security.pathaccess;

import lombok.Builder;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;

import java.util.List;

@Builder
public class PathAccess {

	private HttpMethod method;

	private String path;

	private List<String> authorities;

	public void setPathAuthority(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry spec) {
		if (method == null) {
			spec.requestMatchers(path).hasAnyAuthority(getAuthoritiesAsArray());
		} else {
			spec.requestMatchers(method, path).hasAnyAuthority(getAuthoritiesAsArray());
		}
	}

	private static PathAccess createPathAccess(HttpMethod method, String path, String[] authority) {
		return PathAccess.builder().method(method).path(path).authorities(List.of(authority)).build();
	}

	public static class PathAccessCreator {

		private final String prefix;

		public PathAccessCreator(String prefix) {
			this.prefix = prefix;
		}

		public PathAccess createGet(String path, String... authorities) {
			return createPathAccess(HttpMethod.GET, validatePath(path), authorities);
		}

		public PathAccess createPost(String path, String... authorities) {
			return createPathAccess(HttpMethod.POST, validatePath(path), authorities);
		}

		public PathAccess createPut(String path, String... authorities) {
			return createPathAccess(HttpMethod.PUT, validatePath(path), authorities);
		}

		public PathAccess createDelete(String path, String... authorities) {
			return createPathAccess(HttpMethod.DELETE, validatePath(path), authorities);
		}

		public PathAccess createAny(String path, String... authorities) {
			return createPathAccess(null, validatePath(path), authorities);
		}

		public PathAccess createMethod(String path, HttpMethod method, String... authorities) {
			return createPathAccess(method, validatePath(path), authorities);
		}

		private String validatePath(String path) {
			if (path.startsWith("/") || path.contains(this.prefix)) {
				throw new RuntimeException(String.format("path %s is invalid!", path));
			}
			return this.prefix + "/" + path;
		}

	}

	private String[] getAuthoritiesAsArray() {
		return authorities.toArray(String[]::new);
	}

}
