package repository;

import domain.Product;
import domain.Promotion;
import domain.PromotionType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.Optional;

public class ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final PromotionRepository promotionRepository;

    public ProductRepository(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
        loadProducts();
    }

    private void loadProducts() {
        List<Promotion> promotions = promotionRepository.getPromotions();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/products.md"))) {
            reader.readLine(); // 헤더 한 줄 건너뛰기
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                int price = Integer.parseInt(parts[1].trim());
                int quantity = Integer.parseInt(parts[2].trim());
                String promotion = parts[3].trim();

                PromotionType promotionType = promotionRepository.getPromotionTypeByName(promotion)
                        .orElse(PromotionType.NONE);
                boolean isPromo = (promotionType != PromotionType.NONE);

                Optional<Product> existingProduct = products.stream()
                        .filter(p -> Objects.equals(p.getName(), name))
                        .findFirst();

                if (existingProduct.isPresent()) {
                    Product product = existingProduct.get();
                    if (isPromo) {
                        product.addPromotionQuantity(quantity);
                    } else {
                        product.addGeneralQuantity(quantity);
                    }
                } else {
                    Product newProduct = new Product(name, price, quantity, promotion, promotionType, isPromo);
                    products.add(newProduct);
                }
            }

        } catch (IOException e) {
            System.out.println("[ERROR] 파일 로딩 중 오류 발생");
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product findProductByName(String productName) {
        return products.stream()
                .filter(product -> Objects.equals(product.getName(), productName))
                .findFirst()
                .orElse(null);
    }


}
