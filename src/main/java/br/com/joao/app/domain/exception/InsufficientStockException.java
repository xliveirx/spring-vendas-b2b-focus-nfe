package br.com.joao.app.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InsufficientStockException extends DomainException {

    @Override
    public ProblemDetail toProblemDetail() {

        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Insufficient Stock");
        pb.setDetail("There's less items in stock than the amount requested.");

        return pb;
    }
}
