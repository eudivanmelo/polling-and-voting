package br.edu.ifrn.polling_and_voting.services;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifrn.polling_and_voting.domain.dto.VoteCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.VoteResponseDTO;
import br.edu.ifrn.polling_and_voting.domain.entities.Option;
import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import br.edu.ifrn.polling_and_voting.domain.entities.Vote;
import br.edu.ifrn.polling_and_voting.repositories.OptionRepository;
import br.edu.ifrn.polling_and_voting.repositories.VoteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final OptionRepository optionRepository;

    @Transactional
    public VoteResponseDTO registerVote(VoteCreateDTO dto) {
        Option option = optionRepository.findById(dto.getOptionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Opção não encontrada"));

        Survey survey = option.getSurvey();

        if (!survey.isActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A pesquisa está inativa");
        }

        if (survey.getExpiresAt() != null && survey.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A pesquisa expirou");
        }

        Vote vote = new Vote();
        vote.setOption(option);

        Vote savedVote = voteRepository.save(vote);
        return VoteResponseDTO.fromEntity(savedVote);
    }
}
