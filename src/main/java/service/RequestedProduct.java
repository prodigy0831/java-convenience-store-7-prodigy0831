package service;

import domain.Product;
import repository.ProductRepository;

public class RequestedProduct {
    private final String productName;
    private final int quantity;
    private final int price;

    public RequestedProduct(String productName, int quantity, ProductRepository productRepository) {
        Product matchedProduct = productRepository.findProductByName(productName);
        if (matchedProduct == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
        int totalStock = matchedProduct.getTotalQuantity();
        if (totalStock < quantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }

        this.productName = productName;
        this.quantity = quantity;
        this.price = matchedProduct.getPrice();
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice(){
        return price;
    }
}
