package view;

import domain.Product;
import java.text.NumberFormat;
import java.util.Objects;
import repository.ProductRepository;

public class OutputView {
    public static void printProducts(ProductRepository productRepository) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        for (Product product : productRepository.getProducts()) {
            String promotion = "";
            if (!Objects.equals(product.getPromotion(), "null")) {
                promotion = product.getPromotion();
            }

            String formattedPrice = numberFormat.format(product.getPrice());
            String formattedQuantity = numberFormat.format(product.getQuantity());
            System.out.printf("- %s %s원 %s개 %s%n", product.getName(), formattedPrice, formattedQuantity,
                    promotion);
        }
    }
}
