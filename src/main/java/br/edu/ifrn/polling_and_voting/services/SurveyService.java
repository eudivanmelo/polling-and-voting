package br.edu.ifrn.polling_and_voting.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import br.edu.ifrn.polling_and_voting.repositories.SurveyRepository;

@Service
public class SurveyService {
    private final SurveyRepository surveyRepository;

    public SurveyService(SurveyRepository surveyRepository){
        this.surveyRepository = surveyRepository;
    }

    public List<Survey> listSurveys() {
        return surveyRepository.findAll();
    }
}
