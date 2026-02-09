package br.edu.ifrn.polling_and_voting.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    @Builder.Default
    private String type = "Bearer";
    private String username;
    private String email;
    private String role;
}
