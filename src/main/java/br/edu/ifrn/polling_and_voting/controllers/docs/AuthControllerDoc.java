package br.edu.ifrn.polling_and_voting.controllers.docs;

import br.edu.ifrn.polling_and_voting.domain.dto.AuthResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.LoginRequestDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.RegisterRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Autenticação", description = "Endpoints para registro e login de usuários")
public interface AuthControllerDoc {

    @Operation(
            summary = "Registrar novo usuário",
            description = "Cria uma nova conta de usuário e retorna um token JWT para autenticação"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário registrado com sucesso",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou usuário/email já existe"
            )
    })
    ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request);

    @Operation(
            summary = "Fazer login",
            description = "Autentica um usuário existente e retorna um token JWT"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais inválidas"
            )
    })
    ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request);
}
