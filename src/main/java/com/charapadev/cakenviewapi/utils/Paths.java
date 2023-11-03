package com.charapadev.cakenviewapi.utils;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Record containing the method and string matcher that represents an HTTP endpoint.
 */

record Path(
    /**
     * The HTTP verb used.
     */
    HttpMethod method,
    /**
     * The Ant matcher for one or more endpoints.
     */
    String matcher
) {}

/**
 * Utility class used to match given paths with some conventional application paths registered here.
 */

public class Paths {

    private static List<Path> publicPaths = List.of(
        // Authentication endpoint
        new Path(HttpMethod.POST, "/login"),
        // Register endpoint
        new Path(HttpMethod.POST, "/users"),
        // All listing/searching cake endpoints
        new Path(HttpMethod.GET, "/cakes/**"),
        // All listing/searching rating endpoints
        new Path(HttpMethod.GET, "/ratings/**"),
        // All files related to OpenAPI specification
        new Path(HttpMethod.GET, "/v3/api-docs/**"),
        // All files related to Swagger UI
        new Path(HttpMethod.GET, "/swagger-ui/**")
    );

    /**
     * Performs an verification if the requested endpoint has public access or not.
     *
     * @param request The servlet request instance.
     * @return If the endpoint is public or not.
     */
    public static boolean isPublicRoute(HttpServletRequest request) {
        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());

        return publicPaths.stream()
            .filter(path -> path.method().equals(requestMethod))
            .anyMatch(path -> new AntPathRequestMatcher(path.matcher()).matches(request));
    }

}
