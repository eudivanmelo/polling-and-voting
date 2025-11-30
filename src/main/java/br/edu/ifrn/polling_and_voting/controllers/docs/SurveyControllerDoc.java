package br.edu.ifrn.polling_and_voting.controllers.docs;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import br.edu.ifrn.polling_and_voting.domain.dto.SurveyCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyDetailResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyResultDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pesquisas", description = "Gerenciamento de pesquisas")
public interface SurveyControllerDoc {
    
    @Operation(summary = "Listar pesquisas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    ResponseEntity<List<SurveyResponseDTO>> getSurveys();

    @Operation(summary = "Buscar pesquisa por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pesquisa encontrada"),
        @ApiResponse(responseCode = "404", description = "Pesquisa não encontrada")
    })
    ResponseEntity<SurveyDetailResponseDTO> getSurveyById(UUID id);

    @Operation(summary = "Criar pesquisa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pesquisa criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<SurveyResponseDTO> createSurvey(SurveyCreateDTO dto);

    @Operation(summary = "Atualizar pesquisa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pesquisa atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Pesquisa não encontrada")
    })
    ResponseEntity<SurveyDetailResponseDTO> updateSurvey(UUID id, SurveyUpdateDTO dto);

    @Operation(summary = "Remover pesquisa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pesquisa removida com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pesquisa não encontrada")
    })
    ResponseEntity<Void> deleteSurvey(UUID id);

    @Operation(summary = "Obter resultados da pesquisa")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Resultados retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pesquisa não encontrada")
    })
    ResponseEntity<SurveyResultDTO> getSurveyResults(UUID id);
}
