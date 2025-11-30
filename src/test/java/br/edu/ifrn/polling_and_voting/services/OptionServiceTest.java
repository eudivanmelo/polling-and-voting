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

import br.edu.ifrn.polling_and_voting.domain.dto.OptionCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.entities.Option;
import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import br.edu.ifrn.polling_and_voting.repositories.OptionRepository;
import br.edu.ifrn.polling_and_voting.repositories.SurveyRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do OptionService")
class OptionServiceTest {
    @Mock
    private OptionRepository optionRepository;
    @Mock
    private SurveyRepository surveyRepository;
    @InjectMocks
    private OptionService optionService;

    private Survey survey;
    private Option option;

    @BeforeEach
    void setUp() {
        survey = new Survey();
        survey.setId(UUID.randomUUID());
        survey.setTitle("Pesquisa Teste");
        survey.setActive(true);
        survey.setExpiresAt(LocalDateTime.now().plusDays(5));
        survey.setOptions(new ArrayList<>());

        option = new Option();
        option.setId(UUID.randomUUID());
        option.setText("Opção Teste");
        option.setSurvey(survey);
        option.setVotes(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve adicionar opção à pesquisa ativa")
    void shouldAddOptionToActiveSurvey() {
        OptionCreateDTO dto = new OptionCreateDTO();
        dto.setText("Nova Opção");
        when(surveyRepository.findById(survey.getId())).thenReturn(Optional.of(survey));
        when(optionRepository.save(any(Option.class))).thenReturn(option);
        var result = optionService.addOptionToSurvey(survey.getId(), dto);
        assertNotNull(result);
        assertEquals(option.getText(), result.getText());
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar opção em pesquisa inativa")
    void shouldThrowWhenAddOptionToInactiveSurvey() {
        survey.setActive(false);
        OptionCreateDTO dto = new OptionCreateDTO();
        when(surveyRepository.findById(survey.getId())).thenReturn(Optional.of(survey));
        assertThrows(ResponseStatusException.class, () -> optionService.addOptionToSurvey(survey.getId(), dto));
    }

    @Test
    @DisplayName("Deve lançar exceção ao adicionar opção em pesquisa inexistente")
    void shouldThrowWhenAddOptionToNonexistentSurvey() {
        OptionCreateDTO dto = new OptionCreateDTO();
        when(surveyRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> optionService.addOptionToSurvey(UUID.randomUUID(), dto));
    }

    @Test
    @DisplayName("Deve remover opção sem votos")
    void shouldDeleteOptionWithoutVotes() {
        when(optionRepository.findById(option.getId())).thenReturn(Optional.of(option));
        doNothing().when(optionRepository).delete(option);
        assertDoesNotThrow(() -> optionService.deleteOption(option.getId()));
        verify(optionRepository, times(1)).delete(option);
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover opção com votos")
    void shouldThrowWhenDeleteOptionWithVotes() {
        option.getVotes().add(mock(br.edu.ifrn.polling_and_voting.domain.entities.Vote.class));
        when(optionRepository.findById(option.getId())).thenReturn(Optional.of(option));
        assertThrows(ResponseStatusException.class, () -> optionService.deleteOption(option.getId()));
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover opção inexistente")
    void shouldThrowWhenDeleteOptionNotFound() {
        when(optionRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> optionService.deleteOption(UUID.randomUUID()));
    }
}
