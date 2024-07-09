package com.luisbandeira.certification_nlw.modules.questions.controllers;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luisbandeira.certification_nlw.modules.questions.dto.AlternativesResultDTO;
import com.luisbandeira.certification_nlw.modules.questions.dto.QuestionResultDTO;
import com.luisbandeira.certification_nlw.modules.questions.entities.AlternativesEntity;
import com.luisbandeira.certification_nlw.modules.questions.entities.QuestionEntity;
import com.luisbandeira.certification_nlw.modules.questions.repositories.QuestionRepository;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    
    @GetMapping("/technology/{technology}")
    public List<QuestionResultDTO> findByTechnology(@PathVariable String technology) {
        var result = questionRepository.findByTechnology(technology);

        var toMap = result.stream().map(question -> mapQuestionToDto(question))
        .collect(Collectors.toList());
        return toMap;
    }

    static QuestionResultDTO mapQuestionToDto(QuestionEntity question) {
        var questionResultDto = QuestionResultDTO.builder()
        .id(question.getId())
        .technology(question.getTechnology())
        .description(question.getDescription())
        .build();

        List<AlternativesResultDTO> alternativesResultDTO = 
        question.getAlternatives().stream().map(alternativee -> mapAlternativesToDto(alternativee))
        .collect(Collectors.toList());

        questionResultDto.setAlternatives(alternativesResultDTO);

        return questionResultDto;
    }

    static AlternativesResultDTO mapAlternativesToDto(AlternativesEntity alternativesResultDTO) {
        return AlternativesResultDTO.builder()
        .id(alternativesResultDTO.getId())
        .description(alternativesResultDTO.getDescription())
        .build();
    }
}
