package domain;

import java.util.ArrayList;
import java.util.List;
import view.OutputView;

public class Receipt {
    private static final int INITIAL_AMOUNT = 0;
    private static final int INITIAL_QUANTITY = 0;

    private final List<PurchaseProduct> purchasedItems;
    private final List<PurchaseProduct> promoItems;
    private int totalAmount;
    private int promotionDiscount;
    private int membershipDiscount;
    private int totalGeneralAmount;
    private int totalQuantity;

    public Receipt() {
        this.purchasedItems = new ArrayList<>();
        this.promoItems = new ArrayList<>();
        this.totalAmount = INITIAL_AMOUNT;
        this.promotionDiscount = INITIAL_AMOUNT;
        this.membershipDiscount = INITIAL_AMOUNT;
        this.totalGeneralAmount = INITIAL_AMOUNT;
        this.totalQuantity = INITIAL_QUANTITY;

    }

    public void applyPromoItem(PurchaseProduct promoProduct, int divisor) {
        promoItems.add(promoProduct);
        int promoAmount = calculateAmount(promoProduct);
        promotionDiscount += promoAmount;
        totalGeneralAmount -= promoAmount * divisor;
    }

    public void applyGeneralItem(PurchaseProduct purchaseProduct) {
        purchasedItems.add(purchaseProduct);
        int purchaseAmount = calculateAmount(purchaseProduct);
        totalAmount += purchaseAmount;
        totalGeneralAmount += purchaseAmount;
        totalQuantity += purchaseProduct.getQuantity();
    }

    public int calculateAmount(PurchaseProduct product) {
        return product.getPrice() * product.getQuantity();
    }


    public void useMembership(Membership membership) {
        membershipDiscount = membership.useMembership(totalGeneralAmount);
    }

    public void print() {
        OutputView.printReceipt(purchasedItems);
        if (!promoItems.isEmpty()) {
            OutputView.printPromotionReceipt(promoItems);
        }
        OutputView.printReceiptSummary(totalAmount, totalQuantity, promotionDiscount, membershipDiscount);
    }
}
