package br.com.joao.app.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PasswordsDontMatchException extends DomainException {

    @Override
    public ProblemDetail toProblemDetail() {

        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Passwords don't match");
        pb.setDetail("Passwords don't match, try again.");

        return pb;
    }


}
