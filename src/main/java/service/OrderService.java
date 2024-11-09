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

    public int getProductPrice(String productName) {
        return productRepository.findProductByName(productName).stream()
                .findFirst()
                .map(Product::getPrice)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }

    public Map<String, Integer> applyPromotion(Map<String, Integer> requiredProducts) {
        Map<String, Integer> promoDiscounts = new HashMap<>();

        for (Map.Entry<String, Integer> entry : requiredProducts.entrySet()) {
            String productName = entry.getKey();
            int requiredQuantity = entry.getValue();

            if (productRepository.hasPromo(productName)) {
                List<Product> stockProducts = productRepository.findProductByName(productName);
                promoDiscounts.put(productName, applyPromotionWithBothStocks(productName, requiredQuantity));
                continue;
            }
        }
        return promoDiscounts;
    }

    private Integer applyPromotionWithBothStocks(String productName, int requiredQuantity) {
        Product promoProduct = productRepository.findProductByNameWithPromo(productName);
        Product nonPromoProduct = productRepository.findProductByNameWithoutPromo(productName);
        int promoProductQuantity = promoProduct.getQuantity();
        return promoProductQuantity;

    }

}

