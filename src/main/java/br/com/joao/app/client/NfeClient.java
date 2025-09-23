package br.com.joao.app.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "nfeClient", url = "https://homologacao.focusnfe.com.br/v2")
public interface NfeClient {

    @PostMapping(value = "/nfe", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> sendNfe(@RequestParam("ref") String ref, @RequestBody JsonNode payload);

    @GetMapping(value = "/nfe/{ref}")
    ResponseEntity<JsonNode> checkNfe(@PathVariable("ref") String ref);

}
