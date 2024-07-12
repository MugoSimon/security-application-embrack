package com.mugosimon.security_application_embrack.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The EntityResponse class is a generic utility class used to encapsulate a response entity.
 * It includes fields for a message, an entity of type T, and a status code. This class provides
 * constructors for creating instances with or without initial values and includes methods for
 * accessing and modifying these fields.
 * <p>
 * Fields:
 * - message: A string containing a response message.
 * - entity: A generic type T representing the response entity.
 * - statusCode: An integer representing the HTTP status code of the response.
 * <p>
 * Annotations:
 * - @Data: Generates getters, setters, equals, hashCode, and toString methods.
 * - @AllArgsConstructor: Generates a constructor with parameters for all fields.
 * - @NoArgsConstructor: Generates a default no-arguments constructor.
 * - @ToString: Generates a toString method including all fields.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EntityResponse<T> {

    private String message;
    private T entity;
    private Integer statusCode;
}