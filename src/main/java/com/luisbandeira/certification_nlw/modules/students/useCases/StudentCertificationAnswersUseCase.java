package com.luisbandeira.certification_nlw.modules.students.useCases;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisbandeira.certification_nlw.modules.questions.entities.QuestionEntity;
import com.luisbandeira.certification_nlw.modules.questions.repositories.QuestionRepository;
import com.luisbandeira.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.luisbandeira.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.luisbandeira.certification_nlw.modules.students.entities.AnswersCertificationsEntity;
import com.luisbandeira.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.luisbandeira.certification_nlw.modules.students.entities.StudentEntity;
import com.luisbandeira.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import com.luisbandeira.certification_nlw.modules.students.repositories.StudentRepository;
@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception{
        var hasCertification = this.verifyIfHasCertificationUseCase.execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hasCertification) {
            throw new Exception("Student already has this certification");
        }

        // buscar as alternativas corretas
        List<QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionAnswers().stream().forEach(questionAnswer -> {
            var question = questionsEntity.stream().filter(q -> q.getId().equals(questionAnswer.getQuestionID())).findFirst().get();

            var correctAlternative = question.getAlternatives().stream().filter(a -> a.isCorrect()).findFirst().get();

            if (correctAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                questionAnswer.setCorrect(true);
                correctAnswers.getAndIncrement();
            } else {
                questionAnswer.setCorrect(false);
            }

            var answersCertificationsEntity = AnswersCertificationsEntity.builder()
            .answerID(questionAnswer.getAlternativeID())
            .questionID(questionAnswer.getQuestionID())
            .isCorrect(questionAnswer.isCorrect()).build();

            answersCertifications.add(answersCertificationsEntity);
        });

        // Verificar se existe o estudante
        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentId;
        if (student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentId = studentCreated.getId();
        } else {
            studentId = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity = 
            CertificationStudentEntity.builder()
            .studentID(studentId)
            .technology(dto.getTechnology())
            .grade(correctAnswers.get())
            .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answer -> {
            answer.setCertificationID(certificationStudentCreated.getId());
            answer.setCertificationStudentEntity(certificationStudentCreated);
        });

        certificationStudentEntity.setAnswersCertificationsEntity(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);

        return certificationStudentCreated;
            
        // Salvar as informacoes da certificacao
    }
}
