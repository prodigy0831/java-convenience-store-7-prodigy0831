package domain;

import java.util.HashMap;
import java.util.Map;

public class Recipt {
    private final Map<String, Integer> purchasedItems;
    private final Map<String, Integer> promoItems;
    private int totalAmount;
    private int promotionDiscount;
    private int membershipDiscount;
    private int finalAmount;

    public Recipt() {
        this.purchasedItems = new HashMap<>();
        this.promoItems = new HashMap<>();
        this.totalAmount = 0;
        this.promotionDiscount = 0;
        this.finalAmount = 0;
        this.membershipDiscount = 0;
    }

    public void addPurchasedItem(String productName, int quantity, int price) {
        purchasedItems.put(productName, quantity);
        totalAmount += price * quantity;
    }

    public void addPromoItem(String productName, int quantity, int price) {
        promoItems.put(productName, quantity);
        promotionDiscount += price * quantity;
    }

}
