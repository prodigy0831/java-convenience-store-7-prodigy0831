package service;

import domain.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import repository.ProductRepository;

public class OrderService {
    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }




}

