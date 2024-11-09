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

//    public static void printReceipt(List<RequestedProduct> purchasedItems) {
//        NumberFormat currencyFormat = NumberFormat.getInstance();
//        System.out.println("=========== W 편의점 ===========");
//        System.out.println("상품명       수량     금액");
//        for (RequestedProduct item : purchasedItems) {
//            int quantity = item.getQuantity();
//            int totalPrice = item.getPrice() * quantity;
//            System.out.printf("%-10s %4d개 %10s원%n", item.getProductName(), quantity, currencyFormat.format(totalPrice));
//        }
//
//    }
public static void printReceipt(List<RequestedProduct> purchasedItems) {
    NumberFormat currencyFormat = NumberFormat.getInstance();
    System.out.println("=========== W 편의점 ===========");
    System.out.println("상품명       수량     금액");

    for (RequestedProduct item : purchasedItems) {
        int quantity = item.getQuantity();
        int totalPrice = item.getPrice() * quantity;

        // 포맷을 맞추기 위해 너비 조정
        System.out.printf("%-10s %3d개 %10s원%n", item.getProductName(), quantity, currencyFormat.format(totalPrice));
    }

    System.out.println("===============================");
    int totalAmount = purchasedItems.stream().mapToInt(item -> item.getPrice() * item.getQuantity()).sum();
    System.out.printf("총 구매 금액: %s원%n", currencyFormat.format(totalAmount));
    System.out.println("===============================");
}
}

