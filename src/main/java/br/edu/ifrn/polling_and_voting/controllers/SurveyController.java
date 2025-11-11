package br.edu.ifrn.polling_and_voting.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifrn.polling_and_voting.controllers.docs.SurveyControllerDoc;
import br.edu.ifrn.polling_and_voting.domain.entities.Survey;
import br.edu.ifrn.polling_and_voting.services.SurveyService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/surveys")
@RequiredArgsConstructor
public class SurveyController implements SurveyControllerDoc {
    private final SurveyService surveyService;

    @GetMapping
    public List<Survey> getSurveys(){
        return surveyService.listSurveys();
    }
}
