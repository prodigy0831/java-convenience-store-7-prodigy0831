package view;

import domain.Product;
import domain.PromotionType;
import domain.Receipt;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import repository.ProductRepository;
import service.RequestedProduct;

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
                            product.getPromotionName());
                } else {
                    System.out.printf("- %s %s원 재고 없음 %s%n", product.getName(), formattedPrice,
                            product.getPromotionName());
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


    public static void printReceipt(List<RequestedProduct> purchasedItems) {
        NumberFormat currencyFormat = NumberFormat.getInstance();
        System.out.println("===========W 편의점=============");
        System.out.printf("%-13s %-7s %-6s%n", "상품명", "수량", "금액");

        for (RequestedProduct item : purchasedItems) {
            int quantity = item.getQuantity();
            int totalPrice = item.getPrice() * quantity;

            System.out.printf("%-13s %-7d %-6s%n", item.getProductName(), quantity, currencyFormat.format(totalPrice));
        }
    }

    public static void printPromotionReceipt(Map<String, Integer> promoItems) {
        System.out.println("===========증     정===========");
        promoItems.forEach((productName, quantity) ->
                System.out.printf("%-12s %4d%n", productName, quantity));
    }

}

