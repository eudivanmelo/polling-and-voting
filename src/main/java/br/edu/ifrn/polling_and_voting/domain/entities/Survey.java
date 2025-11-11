package br.edu.ifrn.polling_and_voting.domain.entities;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @NotBlank
    @NotNull
    private String title;

    @CreationTimestamp
    private LocalDateTime created_at;

    private LocalDateTime expires_at;

    @NotBlank
    @NotNull
    private boolean active = true;

    @OneToMany
    private Set<Option> options;
}
