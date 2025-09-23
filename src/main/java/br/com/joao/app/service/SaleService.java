package br.com.joao.app.service;

import br.com.joao.app.domain.*;
import br.com.joao.app.domain.exception.ClientNotFoundException;
import br.com.joao.app.domain.exception.InsufficientStockException;
import br.com.joao.app.domain.exception.ProductNotFoundException;
import br.com.joao.app.domain.exception.ServiceEntityNotFoundException;
import br.com.joao.app.dto.SaleRequest;
import br.com.joao.app.repository.ClientRepository;
import br.com.joao.app.repository.ProductRepository;
import br.com.joao.app.repository.SaleRepository;
import br.com.joao.app.repository.ServiceEntityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final ServiceEntityRepository serviceRepository;
    private final NfeService nfeService;


    public SaleService(SaleRepository saleRepository, ClientRepository clientRepository, ProductRepository productRepository, ServiceEntityRepository serviceRepository, NfeService nfeService) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.serviceRepository = serviceRepository;
        this.nfeService = nfeService;
    }

    public Sale createSale(SaleRequest req, User logged) {

        Client client = clientRepository.findByIdAndActiveTrue(req.clientId())
                .orElseThrow(ClientNotFoundException::new);

        Sale sale = new Sale();
        sale.setClient(client);
        sale.setCreatedBy(logged);
        sale.setCreatedAt(LocalDateTime.now());

        List<SaleItem> items = req.items()
                .stream()
                .map(i -> {

                    SaleItem item = null;

                    if(i.itemType().equals(ItemType.PRODUCT)){

                        Product product = productRepository.findById(i.itemId())
                                .orElseThrow(ProductNotFoundException::new);

                        if(product.getStockQuantity() < i.quantity()){
                            throw new InsufficientStockException();
                        }

                        product.setStockQuantity(product.getStockQuantity() - i.quantity());
                        productRepository.save(product);

                        item = new SaleItem(product.getName(), product, null, sale, i.quantity(), product.getPrice());
                    }

                    else if(i.itemType().equals(ItemType.SERVICE)){

                        ServiceEntity service = serviceRepository.findById(i.itemId())
                                .orElseThrow(ServiceEntityNotFoundException::new);

                        item = new SaleItem(service.getName(),null, service, sale, i.quantity(), service.getPrice());
                    }

                    return item;
                }).toList();

        sale.setItems(items);
        sale.calculateTotal();

        Sale saved = saleRepository.save(sale);

        try {

            nfeService.emitBySale(sale.getId());

        } catch(Exception e) {

            e.printStackTrace();
        }

        return saved;
    }
}
