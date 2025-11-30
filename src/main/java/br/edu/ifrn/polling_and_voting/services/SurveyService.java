package br.edu.ifrn.polling_and_voting.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifrn.polling_and_voting.domain.dto.SurveyCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyDetailResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyResultDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.SurveyUpdateDTO;
import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import br.edu.ifrn.polling_and_voting.repositories.SurveyRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepository surveyRepository;

    @Transactional(readOnly = true)
    public List<SurveyResponseDTO> listSurveys() {
        return surveyRepository.findAll().stream()
                .map(SurveyResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SurveyDetailResponseDTO getSurveyById(UUID id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pesquisa não encontrada"));
        return SurveyDetailResponseDTO.fromEntity(survey);
    }

    @Transactional
    public SurveyResponseDTO createSurvey(SurveyCreateDTO dto) {
        Survey survey = new Survey();
        survey.setTitle(dto.getTitle());
        survey.setExpiresAt(dto.getExpiresAt());
        survey.setActive(true);

        Survey savedSurvey = surveyRepository.save(survey);
        return SurveyResponseDTO.fromEntity(savedSurvey);
    }

    @Transactional
    public SurveyDetailResponseDTO updateSurvey(UUID id, SurveyUpdateDTO dto) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pesquisa não encontrada"));

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            survey.setTitle(dto.getTitle());
        }

        if (dto.getExpiresAt() != null) {
            survey.setExpiresAt(dto.getExpiresAt());
        }

        Survey updatedSurvey = surveyRepository.save(survey);
        return SurveyDetailResponseDTO.fromEntity(updatedSurvey);
    }

    @Transactional
    public void deleteSurvey(UUID id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pesquisa não encontrada"));
        surveyRepository.delete(survey);
    }

    @Transactional(readOnly = true)
    public SurveyResultDTO getSurveyResults(UUID id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pesquisa não encontrada"));

        long totalVotes = survey.getOptions().stream()
                .mapToLong(option -> option.getVotes() != null ? option.getVotes().size() : 0)
                .sum();

        List<SurveyResultDTO.OptionResultDTO> results = survey.getOptions().stream()
                .map(option -> {
                    long votesCount = option.getVotes() != null ? option.getVotes().size() : 0;
                    double percentage = totalVotes > 0 ? (votesCount * 100.0) / totalVotes : 0.0;
                    return new SurveyResultDTO.OptionResultDTO(
                            option.getId(),
                            option.getText(),
                            votesCount,
                            Math.round(percentage * 100.0) / 100.0
                    );
                })
                .collect(Collectors.toList());

        return new SurveyResultDTO(survey.getId(), survey.getTitle(), totalVotes, results);
    }
}
