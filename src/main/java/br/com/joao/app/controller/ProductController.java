package br.com.joao.app.controller;

import br.com.joao.app.dto.ProductEditRequest;
import br.com.joao.app.dto.ProductRequest;
import br.com.joao.app.dto.ProductResponse;
import br.com.joao.app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> registerProduct(@RequestBody @Valid ProductRequest req){

        var product = productService.registerProduct(req);

        var res = ProductResponse.fromDomain(product);

        return ResponseEntity.created(URI.create("/product/" + product.getId())).body(res);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody @Valid ProductEditRequest req){

        var product = productService.updateProduct(req, id);

        var res = ProductResponse.fromDomain(product);

        return ResponseEntity.ok(res);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
