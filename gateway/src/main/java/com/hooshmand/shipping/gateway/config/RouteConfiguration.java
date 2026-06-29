package com.hooshmand.shipping.gateway.config;

import com.hooshmand.shipping.gateway.util.SecurityContextUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.hooshmand.shipping.gateway.Constants.*;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class RouteConfiguration {

	@Value("${booking.uri}")
	private String bookingUri;

	@Value("${booking.route-id}")
	private String bookingRouteId;

	@Value("${booking.path}")
	private String bookingPath;


	@Bean
	public RouterFunction<ServerResponse> bookingRouterFunctionsSetRequestHeader() {
		return route(bookingRouteId)
				.route(path(bookingPath), http(bookingUri))
				.before(encodeRequestParameters())
				.before(addPropertiesToHeader())
				.build();
	}


	private static Function<ServerRequest, ServerRequest> addPropertiesToHeader() {
		return request -> {
			String username = SecurityContextUtil.extractClaimFromJwt(JWT_USERNAME_CLAIM);
			String userId = SecurityContextUtil.extractClaimFromJwt(JWT_USER_ID_CLAIM);
			String firstName = SecurityContextUtil.extractClaimFromJwt(JWT_FIRST_NAME_CLAIM);
			String lastname = SecurityContextUtil.extractClaimFromJwt(JWT_LAST_NAME_CLAIM);
			String correlationId = SecurityContextUtil.extractClaimFromJwt(CORRELATION_ID);
			String roles = getRoles();
			ServerRequest.Builder serverReqBuilder = ServerRequest.from(request);
			if (userId != null)
				serverReqBuilder.header(USER_ID_HEADER, MvcUtils.expandMultiple(request, userId));
			if (username != null)
				serverReqBuilder.header(USERNAME_HEADER, MvcUtils.expandMultiple(request, username));
			if (firstName != null)
				serverReqBuilder.header(FIRST_NAME_HEADER, MvcUtils.expandMultiple(request, firstName));
			if (lastname != null)
				serverReqBuilder.header(LAST_NAME_HEADER, MvcUtils.expandMultiple(request, lastname));
			if (roles != null)
				serverReqBuilder.header(JWT_USER_ROLES_CLAIM, MvcUtils.expandMultiple(request, roles));
			if (correlationId != null) {
				serverReqBuilder.header(CORRELATION_ID, MvcUtils.expandMultiple(request, correlationId));
				MDC.put("correlationId", correlationId);
			}
			return serverReqBuilder.build();
		};
	}

	private static String getRoles() {
		List<String> rolesList = SecurityContextUtil.extractClaimFromJwtAsList(JWT_USER_ROLES_CLAIM);
		return Optional.of(rolesList)
				.map(role -> String.join(",", rolesList))
				.orElse(null);
	}

	private Function<ServerRequest, ServerRequest> encodeRequestParameters() {
		return request -> {
			return ServerRequest.from(request)
					.params(queryParams -> queryParams.forEach((key, values) -> {
						var encodedValues = values.stream()
								.map(v -> UriUtils.encodeQueryParam(v, StandardCharsets.UTF_8))
								.toList();
						queryParams.put(key, encodedValues);
					}))
					.build();
		};
	}

}

