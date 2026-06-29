package com.hooshmand.shipping.gateway;

public class Constants {
	public static final String USER_ID_HEADER = "X-USER-ID";
	public static final String USERNAME_HEADER = "X-USERNAME";
	public static final String JWT_USERNAME_CLAIM = "preferred_username";
	public static final String FIRST_NAME_HEADER = "X-FIRSTNAME";
	public static final String LAST_NAME_HEADER = "X-LASTNAME";
	public static final String JWT_FIRST_NAME_CLAIM = "given_name";
	public static final String JWT_LAST_NAME_CLAIM = "family_name";
	public static final String JWT_USER_ID_CLAIM = "sub";
	public static final String JWT_USER_ROLES_CLAIM = "roles";
	public static final String CORRELATION_ID = "X-CORRELATION-ID";
}