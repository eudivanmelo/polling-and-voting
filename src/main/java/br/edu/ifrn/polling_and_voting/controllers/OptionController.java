package br.edu.ifrn.polling_and_voting.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrn.polling_and_voting.controllers.docs.OptionControllerDoc;
import br.edu.ifrn.polling_and_voting.domain.dto.OptionCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.OptionResponseDTO;
import br.edu.ifrn.polling_and_voting.services.OptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OptionController implements OptionControllerDoc {
    private final OptionService optionService;

    @PostMapping("/surveys/{surveyId}/options")
    public ResponseEntity<OptionResponseDTO> addOption(
            @PathVariable UUID surveyId,
            @Valid @RequestBody OptionCreateDTO dto) {
        OptionResponseDTO response = optionService.addOptionToSurvey(surveyId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/options/{id}")
    public ResponseEntity<Void> deleteOption(@PathVariable UUID id) {
        optionService.deleteOption(id);
        return ResponseEntity.noContent().build();
    }
}
