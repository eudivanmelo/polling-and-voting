package br.edu.ifrn.polling_and_voting.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResponseDTO {
    private UUID id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active;
    private Long totalVotes;

    public static SurveyResponseDTO fromEntity(Survey survey) {
        SurveyResponseDTO dto = new SurveyResponseDTO();
        dto.setId(survey.getId());
        dto.setTitle(survey.getTitle());
        dto.setCreatedAt(survey.getCreatedAt());
        dto.setExpiresAt(survey.getExpiresAt());
        dto.setActive(survey.isActive());
        
        long totalVotes = 0;
        if (survey.getOptions() != null) {
            totalVotes = survey.getOptions().stream()
                .mapToLong(option -> option.getVotes() != null ? option.getVotes().size() : 0)
                .sum();
        }
        dto.setTotalVotes(totalVotes);
        
        return dto;
    }
}
