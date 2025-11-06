package e_Commerse.Application.model.services;




import e_Commerse.Application.model.Entities.DTOs.AuthRequest;
import e_Commerse.Application.model.Entities.DTOs.AuthResponse;
import e_Commerse.Application.model.Entities.User;
import e_Commerse.Application.model.repositories.UserRepository;
import e_Commerse.Application.model.JWT.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String register(AuthRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Username already exists";
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole()); // default role

        // Save user in database
        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return new AuthResponse(token, user.getRole());
    }
}
