package com.luisbandeira.certification_nlw.modules.students.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luisbandeira.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import com.luisbandeira.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import com.luisbandeira.certification_nlw.modules.students.entities.CertificationStudentEntity;
import com.luisbandeira.certification_nlw.modules.students.useCases.StudentCertificationAnswersUseCase;
import com.luisbandeira.certification_nlw.modules.students.useCases.VerifyIfHasCertificationUseCase;

@RestController
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    @Autowired
    private StudentCertificationAnswersUseCase studentCertificationAnswerUseCase;

    @PostMapping("/verifyIfHasCertification")
    public String verifyIfHasCertification(@RequestBody VerifyHasCertificationDTO verifyHasCertificationDTO) {

        var result = this.verifyIfHasCertificationUseCase.execute(verifyHasCertificationDTO);

        if(result) {
            return "User has the certificate!";
        }
        return "Exam available!";
    }

    @PostMapping("/certification/answer")
    public ResponseEntity<Object> certificationAnswer(@RequestBody StudentCertificationAnswerDTO dto) throws Exception {
        try {
            var result = studentCertificationAnswerUseCase.execute(dto);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
