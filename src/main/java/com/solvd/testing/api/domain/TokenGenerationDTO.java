package com.solvd.testing.api.domain;

public class TokenGenerationDTO {
    private String refreshToken;

    public TokenGenerationDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
