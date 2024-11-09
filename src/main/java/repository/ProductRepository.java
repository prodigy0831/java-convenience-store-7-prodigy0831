package repository;

import domain.Product;
import domain.PromotionType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.util.Optional;
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
                String[] parts = line.split(",");
                String name = parts[0].trim();
                int price = Integer.parseInt(parts[1].trim());
                int quantity = Integer.parseInt(parts[2].trim());
                String promotion = parts[3].trim();
                boolean isPromo = !promotion.equals("null");

                Optional<Product> existingProduct = products.stream()
                        .filter(p->Objects.equals(p.getName(),name))
                        .findFirst();

                if(existingProduct.isPresent()){
                    Product product = existingProduct.get();
                    if(isPromo){
                        product.addPromotionQuantity(quantity);
                    }else{
                        product.addGeneralQuantity(quantity);
                    }
                }else{
                    Product newProduct = new Product(name,price,quantity,promotion,isPromo);
                    products.add(newProduct);
                }
            }

        } catch (IOException e) {
            System.out.println("[ERROR] 파일 로딩 중 오류 발생");
        }
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

    public Product findProductByName(String productName) {
        return products.stream()
                .filter(product -> Objects.equals(product.getName(), productName))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("[ERROR] 해당 상품을 찾을 수 없습니다"));
    }

    public Product findProductByNameWithPromo(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName)
                        && product.getPromotionType() != PromotionType.NONE)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("[ERROR] 해당 상품에 프로모션이 적용된 제품을 찾을 수 없습니다: " + productName));
    }

    public Product findProductByNameWithoutPromo(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName)
                        && product.getPromotionType() == PromotionType.NONE)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("[ERROR] 해당 상품에 프로모션이 적용된 제품을 찾을 수 없습니다: " + productName));
    }

    public boolean hasPromo(String productName) {
        return products.stream()
                .filter(product -> product.getName().equals(productName))
                .count() == 2;
    }

}
//
//package repository;
//
//import domain.Product;
//import domain.PromotionType;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import java.util.stream.Collectors;
//
//public class ProductRepository {
//    private final List<Product> products = new ArrayList<>();
//
//    public ProductRepository() {
//        loadProducts();
//    }
//
//    private void loadProducts() {
//        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/products.md"))) {
//            reader.readLine(); // 헤더 한 줄 건너뛰기
//            String line;
//            while ((line = reader.readLine()) != null) {
//                addProductFromLine(line);
//            }
//        } catch (IOException e) {
//            System.out.println("[ERROR] 파일 로딩 중 오류 발생");
//        }
//    }
//
//    private void addProductFromLine(String line) {
//        if (line.trim().isEmpty()) {
//            return;
//        }
//
//        String[] data = line.split(",");
//        String name = data[0];
//        int price = Integer.parseInt(data[1]);
//        int quantity = Integer.parseInt(data[2]);
//        String promotion = data[3];
//
//        if (!promotion.equals("null")) {
//            products.add(new Product(name, price, quantity, promotion));
//            addOrUpdateNullPromotionProduct(name, price, 0);
//        } else {
//            addOrUpdateNullPromotionProduct(name, price, quantity);
//        }
//    }
//
//    private void addOrUpdateNullPromotionProduct(String name, int price, int quantity) {
//        Product existingProduct = findProductWithNullPromotion(name);
//        if (existingProduct != null) {
//            existingProduct.setQuantity(existingProduct.getQuantity() + quantity);
//            return;
//        }
//        products.add(new Product(name, price, quantity, "null"));
//    }
//
//    private Product findProductWithNullPromotion(String name) {
//        for (Product product : products) {
//            if (product.getName().equals(name) && product.getPromotion().equals("null")) {
//                return product;
//            }
//        }
//        return null;
//    }
//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public List<Product> findProductByName(String productName) {
//        return products.stream()
//                .filter(product -> Objects.equals(product.getName(), productName))
//                .collect(Collectors.toList());
//    }
//
//    public Product findProductByNameWithPromo(String productName) {
//        return products.stream()
//                .filter(product -> product.getName().equals(productName)
//                        && product.getPromotionType() != PromotionType.NONE)
//                .findFirst()
//                .orElseThrow(
//                        () -> new IllegalArgumentException("[ERROR] 해당 상품에 프로모션이 적용된 제품을 찾을 수 없습니다: " + productName));
//    }
//
//    public Product findProductByNameWithoutPromo(String productName) {
//        return products.stream()
//                .filter(product -> product.getName().equals(productName)
//                        && product.getPromotionType() == PromotionType.NONE)
//                .findFirst()
//                .orElseThrow(
//                        () -> new IllegalArgumentException("[ERROR] 해당 상품에 프로모션이 적용된 제품을 찾을 수 없습니다: " + productName));
//    }
//
//    public boolean hasPromo(String productName) {
//        return products.stream()
//                .filter(product -> product.getName().equals(productName))
//                .count() == 2;
//    }
//
//}
//
