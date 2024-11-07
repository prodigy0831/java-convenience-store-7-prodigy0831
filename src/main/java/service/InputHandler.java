package service;

import domain.Product;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import repository.ProductRepository;

public class InputHandler {
    private final static String INVALID_INPUT_FORMAT = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private final static String PRODUCT_NOT_FOUND = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    private final static String OUT_OF_STOCK = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    private static final Pattern INPUT_PATTERN = Pattern.compile(("\\[(.+)-([0-9]+)]"));

    public static Map<String, Integer> processInput(String items, ProductRepository productRepository) {
        if (items == null || items.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
        Map<String, Integer> productMap = createProductMap(items);
        for (String productName : productMap.keySet()) {
            List<Product> matchedProducts = productRepository.findProductByName(productName);
            int totalAmount = 0;
            for (Product product : matchedProducts) {
                totalAmount += product.getQuantity();
            }
            if (matchedProducts.isEmpty()) {
                throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
            }
            if (totalAmount < productMap.get(productName)) {
                throw new IllegalArgumentException(OUT_OF_STOCK);
            }
        }

        return productMap;
    }

    private static Map<String, Integer> createProductMap(String items) {
        Map<String, Integer> productMap = new LinkedHashMap<>();
        for (String item : items.split(",")) {
            Matcher matcher = INPUT_PATTERN.matcher(item);

            if (!matcher.matches()) {
                throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
            }
            String productName = matcher.group(1);
            int productAmount = Integer.parseInt(matcher.group(2));
            productMap.put(productName, productMap.getOrDefault(productName, 0) + productAmount);
        }
        return productMap;
    }
}
