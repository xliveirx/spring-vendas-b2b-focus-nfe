package br.com.joao.app.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EmailAlreadyExistsException extends DomainException {

    @Override
    public ProblemDetail toProblemDetail() {

        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Email Already Exists");
        pb.setDetail("Email already exists in the database, try another");

        return pb;
    }
}
