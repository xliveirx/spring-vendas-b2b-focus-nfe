package br.com.joao.app.controller;

import br.com.joao.app.domain.User;
import br.com.joao.app.dto.SaleRequest;
import br.com.joao.app.dto.SaleResponse;
import br.com.joao.app.service.SaleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@RequestBody @Valid SaleRequest req,
                                                   @AuthenticationPrincipal User logged){

        var sale = saleService.createSale(req, logged);

    }



}
