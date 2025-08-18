package br.com.joao.app.service;

import br.com.joao.app.domain.Product;
import br.com.joao.app.domain.exception.ProductAlreadyRegisteredException;
import br.com.joao.app.domain.exception.ProductNotFoundException;
import br.com.joao.app.dto.ProductEditRequest;
import br.com.joao.app.dto.ProductRequest;
import br.com.joao.app.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product registerProduct(ProductRequest req) {

        if(productRepository.findByNameIgnoreCase(req.name()).isPresent()){
            throw new ProductAlreadyRegisteredException();
        }

        return productRepository.save(new Product(req.name(), req.stockQuantity(), req.price(), req.description()));
    }

    @Transactional
    public Product updateProduct(ProductEditRequest req, Long id) {

        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        if(req == null){
            return product;
        }

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

    @Transactional
    public void deleteProduct(Long id) {

        var product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.deleteById(product.getId());

    }

    public Page<Product> getProducts(Pageable pageable) {

        return productRepository.findAll(pageable);
    }
}
