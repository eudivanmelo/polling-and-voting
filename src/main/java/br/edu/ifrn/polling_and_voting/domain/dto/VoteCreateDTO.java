package br.edu.ifrn.polling_and_voting.domain.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteCreateDTO {
    @NotNull(message = "O ID da opção é obrigatório")
    private UUID optionId;
}
