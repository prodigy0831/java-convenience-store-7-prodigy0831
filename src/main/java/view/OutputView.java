package view;

import domain.Product;
import domain.PromotionType;
import java.text.NumberFormat;
import java.util.Objects;
import repository.ProductRepository;

public class OutputView {
    public static void printProducts(ProductRepository productRepository) {
        greetingMessage();
        NumberFormat numberFormat = NumberFormat.getInstance();

        for (Product product : productRepository.getProducts()) {
            String formattedPrice = numberFormat.format(product.getPrice());
            boolean isPromotionProduct = product.getPromotionType() != PromotionType.NONE;

            if (isPromotionProduct) {
                if (product.getPromotionQuantity() > 0) {
                    String formattedPromoQuantity = numberFormat.format(product.getPromotionQuantity());
                    System.out.printf("- %s %s원 %s개 %s%n", product.getName(), formattedPrice, formattedPromoQuantity,
                            product.getPromotion());
                } else {
                    System.out.printf("- %s %s원 재고 없음 %s%n", product.getName(), formattedPrice, product.getPromotion());
                }
            }
            if (product.getGeneralQuantity() > 0) {
                String formattedGeneralQuantity = numberFormat.format(product.getGeneralQuantity());
                System.out.printf("- %s %s원 %s개%n", product.getName(), formattedPrice, formattedGeneralQuantity);
            } else {
                System.out.printf("- %s %s원 재고 없음%n", product.getName(), formattedPrice);
            }

        }

    }

    private static void greetingMessage() {
        System.out.printf("안녕하세요. W편의점입니다.%n현재 보유하고 있는 상품입니다.%n%n");
    }
}

