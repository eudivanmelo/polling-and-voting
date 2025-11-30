package br.edu.ifrn.polling_and_voting.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyDetailResponseDTO {
    private UUID id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private boolean active;
    private List<OptionResponseDTO> options;

    public static SurveyDetailResponseDTO fromEntity(Survey survey) {
        SurveyDetailResponseDTO dto = new SurveyDetailResponseDTO();
        dto.setId(survey.getId());
        dto.setTitle(survey.getTitle());
        dto.setCreatedAt(survey.getCreatedAt());
        dto.setExpiresAt(survey.getExpiresAt());
        dto.setActive(survey.isActive());
        
        if (survey.getOptions() != null) {
            dto.setOptions(survey.getOptions().stream()
                .map(OptionResponseDTO::fromEntity)
                .collect(Collectors.toList()));
        } else {
            dto.setOptions(new ArrayList<>());
        }
        
        return dto;
    }
}
