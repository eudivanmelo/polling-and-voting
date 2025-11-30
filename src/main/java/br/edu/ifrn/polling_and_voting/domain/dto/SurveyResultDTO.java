package br.edu.ifrn.polling_and_voting.domain.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurveyResultDTO {
    private UUID surveyId;
    private String surveyTitle;
    private Long totalVotes;
    private List<OptionResultDTO> results;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OptionResultDTO {
        private UUID optionId;
        private String optionText;
        private Long votesCount;
        private Double percentage;
    }
}
