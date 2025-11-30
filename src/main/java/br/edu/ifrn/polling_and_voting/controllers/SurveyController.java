package br.edu.ifrn.polling_and_voting.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrn.polling_and_voting.controllers.docs.SurveyControllerDoc;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyDetailResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyResultDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyUpdateDTO;
import br.edu.ifrn.polling_and_voting.services.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController implements SurveyControllerDoc {
    private final SurveyService surveyService;

    @GetMapping
    public ResponseEntity<List<SurveyResponseDTO>> getSurveys() {
        return ResponseEntity.ok(surveyService.listSurveys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyDetailResponseDTO> getSurveyById(@PathVariable UUID id) {
        return ResponseEntity.ok(surveyService.getSurveyById(id));
    }

    @PostMapping
    public ResponseEntity<SurveyResponseDTO> createSurvey(@Valid @RequestBody SurveyCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(surveyService.createSurvey(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyDetailResponseDTO> updateSurvey(
            @PathVariable UUID id,
            @Valid @RequestBody SurveyUpdateDTO dto) {
        return ResponseEntity.ok(surveyService.updateSurvey(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable UUID id) {
        surveyService.deleteSurvey(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/results")
    public ResponseEntity<SurveyResultDTO> getSurveyResults(@PathVariable UUID id) {
        return ResponseEntity.ok(surveyService.getSurveyResults(id));
    }
}
