package br.com.joao.app.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ServiceEntityNotFoundException extends DomainException {


    @Override
    public ProblemDetail toProblemDetail() {

        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        pb.setTitle("Service not Found");
        pb.setDetail("Service not found, try another one.");

        return pb;
    }

}
