package e_Commerse.Application.model.Entities.DTOs;


import e_Commerse.Application.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthResponse {

    public AuthResponse(String token, String role) {
        this.token=token;
        this.role=role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String token;
    private String role;
}

