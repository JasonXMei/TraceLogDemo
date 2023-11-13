package com.example.TraceLogDemo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class TraceFilter extends OncePerRequestFilter {

    public static final String TRACE_ID_HEADER  = "X-TRACE-ID";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = getOrGenerateTraceId(request);
        MDC.put(TRACE_ID_HEADER, traceId);

        log.info("enter trace filter");
        // 微服务调用需要在 header 中加上 traceId, 用于链路追踪
        // response.addHeader(TRACE_ID_HEADER, traceId);
        try {
            filterChain.doFilter(request, response);
        } finally {
            log.info("exit trace filter");
            MDC.clear();
        }
    }

    private String getOrGenerateTraceId(HttpServletRequest request) {
        String traceId = request.getHeader(TRACE_ID_HEADER);
        if (Objects.isNull(traceId)) {
            traceId = UUID.randomUUID().toString().replaceAll("-", "");
        }

        return traceId;
    }
}
