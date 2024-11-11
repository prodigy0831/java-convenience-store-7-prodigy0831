package service;

import domain.Product;
import repository.ProductRepository;

public class RequestedProduct {
    private final static String PRODUCT_NOT_FOUND = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
    private final static String OUT_OF_STOCK = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";

    private final String productName;
    private final int quantity;
    private final int price;

    public RequestedProduct(String productName, int quantity, ProductRepository productRepository) {
        Product matchedProduct = findProduct(productName, productRepository);
        validateQuantity(matchedProduct, quantity);

        this.productName = productName;
        this.quantity = quantity;
        this.price = matchedProduct.getPrice();
    }

    private void validateQuantity(Product matchedProduct, int quantity) {
        if (matchedProduct.getTotalQuantity() < quantity) {
            throw new IllegalArgumentException(OUT_OF_STOCK);
        }
    }

    private Product findProduct(String productName, ProductRepository productRepository) {
        return productRepository.findProductByName(productName)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_NOT_FOUND));
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
