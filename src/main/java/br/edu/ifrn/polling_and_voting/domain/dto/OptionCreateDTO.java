package br.edu.ifrn.polling_and_voting.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionCreateDTO {
    @NotBlank(message = "O texto da opção é obrigatório")
    @Size(min = 1, max = 100, message = "O texto deve ter entre 1 e 100 caracteres")
    private String text;
}
