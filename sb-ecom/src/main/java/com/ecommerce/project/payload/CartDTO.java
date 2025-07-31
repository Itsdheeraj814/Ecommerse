package com.ecommerce.project.payload;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private Long cartId;
    private double totalPrice = 0.0;
    private List<ProductDTO> products=new ArrayList<>();

    public CartDTO() {}

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public CartDTO(Long cartId, double totalPrice, List<ProductDTO> products) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    @Override
    public String toString() {
        return "CartDTO{" +
                "cartId=" + cartId +
                ", totalPrice=" + totalPrice +
                ", products=" + products +
                '}';
    }
}
