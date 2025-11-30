package br.edu.ifrn.polling_and_voting.domain.dto;

import java.util.UUID;

import br.edu.ifrn.polling_and_voting.domain.entities.Option;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionResponseDTO {
    private UUID id;
    private String text;
    private Long votesCount;

    public static OptionResponseDTO fromEntity(Option option) {
        OptionResponseDTO dto = new OptionResponseDTO();
        dto.setId(option.getId());
        dto.setText(option.getText());
        dto.setVotesCount(option.getVotes() != null ? (long) option.getVotes().size() : 0L);
        return dto;
    }
}
