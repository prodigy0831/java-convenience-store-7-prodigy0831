package domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import service.RequestedProduct;
import view.OutputView;

public class Receipt {
    private final List<PurchaseProduct> purchasedItems;
    private final List<PurchaseProduct> promoItems;
    private int totalAmount;
    private int promotionDiscount;
    private int membershipDiscount;
    private int finalAmount;

    public Receipt() {
        this.purchasedItems = new ArrayList<>();
        this.promoItems = new ArrayList<>();
        this.totalAmount = 0;
        this.promotionDiscount = 0;
        this.finalAmount = 0;
        this.membershipDiscount = 0;

    }
    public void applyPromoItem(String productName, int quantity, int price) {
        if(quantity>0){
            PurchaseProduct purchaseProduct = new PurchaseProduct(productName, quantity, price);
            promoItems.add(purchaseProduct);
            promotionDiscount += price * quantity;
        }
    }
    public void applyGeneralItem(PurchaseProduct purchaseProduct){
        purchasedItems.add(purchaseProduct);
        totalAmount+=purchaseProduct.getPrice()*purchaseProduct.getQuantity();
    }

    public void print() {
        OutputView.printReceipt(purchasedItems);
        if (!promoItems.isEmpty()) {
            OutputView.printPromotionReceipt(promoItems);
        }

    }
}
