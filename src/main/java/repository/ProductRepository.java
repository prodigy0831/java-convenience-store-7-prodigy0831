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

public class ProductRepository {
    public static final String PRODUCTS_FILE_PATH = "src/main/resources/products.md";
    public static final String FILE_LOADING_ERROR_MESSAGE = "[ERROR] 파일 로딩 중 오류 발생";
    public static final String DELIMITER = ",";
    public static final int NAME_INDEX = 0;
    public static final int PRICE_INDEX = 1;
    public static final int QUANTITY_INDEX = 2;
    public static final int PROMOTION_INDEX = 3;

    private final List<Product> products = new ArrayList<>();
    private final PromotionRepository promotionRepository;

    public ProductRepository(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
        loadProducts();
    }

    private void loadProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            skipHeader(reader);
            processProductLines(reader);

        } catch (IOException e) {
            System.out.println(FILE_LOADING_ERROR_MESSAGE);
        }
    }

    private void processProductLines(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                processProductLine(line);
            }
        }
    }

    private void processProductLine(String line) {
        String[] parts = line.split(DELIMITER);
        String name = parts[NAME_INDEX].trim();
        int price = Integer.parseInt(parts[PRICE_INDEX].trim());
        int quantity = Integer.parseInt(parts[QUANTITY_INDEX].trim());
        String promotion = parts[PROMOTION_INDEX].trim();

        PromotionType promotionType = getPromotionType(promotion);
        boolean isPromo = (promotionType != PromotionType.NONE);

        addOrUpdateProduct(name, price, quantity, promotion, promotionType, isPromo);
    }

    private void addOrUpdateProduct(String name, int price, int quantity, String promotion,
                                    PromotionType promotionType, boolean isPromo) {
        Optional<Product> existingProduct = findExistingProduct(name);

        existingProduct.ifPresent(product -> updateProductQuantity(product, quantity, isPromo));
        addNewProduct(name, price, quantity, promotion, promotionType, isPromo);
    }

    private void updateProductQuantity(Product product, int quantity, boolean isPromo) {
        if (isPromo) {
            product.addPromotionQuantity(quantity);
            return;
        }
        product.addGeneralQuantity(quantity);
    }

    private void addNewProduct(String name, int price, int quantity, String promotion, PromotionType promotionType,
                               boolean isPromo) {
        Product newProduct = new Product(name, price, quantity, promotion, promotionType, isPromo);
        products.add(newProduct);
    }

    private Optional<Product> findExistingProduct(String name) {
        return products.stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .findFirst();
    }

    private PromotionType getPromotionType(String promotion) {
        return promotionRepository.getPromotionTypeByName(promotion)
                .orElse(PromotionType.NONE);
    }

    private void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    public List<Product> getProducts() {
        return products;
    }

    public Optional<Product> findProductByName(String productName) {
        return products.stream()
                .filter(product -> Objects.equals(product.getName(), productName))
                .findFirst();
    }
}
