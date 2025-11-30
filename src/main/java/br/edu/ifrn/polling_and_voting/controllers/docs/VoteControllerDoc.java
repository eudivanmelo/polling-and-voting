package br.edu.ifrn.polling_and_voting.controllers.docs;

import org.springframework.http.ResponseEntity;

import br.edu.ifrn.polling_and_voting.domain.dto.VoteCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.VoteResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Votação", description = "Gerenciamento de votos")
public interface VoteControllerDoc {
    
    @Operation(summary = "Registrar voto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Pesquisa inativa ou expirada"),
        @ApiResponse(responseCode = "404", description = "Opção não encontrada")
    })
    ResponseEntity<VoteResponseDTO> vote(VoteCreateDTO dto);
}
