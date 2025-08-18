package br.com.joao.app.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ProductAlreadyRegisteredException extends DomainException {

    @Override
    public ProblemDetail toProblemDetail() {

        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Product Already Registered");
        pb.setDetail("Product already registered, try another one.");

        return pb;
    }
}
