// src/main/java/com/vito/backend/DTO/AuthRequest.java
package com.vito.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String senha;
}
