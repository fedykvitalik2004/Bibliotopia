package org.vitalii.fedyk.bibliotopia.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String accessToken, String refreshToken) {
}
