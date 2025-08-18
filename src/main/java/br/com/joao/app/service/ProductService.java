package br.com.joao.app.service;

import br.com.joao.app.domain.Product;
import br.com.joao.app.domain.exception.ProductAlreadyRegisteredException;
import br.com.joao.app.domain.exception.ProductNotFoundException;
import br.com.joao.app.dto.ProductEditRequest;
import br.com.joao.app.dto.ProductRequest;
import br.com.joao.app.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product registerProduct(ProductRequest req) {

        if(productRepository.findByNameIgnoreCase(req.name()).isPresent()){
            throw new ProductAlreadyRegisteredException();
        }

        return productRepository.save(new Product(req.name(), req.stockQuantity(), req.price(), req.description()));
    }

    public Product updateProduct(ProductEditRequest req, Long id) {

        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if(req.stockQuantity() != null){
            product.setStockQuantity(req.stockQuantity());
        }

        if(req.price() != null){
            product.setPrice(req.price());
        }

        if(req.description() != null){
            product.setDescription(req.description());
        }

        return productRepository.save(product);

    }

    public void deleteProduct(Long id) {

        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.deleteById(product.getId());

    }
}
