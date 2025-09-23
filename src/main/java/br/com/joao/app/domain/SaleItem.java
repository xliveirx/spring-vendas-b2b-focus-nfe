package br.com.joao.app.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;

    private int quantity;

    private BigDecimal unitPrice;

    BigDecimal getSubtotal(){
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public SaleItem(String name, Product product, ServiceEntity service, Sale sale, int quantity, BigDecimal unitPrice) {
        this.name = name;
        this.product = product;
        this.service = service;
        this.sale = sale;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public SaleItem() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
