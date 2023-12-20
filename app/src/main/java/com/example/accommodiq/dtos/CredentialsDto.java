package com.example.accommodiq.dtos;

public class CredentialsDto {
    private String email;
    private String password;

    public CredentialsDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword() {
        this.password = password;
    }
}
