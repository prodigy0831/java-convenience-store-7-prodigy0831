package view;

import domain.Product;
import domain.PromotionType;
import domain.PurchaseProduct;
import java.text.NumberFormat;
import java.util.List;
import repository.ProductRepository;

public class OutputView {
    private static final NumberFormat currencyFormat = NumberFormat.getInstance();

    public static void printProducts(ProductRepository productRepository) {
        greetingMessage();

        for (Product product : productRepository.getProducts()) {
            String formattedPrice = formatCurrency(product.getPrice());
            boolean isPromotionProduct = product.getPromotionType() != PromotionType.NONE;

            if (isPromotionProduct) {
                printPromotionProduct(product, formattedPrice);
            }
            printGeneralProduct(product, formattedPrice);
        }
    }

    private static void greetingMessage() {
        System.out.print(System.lineSeparator());
        System.out.printf("안녕하세요. W편의점입니다.%n현재 보유하고 있는 상품입니다.%n%n");
    }

    private static void printPromotionProduct(Product product, String formattedPrice) {
        if (product.getPromotionQuantity() > 0) {
            String formattedPromoQuantity = formatCurrency(product.getPromotionQuantity());
            System.out.printf("- %s %s원 %s개 %s%n", product.getName(), formattedPrice, formattedPromoQuantity,
                    product.getPromotionName());
        } else {
            System.out.printf("- %s %s원 재고 없음 %s%n", product.getName(), formattedPrice,
                    product.getPromotionName());
        }
    }

    private static void printGeneralProduct(Product product, String formattedPrice) {
        if (product.getGeneralQuantity() > 0) {
            String formattedGeneralQuantity = formatCurrency(product.getGeneralQuantity());
            System.out.printf("- %s %s원 %s개%n", product.getName(), formattedPrice, formattedGeneralQuantity);
        } else {
            System.out.printf("- %s %s원 재고 없음%n", product.getName(), formattedPrice);
        }
    }

    private static String formatCurrency(int value) {
        return currencyFormat.format(value);
    }

    public static void printReceipt(List<PurchaseProduct> purchasedItems) {
        System.out.println("===========W 편의점=============");
        System.out.printf("%-13s %-7s %-6s%n", "상품명", "수량", "금액");

        for (PurchaseProduct item : purchasedItems) {
            int totalPrice = item.getPrice() * item.getQuantity();
            System.out.printf("%-13s %-7d %-6s%n", item.getName(), item.getQuantity(), formatCurrency(totalPrice));
        }
    }

    public static void printPromotionReceipt(List<PurchaseProduct> promoItems) {
        System.out.println("===========증     정===========");
        for (PurchaseProduct item : promoItems) {
            System.out.printf("%-13s %-6s%n", item.getName(), item.getQuantity());
        }
    }

    public static void printReceiptSummary(int totalAmount, int totalQuantity, int promotionDiscount,
                                           int membershipDiscount) {
        System.out.println("=============================");
        System.out.printf("%-13s %-7d %10s%n", "총구매액", totalQuantity, formatCurrency(totalAmount));
        System.out.printf("%-13s %15s%n", "행사할인", formatCurrency(-promotionDiscount));
        System.out.printf("%-13s %15s%n", "멤버십할인", formatCurrency(-membershipDiscount));
        System.out.printf("%-13s %15s%n", "내실돈", formatCurrency(totalAmount - promotionDiscount - membershipDiscount));
    }
}
