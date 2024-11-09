package domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import service.RequestedProduct;
import view.OutputView;

public class Receipt {
    private final List<RequestedProduct> purchasedItems;
    private final Map<String, Integer> promoItems;
    private int totalAmount;
    private int promotionDiscount;
    private int membershipDiscount;
    private int finalAmount;

    public Receipt(List<RequestedProduct> requestedProductList) {
        this.purchasedItems = new ArrayList<>();
        this.promoItems = new LinkedHashMap<>();
        this.totalAmount = 0;
        this.promotionDiscount = 0;
        this.finalAmount = 0;
        this.membershipDiscount = 0;

        for (RequestedProduct requestedProduct : requestedProductList) {
            purchasedItems.add(requestedProduct);
            totalAmount += (requestedProduct.getPrice() * requestedProduct.getQuantity());
        }
    }

    public void addPromoItem(String productName, int quantity, Product stockProduct) {
        PromotionType promotionType = stockProduct.getPromotionType();
        int price = stockProduct.getPrice();
        int discountQuantity = promotionType.calculateDiscountQuantity(quantity);
        promoItems.put(productName, discountQuantity);
        promotionDiscount += price * quantity;
    }

    public void print() {
        OutputView.printReceipt(purchasedItems);
        if (!promoItems.isEmpty()) {
            OutputView.printPromotionReceipt(promoItems);
        }

    }
}
