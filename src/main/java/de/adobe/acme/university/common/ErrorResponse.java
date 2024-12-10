package de.adobe.acme.university.common;

import lombok.Builder;

@Builder
public record ErrorResponse(String key, String message) {
}
