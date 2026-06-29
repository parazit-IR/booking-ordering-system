package com.hooshmand.shipping.gateway.config.security.pathaccess;

import org.springframework.http.HttpMethod;

import java.util.List;

public abstract class PathAccessProvider {

	public abstract List<PathAccess> getPathAccesses();

	public abstract PathAccess.PathAccessCreator getPathAccessCreator();

	protected PathAccess get(String path, String... authorities) {
		return getPathAccessCreator().createGet(path, authorities);
	}

	protected PathAccess path(String path, HttpMethod method, String... authorities) {
		return getPathAccessCreator().createGet(path, authorities);
	}

	protected PathAccess post(String path, String... authorities) {
		return getPathAccessCreator().createPost(path, authorities);
	}

	protected PathAccess put(String path, String... authorities) {
		return getPathAccessCreator().createPut(path, authorities);
	}

	protected PathAccess delete(String path, String... authorities) {
		return getPathAccessCreator().createDelete(path, authorities);
	}

	protected PathAccess any(String path, String... authorities) {
		return getPathAccessCreator().createAny(path, authorities);
	}

	protected PathAccess method(String path, HttpMethod httpMethod, String... authorities) {
		return getPathAccessCreator().createMethod(path, httpMethod, authorities);
	}


}
