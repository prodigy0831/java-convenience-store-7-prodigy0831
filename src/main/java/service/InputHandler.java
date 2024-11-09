package service;

import domain.Product;
import java.util.ArrayList;
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

    public List<RequestedProduct> processInput(String items) {
        validateInputFormat(items);

        return parseRequestedProductList(items);
    }

    private List<RequestedProduct> parseRequestedProductList(String items) {
        Map<String, Integer> productMap = new LinkedHashMap<>();//중복되는 item 주문을 한번에 처리
        for (String item : items.split(",")) {
            Matcher matcher = validateAndMatchInput(item);
            String productName = matcher.group(1);
            int productAmount = Integer.parseInt(matcher.group(2));

            productMap.put(productName, productMap.getOrDefault(productName, 0) + productAmount);
        }

        List<RequestedProduct> requestedProductList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : productMap.entrySet()) {
            RequestedProduct requestedProduct = new RequestedProduct(entry.getKey(), entry.getValue(),
                    productRepository);
            requestedProductList.add(requestedProduct);
        }
        return requestedProductList;
    }

    private void validateInputFormat(String items) {
        if (items == null || items.isBlank()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
    }


    private Matcher validateAndMatchInput(String item) {
        Matcher matcher = INPUT_PATTERN.matcher(item);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
        return matcher;
    }

}
