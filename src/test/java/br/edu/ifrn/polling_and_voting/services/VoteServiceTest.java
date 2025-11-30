package br.edu.ifrn.polling_and_voting.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifrn.polling_and_voting.domain.dto.VoteCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.VoteResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.entities.Option;
import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import br.edu.ifrn.polling_and_voting.domain.entities.Vote;
import br.edu.ifrn.polling_and_voting.repositories.OptionRepository;
import br.edu.ifrn.polling_and_voting.repositories.VoteRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do VoteService")
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private OptionRepository optionRepository;

    @InjectMocks
    private VoteService voteService;

    private Survey activeSurvey;
    private Option option;
    private VoteCreateDTO voteCreateDTO;

    @BeforeEach
    void setUp() {
        // Setup de uma pesquisa ativa
        activeSurvey = new Survey();
        activeSurvey.setId(UUID.randomUUID());
        activeSurvey.setTitle("Pesquisa Teste");
        activeSurvey.setActive(true);
        activeSurvey.setExpiresAt(LocalDateTime.now().plusDays(7));

        // Setup de uma opção
        option = new Option();
        option.setId(UUID.randomUUID());
        option.setText("Opção Teste");
        option.setSurvey(activeSurvey);

        // Setup do DTO
        voteCreateDTO = new VoteCreateDTO();
        voteCreateDTO.setOptionId(option.getId());
    }

    @Test
    @DisplayName("Deve registrar voto com sucesso em pesquisa ativa")
    void shouldRegisterVoteSuccessfully() {
        // Arrange
        Vote vote = new Vote();
        vote.setId(UUID.randomUUID());
        vote.setOption(option);
        vote.setDateVote(LocalDateTime.now());

        when(optionRepository.findById(option.getId())).thenReturn(Optional.of(option));
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        // Act
        VoteResponseDTO result = voteService.registerVote(voteCreateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(option.getId(), result.getOptionId());
        assertEquals(option.getText(), result.getOptionText());
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao votar em opção inexistente")
    void shouldThrowExceptionWhenOptionNotFound() {
        // Arrange
        when(optionRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            voteService.registerVote(voteCreateDTO);
        });
        verify(voteRepository, never()).save(any(Vote.class));
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Deve lançar exceção ao votar em pesquisa inativa")
    void shouldThrowExceptionWhenSurveyIsInactive() {
        // Arrange
        activeSurvey.setActive(false);
        when(optionRepository.findById(option.getId())).thenReturn(Optional.of(option));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            voteService.registerVote(voteCreateDTO);
        });
        assertTrue(exception.getReason().contains("inativa"));
        verify(voteRepository, never()).save(any(Vote.class));
    }

    @SuppressWarnings("null")
    @Test
    @DisplayName("Deve lançar exceção ao votar em pesquisa expirada")
    void shouldThrowExceptionWhenSurveyIsExpired() {
        // Arrange
        activeSurvey.setExpiresAt(LocalDateTime.now().minusDays(1));
        when(optionRepository.findById(option.getId())).thenReturn(Optional.of(option));

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            voteService.registerVote(voteCreateDTO);
        });
        assertTrue(exception.getReason().contains("expirou"));
        verify(voteRepository, never()).save(any(Vote.class));
    }
}
