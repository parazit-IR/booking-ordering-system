package com.hooshmand.shipping.gateway.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestLoggingFilter extends AbstractRequestLoggingFilter {

	private final Set<String> excludedUrls = Set.of(
			"/actuator/health",
			"/actuator/prometheus"
	);
//	private final Tracer tracer;

	@Override
	protected boolean shouldLog(HttpServletRequest request) {
		if(excludedUrls.contains(request.getRequestURI())) {
			return false;
		}
		return logger.isDebugEnabled();
	}

	@Override
	protected void beforeRequest(HttpServletRequest request, String message) {
		setLoggingContext(request);
		logger.debug(message);
	}

	@Override
	protected void afterRequest(HttpServletRequest request, String message) {
		setLoggingContext(request);
		logger.debug(message);
	}

	private void setLoggingContext(HttpServletRequest request) {
//		MDC.put("traceId", getTraceId());
//		MDC.put("spanId", getSpanId());
		MDC.put("correlationId", getCorrelationId(request));
	}

//	private String getTraceId() {
//		return Optional.ofNullable(tracer)
//				.map(Tracer::currentSpan)
//				.map(Span::context)
//				.map(TraceContext::traceId)
//				.orElse("");
//	}
//
//	private String getSpanId() {
//		return Optional.ofNullable(tracer)
//				.map(Tracer::currentSpan)
//				.map(Span::context)
//				.map(TraceContext::spanId)
//				.orElse("");
//	}
//
	private String getCorrelationId(HttpServletRequest request) {
		return Optional.ofNullable(request)
				.map(req -> req.getHeader("X-CORRELATION-ID"))
				.orElse("");
	}
}
