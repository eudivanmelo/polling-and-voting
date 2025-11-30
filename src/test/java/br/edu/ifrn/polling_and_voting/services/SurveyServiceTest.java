package br.edu.ifrn.polling_and_voting.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifrn.polling_and_voting.domain.dto.SurveyCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyUpdateDTO;
import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import br.edu.ifrn.polling_and_voting.repositories.SurveyRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do SurveyService")
class SurveyServiceTest {
    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService surveyService;

    private Survey survey;

    @BeforeEach
    void setUp() {
        survey = new Survey();
        survey.setId(UUID.randomUUID());
        survey.setTitle("Pesquisa Teste");
        survey.setActive(true);
        survey.setCreatedAt(LocalDateTime.now());
        survey.setExpiresAt(LocalDateTime.now().plusDays(5));
        survey.setOptions(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve buscar pesquisa por ID com sucesso")
    void shouldGetSurveyById() {
        when(surveyRepository.findById(survey.getId())).thenReturn(Optional.of(survey));
        var result = surveyService.getSurveyById(survey.getId());
        assertNotNull(result);
        assertEquals(survey.getId(), result.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar pesquisa inexistente")
    void shouldThrowWhenSurveyNotFound() {
        when(surveyRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> surveyService.getSurveyById(UUID.randomUUID()));
    }

    @Test
    @DisplayName("Deve criar pesquisa com sucesso")
    void shouldCreateSurvey() {
        SurveyCreateDTO dto = new SurveyCreateDTO();
        dto.setTitle("Nova Pesquisa");
        dto.setExpiresAt(LocalDateTime.now().plusDays(2));
        when(surveyRepository.save(any(Survey.class))).thenReturn(survey);
        var result = surveyService.createSurvey(dto);
        assertNotNull(result);
        assertEquals(survey.getTitle(), result.getTitle());
    }

    @Test
    @DisplayName("Deve atualizar pesquisa com sucesso")
    void shouldUpdateSurvey() {
        SurveyUpdateDTO dto = new SurveyUpdateDTO();
        dto.setTitle("Atualizada");
        dto.setExpiresAt(LocalDateTime.now().plusDays(10));
        when(surveyRepository.findById(survey.getId())).thenReturn(Optional.of(survey));
        when(surveyRepository.save(any(Survey.class))).thenReturn(survey);
        var result = surveyService.updateSurvey(survey.getId(), dto);
        assertNotNull(result);
        assertEquals("Atualizada", result.getTitle());
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar pesquisa inexistente")
    void shouldThrowWhenUpdateSurveyNotFound() {
        SurveyUpdateDTO dto = new SurveyUpdateDTO();
        when(surveyRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> surveyService.updateSurvey(UUID.randomUUID(), dto));
    }

    @Test
    @DisplayName("Deve deletar pesquisa com sucesso")
    void shouldDeleteSurvey() {
        when(surveyRepository.findById(survey.getId())).thenReturn(Optional.of(survey));
        doNothing().when(surveyRepository).delete(survey);
        assertDoesNotThrow(() -> surveyService.deleteSurvey(survey.getId()));
        verify(surveyRepository, times(1)).delete(survey);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar pesquisa inexistente")
    void shouldThrowWhenDeleteSurveyNotFound() {
        when(surveyRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> surveyService.deleteSurvey(UUID.randomUUID()));
    }
}
