package service;

import domain.Membership;
import domain.Product;
import domain.PromotionType;
import domain.PurchaseProduct;
import domain.Receipt;
import java.util.List;
import repository.ProductRepository;
import view.InputView;

public class OrderService {
    public static final String PRODUCT_NOT_FOUND = "[ERROR] 존재하지 않는 상품입니다.";
    public static final String CONFIRM_FREE_ITEM_ADDITION = "Y";
    public static final String CONFIRM_PURCHASE_WITHOUT_PROMOTION = "N";
    public static final String CONFIRM_USE_MEMBERSHIP = "Y";

    private final ProductRepository productRepository;
    private final Membership membership;

    public OrderService(ProductRepository productRepository, Membership membership) {
        this.productRepository = productRepository;
        this.membership = membership;
    }

    public Receipt createOrder(List<RequestedProduct> requestedProducts) {
        Receipt receipt = new Receipt();
        for (RequestedProduct requestedProduct : requestedProducts) {
            processRequestedProduct(requestedProduct, receipt);
        }
        applyMembershipDiscount(receipt);
        return receipt;
    }

    private void applyMembershipDiscount(Receipt receipt) {
        if (InputView.confirmUseMembership().equals(CONFIRM_USE_MEMBERSHIP)) {
            receipt.useMembership(membership);
        }
    }

    private void processRequestedProduct(RequestedProduct requestedProduct, Receipt receipt) {
        Product stockProduct = findStockProduct(requestedProduct.getProductName());
        PurchaseProduct totalProduct = createPurchaseProduct(requestedProduct);

        if (stockProduct.hasPromo()) {
            handlePromotion(stockProduct, totalProduct, requestedProduct.getQuantity(), receipt);
            return;
        }
        applyGeneralItem(stockProduct, totalProduct, receipt);
    }

    private void applyGeneralItem(Product stockProduct, PurchaseProduct totalProduct, Receipt receipt) {
        receipt.applyGeneralItem(totalProduct);
        stockProduct.reduceGeneralQuantity(totalProduct.getQuantity());
    }

    private void handlePromotion(Product stockProduct, PurchaseProduct totalProduct, int quantity, Receipt receipt) {
        int promotionQuantity = stockProduct.getPromotionQuantity();
        PromotionType promotionType = stockProduct.getPromotionType();
        int divisor = promotionType.getDivisor();
        if (isAvailableForFreeItem(quantity, divisor, promotionQuantity)) {
            quantity = addFreeItem(totalProduct, quantity);
        }
        if (promotionQuantity >= quantity) {
            applyPromotion(stockProduct, totalProduct, quantity, receipt, divisor);
            return;
        }
        handleInsufficientPromotionStock(stockProduct, totalProduct, promotionQuantity, quantity, receipt, divisor);
    }

    private void handleInsufficientPromotionStock(Product stockProduct, PurchaseProduct totalProduct,
                                                  int promotionQuantity, int quantity, Receipt receipt, int divisor) {
        int usedTotalPromoProduct = (promotionQuantity / divisor) * divisor;
        stockProduct.reducePromotionQuantity(usedTotalPromoProduct);

        PurchaseProduct promoProduct = createPromoProduct(stockProduct, usedTotalPromoProduct, divisor);
        int restPromoQuantity = promotionQuantity - usedTotalPromoProduct;
        stockProduct.reducePromotionQuantity(restPromoQuantity);
        stockProduct.addGeneralQuantity(restPromoQuantity);

        int insufficientQuantity = quantity - usedTotalPromoProduct;
        adjustForInsufficientQuantity(stockProduct, totalProduct, insufficientQuantity);

        if (promoProduct != null) {
            receipt.applyPromoItem(promoProduct, divisor);
        }
        receipt.applyGeneralItem(totalProduct);
    }

    private void adjustForInsufficientQuantity(Product stockProduct, PurchaseProduct totalProduct,
                                               int insufficientQuantity) {
        if (InputView.confirmPurchaseWithoutPromotion(totalProduct.getName(), insufficientQuantity)
                .equals(CONFIRM_PURCHASE_WITHOUT_PROMOTION)) {
            totalProduct.subtractQuantity(insufficientQuantity);
            return;
        }
        stockProduct.reduceGeneralQuantity(insufficientQuantity);
    }

    private PurchaseProduct createPromoProduct(Product stockProduct, int usedTotalPromoProduct, int divisor) {
        if (usedTotalPromoProduct == 0) {
            return null;
        }
        return new PurchaseProduct(stockProduct.getName(), usedTotalPromoProduct / divisor, stockProduct.getPrice());
    }


    private void applyPromotion(Product stockProduct, PurchaseProduct totalProduct, int quantity, Receipt receipt,
                                int divisor) {
        PurchaseProduct promoProduct = new PurchaseProduct(totalProduct.getName(), quantity / divisor,
                totalProduct.getPrice());
        stockProduct.reducePromotionQuantity(quantity);
        receipt.applyPromoItem(promoProduct, divisor);
        receipt.applyGeneralItem(totalProduct);
    }

    private boolean isAvailableForFreeItem(int quantity, int divisor, int promotionQuantity) {
        return quantity % divisor == divisor - 1 && promotionQuantity >= quantity + 1;
    }

    private int addFreeItem(PurchaseProduct totalProduct, int quantity) {
        if (InputView.confirmFreeItemAddition(totalProduct.getName()).equals(CONFIRM_FREE_ITEM_ADDITION)) {
            totalProduct.addGiveawayQuantity();
            quantity += 1;
        }
        return quantity;
    }

    private PurchaseProduct createPurchaseProduct(RequestedProduct requestedProduct) {
        return new PurchaseProduct(requestedProduct.getProductName(), requestedProduct.getQuantity(),
                requestedProduct.getPrice());
    }

    private Product findStockProduct(String name) {
        return productRepository.findProductByName(name).orElseThrow(() -> new IllegalArgumentException(
                PRODUCT_NOT_FOUND));
    }
}
