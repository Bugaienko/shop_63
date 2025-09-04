package ait.cohor63.shop.security.dto;

import java.util.Objects;

/**
 * @author Sergey Bugaenko
 * {@code @date} 04.09.2025
 */

public class TokenResponseDTO {

    private String accessToken;
    private String refreshToken;

    public TokenResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return String.format("Token Response -> accessToken: %s, refreshToken: %s", accessToken, refreshToken);
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof TokenResponseDTO that)) return false;

        return Objects.equals(accessToken, that.accessToken) && Objects.equals(refreshToken, that.refreshToken);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(accessToken);
        result = 31 * result + Objects.hashCode(refreshToken);
        return result;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
