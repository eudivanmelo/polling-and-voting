package br.edu.ifrn.polling_and_voting.controllers;

import br.edu.ifrn.polling_and_voting.controllers.docs.AuthControllerDoc;
import br.edu.ifrn.polling_and_voting.domain.dto.AuthResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.LoginRequestDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.RegisterRequestDTO;
import br.edu.ifrn.polling_and_voting.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements AuthControllerDoc {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            AuthResponseDTO response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            AuthResponseDTO response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
