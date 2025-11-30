package br.edu.ifrn.polling_and_voting.controllers.docs;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import br.edu.ifrn.polling_and_voting.domain.dto.OptionCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.OptionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Opções", description = "Gerenciamento de opções de pesquisas")
public interface OptionControllerDoc {
    
    @Operation(summary = "Adicionar opção à pesquisa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Opção criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou pesquisa inativa"),
        @ApiResponse(responseCode = "404", description = "Pesquisa não encontrada")
    })
    ResponseEntity<OptionResponseDTO> addOption(UUID surveyId, OptionCreateDTO dto);

    @Operation(summary = "Remover opção")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Opção removida com sucesso"),
        @ApiResponse(responseCode = "400", description = "Opção já possui votos"),
        @ApiResponse(responseCode = "404", description = "Opção não encontrada")
    })
    ResponseEntity<Void> deleteOption(UUID id);
}
