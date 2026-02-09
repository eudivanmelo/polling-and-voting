package br.edu.ifrn.polling_and_voting.services;

import br.edu.ifrn.polling_and_voting.config.JwtTokenUtil;
import br.edu.ifrn.polling_and_voting.domain.dto.AuthResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.LoginRequestDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.RegisterRequestDTO;
import br.edu.ifrn.polling_and_voting.domain.entities.User;
import br.edu.ifrn.polling_and_voting.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username já está em uso");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já está em uso");
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(User.UserRole.USER)
                .enabled(true)
                .build();

        userRepository.save(user);

        String token = jwtTokenUtil.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = jwtTokenUtil.generateToken(user);

        return AuthResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
