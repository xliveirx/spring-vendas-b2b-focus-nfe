package br.com.joao.app.dto;

import br.com.joao.app.domain.ItemType;
import br.com.joao.app.domain.Product;
import br.com.joao.app.domain.SaleItem;
import br.com.joao.app.domain.ServiceEntity;

import java.math.BigDecimal;

public record SaleItemResponse(Long id, ItemType itemType, String name, int quantity, BigDecimal unitPrice) {

    public static SaleItemResponse fromDomain(SaleItem item) {
        if (item.getProduct() != null) {
            Product p = item.getProduct();
            return new SaleItemResponse(
                    item.getId(),
                    ItemType.PRODUCT,
                    p.getName(),
                    item.getQuantity(),
                    item.getUnitPrice()
            );
        } else {
            ServiceEntity s = item.getService();
            return new SaleItemResponse(
                    item.getId(),
                    ItemType.SERVICE,
                    s.getName(),
                    item.getQuantity(),
                    item.getUnitPrice()
            );
        }
    }

}
