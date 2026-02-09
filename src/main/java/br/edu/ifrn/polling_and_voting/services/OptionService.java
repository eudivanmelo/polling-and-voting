package br.edu.ifrn.polling_and_voting.services;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import br.edu.ifrn.polling_and_voting.domain.dto.OptionCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.OptionResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.entities.Option;
import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import br.edu.ifrn.polling_and_voting.domain.entities.User;
import br.edu.ifrn.polling_and_voting.repositories.OptionRepository;
import br.edu.ifrn.polling_and_voting.repositories.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@RequiredArgsConstructor
public class OptionService {
    private final OptionRepository optionRepository;
    private final SurveyRepository surveyRepository;

    @Transactional
    public OptionResponseDTO addOptionToSurvey(UUID surveyId, OptionCreateDTO dto) {
        // Obter usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pesquisa não encontrada"));

        // Verificar se o usuário é o criador da pesquisa
        if (!survey.getCreator().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para adicionar opções a esta pesquisa");
        }

        if (!survey.isActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível adicionar opções a uma pesquisa inativa");
        }

        Option option = new Option();
        option.setText(dto.getText());
        option.setSurvey(survey);

        Option savedOption = optionRepository.save(option);
        return OptionResponseDTO.fromEntity(savedOption);
    }

    @Transactional
    public void deleteOption(UUID optionId) {
        // Obter usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Option option = optionRepository.findById(optionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opção não encontrada"));

        // Verificar se o usuário é o criador da pesquisa
        if (!option.getSurvey().getCreator().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não tem permissão para deletar esta opção");
        }

        if (option.getVotes() != null && !option.getVotes().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível remover uma opção que já possui votos");
        }

        optionRepository.delete(option);
    }
}
