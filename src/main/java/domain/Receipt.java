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
    private int totalGeneralAmount;
    private int totalQuantity;

    public Receipt() {
        this.purchasedItems = new ArrayList<>();
        this.promoItems = new ArrayList<>();
        this.totalAmount = 0;
        this.promotionDiscount = 0;
        this.finalAmount = 0;
        this.membershipDiscount = 0;
        this.totalGeneralAmount = 0;
        this.totalQuantity = 0;

    }

    public void applyPromoItem(PurchaseProduct promoProduct,int divisor) {
        promoItems.add(promoProduct);
        promotionDiscount+=promoProduct.getPrice()*promoProduct.getQuantity();
        totalGeneralAmount-=promotionDiscount*divisor;
    }

    public void applyGeneralItem(PurchaseProduct purchaseProduct) {
        purchasedItems.add(purchaseProduct);
        int purchaseAmount = purchaseProduct.getPrice() * purchaseProduct.getQuantity();
        totalAmount += purchaseAmount;
        totalGeneralAmount+=purchaseAmount;
        totalQuantity+=purchaseProduct.getQuantity();
    }


    public void useMembership(Membership membership){
        membershipDiscount = membership.useMembership(totalGeneralAmount);
    }

    public void print() {
        OutputView.printReceipt(purchasedItems);
        if (!promoItems.isEmpty()) {
            OutputView.printPromotionReceipt(promoItems);
        }
        OutputView.printReceiptSummary(totalAmount,totalQuantity,promotionDiscount,membershipDiscount);

    }
}
