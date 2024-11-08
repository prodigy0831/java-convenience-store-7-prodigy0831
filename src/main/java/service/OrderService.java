package service;

import domain.Product;
import domain.PromotionType;
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
            int requiredquantity = entry.getValue();
            List<Product> stockProducts = productRepository.findProductByName(productName);

            Product promoProduct = stockProducts.stream()
                    .filter(product -> product.getPromotionType() != PromotionType.NONE)
                    .findFirst()
                    .orElse(null);
            Product nonPromoProduct = stockProducts.stream()
                    .filter(product -> product.getPromotionType() == PromotionType.NONE)
                    .findFirst()
                    .orElse(null);
            if (promoProduct == null || promoProduct.getQuantity() == 0) {
                continue;
            }


        }
        return promoDiscounts;
    }
}

