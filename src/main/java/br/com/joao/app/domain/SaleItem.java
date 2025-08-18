package br.com.joao.app.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_items")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public SaleItem(Product product, ServiceEntity service, Sale sale, int quantity, BigDecimal unitPrice) {
        this.product = product;
        this.service = service;
        this.sale = sale;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
