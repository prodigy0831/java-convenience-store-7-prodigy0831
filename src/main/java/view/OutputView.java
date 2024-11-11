package view;

import domain.Product;
import domain.PromotionType;
import domain.PurchaseProduct;
import java.text.NumberFormat;
import java.util.List;
import repository.ProductRepository;

public class OutputView {
    private static final NumberFormat currencyFormat = NumberFormat.getInstance();

    private static final String GREETING_MESSAGE = "안녕하세요. W편의점입니다.%n현재 보유하고 있는 상품입니다.%n%n";
    private static final String RECEIPT_HEADER = "===========W 편의점=============";
    public static final String PROMOTION_HEADER = "===========증   정=============";
    public static final String RECEIPT_DIVIDER = "==============================";
    public static final String TOTAL_AMOUNT_LABEL = "총구매액";
    public static final String PROMOTION_LABEL = "행사할인";
    public static final String MEMBERSHIP_LABEL = "멤버십할인";
    public static final String FINAL_AMOUNT_LABEL = "내실돈";

    public static void printProducts(ProductRepository productRepository) {
        greetingMessage();

        for (Product product : productRepository.getProducts()) {
            printProductByPromotion(product);
        }
    }

    private static void printProductByPromotion(Product product) {
        String formattedPrice = formatCurrency(product.getPrice());
        if (product.getPromotionType() != PromotionType.NONE) {
            printPromotionProduct(product, formattedPrice);
        }
        printGeneralProduct(product, formattedPrice);
    }


    private static void greetingMessage() {
        System.out.print(System.lineSeparator());
        System.out.printf(GREETING_MESSAGE);
    }

    private static void printPromotionProduct(Product product, String formattedPrice) {
        if (product.getPromotionQuantity() > 0) {
            String formattedPromoQuantity = formatCurrency(product.getPromotionQuantity());
            System.out.printf("- %s %s원 %s개 %s%n", product.getName(), formattedPrice, formattedPromoQuantity,
                    product.getPromotionName());
            return;
        }
        System.out.printf("- %s %s원 재고 없음 %s%n", product.getName(), formattedPrice,
                product.getPromotionName());
    }

    private static void printGeneralProduct(Product product, String formattedPrice) {
        if (product.getGeneralQuantity() > 0) {
            String formattedGeneralQuantity = formatCurrency(product.getGeneralQuantity());
            System.out.printf("- %s %s원 %s개%n", product.getName(), formattedPrice, formattedGeneralQuantity);
            return;
        }
        System.out.printf("- %s %s원 재고 없음%n", product.getName(), formattedPrice);
    }

    public static void printReceipt(List<PurchaseProduct> purchasedItems) {
        System.out.println(RECEIPT_HEADER);
        System.out.printf("%-14s %-6s %-6s%n", "상품명", "수량", "금액");

        for (PurchaseProduct item : purchasedItems) {
            int totalPrice = item.getPrice() * item.getQuantity();
            System.out.printf("%-14s %-6d %-6s%n", item.getName(), item.getQuantity(), formatCurrency(totalPrice));
        }
    }

    public static void printPromotionReceipt(List<PurchaseProduct> promoItems) {
        System.out.println(PROMOTION_HEADER);
        for (PurchaseProduct item : promoItems) {
            System.out.printf("%-13s %-6s%n", item.getName(), item.getQuantity());
        }
    }

    public static void printReceiptSummary(int totalAmount, int totalQuantity, int promotionDiscount,
                                           int membershipDiscount) {
        System.out.println(RECEIPT_DIVIDER);
        System.out.printf("%-14s %-6d %-6s%n", TOTAL_AMOUNT_LABEL, totalQuantity, formatCurrency(totalAmount));
        System.out.printf("%-20s %-6s%n", PROMOTION_LABEL, formatCurrencyWithMinus(promotionDiscount));
        System.out.printf("%-20s %-6s%n", MEMBERSHIP_LABEL, formatCurrencyWithMinus(membershipDiscount));
        System.out.printf("%-20s %-6s%n", FINAL_AMOUNT_LABEL,
                formatCurrency(totalAmount - promotionDiscount - membershipDiscount));
    }

    private static String formatCurrency(int value) {
        return currencyFormat.format(value);
    }

    private static String formatCurrencyWithMinus(int amount) {
        return "-" + formatCurrency(amount);
    }
}
