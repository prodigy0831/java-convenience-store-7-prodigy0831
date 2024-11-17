package service;

import java.util.ArrayList;
import repository.ProductRepository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputHandler {
    private final static String INVALID_INPUT_FORMAT = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private static final Pattern INPUT_PATTERN = Pattern.compile("\\[(.+)-([0-9]+)]");
    public static final String COMMA_PATTERN = ",";
    public static final String PAIR_PATTERN = "\\[.+?]";
    public static final String DELIMITER = ",";

    private final ProductRepository productRepository;

    public InputHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<RequestedProduct> processInput(String items) {
        validateInputFormat(items);
        return parseRequestedProductList(items);
    }

    private void validateInputFormat(String items) {
        if (items == null || items.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
        int pairCount = countPattern(items, PAIR_PATTERN);
        int commaCount = countPattern(items, COMMA_PATTERN);

        if (pairCount > 1 && commaCount != pairCount - 1) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
    }

    private int countPattern(String input, String pattern) {
        Matcher matcher = Pattern.compile(pattern).matcher(input);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private List<RequestedProduct> parseRequestedProductList(String items) {
        Map<String, Integer> productMap = parseProductMap(items);
        return createRequestedProduct(productMap);
    }

    private List<RequestedProduct> createRequestedProduct(Map<String, Integer> productMap) {
        List<RequestedProduct> requestedProductList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : productMap.entrySet()) {
            RequestedProduct requestedProduct = new RequestedProduct(entry.getKey(), entry.getValue(),
                    productRepository);
            requestedProductList.add(requestedProduct);
        }
        return requestedProductList;
    }

    private Map<String, Integer> parseProductMap(String items) {
        Map<String, Integer> productMap = new LinkedHashMap<>();
        for (String item : items.split(DELIMITER)) {
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
