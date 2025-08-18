package br.com.joao.app.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CompanyNameAlreadyRegisteredException extends DomainException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Company name already registered.");
        pb.setDetail("Company name already registered, it must be unique. Try again.");

        return pb;
    }
}
