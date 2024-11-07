package service;

import domain.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import repository.ProductRepository;

public class InputHandler {
    private final static String INVALID_INPUT_FORMAT = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private final static String PRODUCT_NOT_FOUND = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    private static final Pattern INPUT_PATTERN = Pattern.compile(("\\[(.+)-([0-9]+)]"));

    public static void processInput(String items, ProductRepository productRepository) {
        if (items == null || items.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
        List<Arrays> itemList = new ArrayList<>();
        for (String item : items.split(",")) {
            Matcher matcher = INPUT_PATTERN.matcher(item);

            if (!matcher.matches()) {
                throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
            }
            String productName = matcher.group(1);
            String productAmount = matcher.group(2);

            List<Product> matchedProducts = productRepository.findProductByName(productName);
            if (matchedProducts.isEmpty()) {
                throw new IllegalArgumentException(PRODUCT_NOT_FOUND);
            }
        }
    }
}
