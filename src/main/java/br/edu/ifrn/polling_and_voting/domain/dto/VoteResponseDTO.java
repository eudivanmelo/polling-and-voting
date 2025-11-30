package br.edu.ifrn.polling_and_voting.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.edu.ifrn.polling_and_voting.domain.entities.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResponseDTO {
    private UUID id;
    private LocalDateTime dateVote;
    private UUID optionId;
    private String optionText;

    public static VoteResponseDTO fromEntity(Vote vote) {
        VoteResponseDTO dto = new VoteResponseDTO();
        dto.setId(vote.getId());
        dto.setDateVote(vote.getDateVote());
        dto.setOptionId(vote.getOption().getId());
        dto.setOptionText(vote.getOption().getText());
        return dto;
    }
}
