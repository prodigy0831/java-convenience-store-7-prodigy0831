package service;

import domain.Product;
import repository.ProductRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    private final static String INVALID_INPUT_FORMAT = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private final static String PRODUCT_NOT_FOUND = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    private final static String OUT_OF_STOCK = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
    private static final Pattern INPUT_PATTERN = Pattern.compile("\\[(.+)-([0-9]+)]");

    private final ProductRepository productRepository;

    public InputHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Map<String, Integer> processInput(String items) {
        validateInputFormat(items);

        Map<String, Integer> parsedProductMap = parseProductMap(items);
        validateProducts(parsedProductMap);

        return parsedProductMap;
    }

    private void validateInputFormat(String items) {
        if (items == null || items.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
    }
    //product에서 validate하기
    private void validateProducts(Map<String, Integer> parsedProductMap) {
        for (Map.Entry<String, Integer> entry : parsedProductMap.entrySet()) {
            String ProductName = entry.getKey();
            int requiredProductQuantity = entry.getValue();

            Product matchedProduct = productRepository.findProductByName(ProductName);
            checkQuantity(matchedProduct, requiredProductQuantity);

        }
    }

    private void checkQuantity(Product matchedProduct, int requiredProductQuantity) {
        int totalStock = matchedProduct.getTotalQauntity();
        if (totalStock < requiredProductQuantity) {
            throw new IllegalArgumentException(OUT_OF_STOCK);
        }
    }

    private Map<String, Integer> parseProductMap(String items) {
        Map<String, Integer> productMap = new LinkedHashMap<>();
        for (String item : items.split(",")) {
            Matcher matcher = validateAndMatchInput(item);
            String productName = matcher.group(1);
            int productAmount = Integer.parseInt(matcher.group(2));
            productMap.put(productName, productMap.getOrDefault(productName, 0) + productAmount);
        }
        return productMap;
    }

    private Matcher validateAndMatchInput(String item) {
        Matcher matcher = INPUT_PATTERN.matcher(item);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
        return matcher;
    }

}
