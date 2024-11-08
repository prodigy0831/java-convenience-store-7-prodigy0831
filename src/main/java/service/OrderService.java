package service;

import java.util.HashMap;
import java.util.Map;
import repository.ProductRepository;

public class OrderService {
    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Map<String, Integer> applyPromotion(Map<String,Integer> requiredItems){
        Map<String, Integer> promoDiscounts = new HashMap<>();

        for (Map.Entry<String,Integer> entry:requiredItems.entrySet()){
            String itemName = entry.getKey();
            int quantity = entry.getValue();


        }
    }
}
