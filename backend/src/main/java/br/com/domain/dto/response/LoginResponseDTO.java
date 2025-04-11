package br.com.domain.dto.response;

public class LoginResponseDTO {
    public String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}