package br.com.joao.app.controller;

import br.com.joao.app.dto.ProductEditRequest;
import br.com.joao.app.dto.ProductRequest;
import br.com.joao.app.dto.ProductResponse;
import br.com.joao.app.dto.ServiceResponse;
import br.com.joao.app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> registerProduct(@RequestBody @Valid ProductRequest req){

        var product = productService.registerProduct(req);

        var res = ProductResponse.fromDomain(product);

        return ResponseEntity.created(URI.create("/products/" + product.getId())).body(res);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                         @RequestBody(required = false) @Valid ProductEditRequest req){

        var product = productService.updateProduct(req, id);

        var res = ProductResponse.fromDomain(product);

        return ResponseEntity.ok(res);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<Page<ProductResponse>> getProducts(@RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

        var services = productService.getProducts(pageable);

        var res = services
                .map(ProductResponse::fromDomain);

        return ResponseEntity.ok(res);
    }
}
