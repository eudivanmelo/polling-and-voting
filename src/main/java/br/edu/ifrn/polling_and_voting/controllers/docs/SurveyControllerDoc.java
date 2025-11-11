package br.edu.ifrn.polling_and_voting.controllers.docs;

import java.util.List;

import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Pesquisas", description = "Gerenciamento de pesquisas")
public interface SurveyControllerDoc {
    @Operation(
        summary = "Listar pesquisas",
        description = "Lista todas as pesquisas ativas ou paginadas."
    )
    List<Survey> getSurveys();
}
