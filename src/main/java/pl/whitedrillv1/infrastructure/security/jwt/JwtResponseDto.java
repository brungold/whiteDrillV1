package pl.whitedrillv1.infrastructure.security.jwt;

import lombok.Builder;

@Builder
public record JwtResponseDto(String token) {
}
