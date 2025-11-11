package br.edu.ifrn.polling_and_voting.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifrn.polling_and_voting.domain.entities.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

}
