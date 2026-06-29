package com.hooshmand.shipping.gateway.config.security.authorities;

import lombok.Getter;

import java.util.*;

public class RoleAuthoritiesBuilder {
	@Getter
	private Map<String, Set<String>> roleAuthorities;

	public RoleAuthoritiesBuilder() {
		roleAuthorities = new HashMap<>();
	}

	public static RoleAuthoritiesBuilder builder(){
		return new RoleAuthoritiesBuilder();
	}

	public AuthoritiesBuilder role(String role){
		return new AuthoritiesBuilder(role, this);
	}

	public AuthoritiesSetBuilder roles(String... roles){
		return new AuthoritiesSetBuilder(Set.of(roles), this);
	}

	public Map<String, Set<String>> build() {
		return roleAuthorities;
	}

	public static class AuthoritiesBuilder {
		public String role;
		public Set<String> authorities;

		public RoleAuthoritiesBuilder roleAuthoritiesBuilder;

		public AuthoritiesBuilder(String role, RoleAuthoritiesBuilder roleAuthoritiesBuilder) {
			this.role = role;
			authorities = new HashSet<>();
			this.roleAuthoritiesBuilder = roleAuthoritiesBuilder;
		}

		public AuthoritiesBuilder hasAuthorities(String... authorities){
			this.authorities.addAll(List.of(authorities));
			return this;
		}

		public AuthoritiesBuilder hasAuthority(String authorities){
			this.authorities.add(authorities);
			return this;
		}

		public RoleAuthoritiesBuilder and() {
			roleAuthoritiesBuilder.getRoleAuthorities().put(role, authorities);
			return roleAuthoritiesBuilder;
		}

		public Map<String, Set<String>> build() {
			roleAuthoritiesBuilder.getRoleAuthorities().put(role, authorities);
			return roleAuthoritiesBuilder.build();
		}

	}

	public static class AuthoritiesSetBuilder {
		public Set<String> roles;
		public Set<String> authorities;

		public RoleAuthoritiesBuilder roleAuthoritiesBuilder;

		public AuthoritiesSetBuilder(Set<String> roles, RoleAuthoritiesBuilder roleAuthoritiesBuilder) {
			this.roles = roles;
			authorities = new HashSet<>();
			this.roleAuthoritiesBuilder = roleAuthoritiesBuilder;
		}

		public AuthoritiesSetBuilder hasAuthorities(String... authorities){
			this.authorities.addAll(List.of(authorities));
			return this;
		}

		public AuthoritiesSetBuilder hasAuthority(String authorities){
			this.authorities.add(authorities);
			return this;
		}

		public RoleAuthoritiesBuilder and() {
			roles.forEach(r -> roleAuthoritiesBuilder.getRoleAuthorities().put(r, authorities));
			return roleAuthoritiesBuilder;
		}

		public Map<String, Set<String>> build() {
			roles.forEach(r -> roleAuthoritiesBuilder.getRoleAuthorities().put(r, authorities));
			return roleAuthoritiesBuilder.build();
		}

	}
}

