package e_Commerse.Application.model.Entities.DTOs;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String role;
    private String username; // required for registration

    // sanitize setters
    public void setEmail(String email) {
        this.email = clean(email);
    }

    public void setPassword(String password) {
        this.password = clean(password);
    }

    public void setRole(String role) {
        this.role = clean(role);
    }

    public void setUsername(String username) {
        this.username = clean(username);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    // helper method to remove hidden non-breaking space (U+00A0) & trim
    private String clean(String value) {
        return value != null ? value.replace("\u00A0", "").trim() : null;
    }
}


