package br.com.joao.app.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class CnpjAlreadyRegistered extends DomainException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("CNPJ already registered.");
        pb.setDetail("CNPJ already registered, it must be unique. Try again.");

        return pb;
    }
}
