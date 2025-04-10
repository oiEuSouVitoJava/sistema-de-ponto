// src/main/java/com/vito/backend/controller/LoginRequest.java
package com.vito.backend.controller;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String senha;
}
