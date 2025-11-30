package br.edu.ifrn.polling_and_voting.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrn.polling_and_voting.controllers.docs.VoteControllerDoc;
import br.edu.ifrn.polling_and_voting.domain.dto.VoteCreateDTO;
import br.edu.ifrn.polling_and_voting.domain.dto.VoteResponseDTO;
import br.edu.ifrn.polling_and_voting.services.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VoteController implements VoteControllerDoc {
    private final VoteService voteService;

    @PostMapping("/vote")
    public ResponseEntity<VoteResponseDTO> vote(@Valid @RequestBody VoteCreateDTO dto) {
        VoteResponseDTO response = voteService.registerVote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
