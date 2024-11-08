package repository;

import domain.Product;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductRepository {
    private final List<Product> products = new ArrayList<>();

    public ProductRepository() {
        loadProducts();
    }

    private void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/products.md"))) {
            reader.readLine(); // 헤더 한 줄 건너뛰기
            String line;
            while ((line = reader.readLine()) != null) {
                addProductFromLine(line);
            }
        } catch (IOException e) {
            System.out.println("[ERROR] 파일 로딩 중 오류 발생");
        }
    }

    private void addProductFromLine(String line) {
        if (line.trim().isEmpty()) {
            return;
        }

        String[] data = line.split(",");
        String name = data[0];
        int price = Integer.parseInt(data[1]);
        int quantity = Integer.parseInt(data[2]);
        String promotion = data[3];

        if (!promotion.equals("null")) {
            products.add(new Product(name, price, quantity, promotion));
            addOrUpdateNullPromotionProduct(name, price, 0);
        } else {
            addOrUpdateNullPromotionProduct(name, price, quantity);
        }
    }

    private void addOrUpdateNullPromotionProduct(String name, int price, int quantity) {
        Product existingProduct = findProductWithNullPromotion(name);
        if (existingProduct != null) {
            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
            return;
        }
        products.add(new Product(name, price, quantity, "null"));
    }

    private Product findProductWithNullPromotion(String name) {
        for (Product product : products) {
            if (product.getName().equals(name) && product.getPromotion().equals("null")) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }
    public List<Product> findProductByName(String productName) {
        return products.stream()
                .filter(product -> Objects.equals(product.getName(), productName))
                .collect(Collectors.toList());
    }

}

