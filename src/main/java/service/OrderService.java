package service;

import domain.Product;
import domain.PromotionType;
import domain.PurchaseProduct;
import domain.Receipt;
import java.util.List;
import repository.ProductRepository;
import view.InputView;

public class OrderService {
    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Receipt order(List<RequestedProduct> requestedProducts) {
        Receipt receipt = new Receipt();

        for (RequestedProduct requestedProduct : requestedProducts) {
            String name = requestedProduct.getProductName();
            int quantity = requestedProduct.getQuantity();
            int price = requestedProduct.getPrice();

            Product stockProduct = productRepository.findProductByName(name);
            int promotionQuantity = stockProduct.getPromotionQuantity();

            PurchaseProduct totalProduct = new PurchaseProduct(name, quantity, price);
            PurchaseProduct promoProduct = null;
            //프로모션중일때
            if (stockProduct.hasPromo()) {
                PromotionType promotionType = stockProduct.getPromotionType();
                int divisor = promotionType.getDivisor();
                //1개 무료로 받을 수 있을 때>메서드로 빼기
                if (quantity % divisor == divisor - 1 && promotionQuantity >= quantity + 1) {
                    if (InputView.confirmFreeItemAddition(name).equals("Y")) {
                        totalProduct.addGiveawayQuantity();
                        quantity += 1;
                    }
                }
                //프로모션 재고 충분하면
                if (promotionQuantity >= quantity) {
                    promoProduct = new PurchaseProduct(name, quantity / divisor, price);
                    stockProduct.reducePromotionQuantity(quantity);
                } else {//프로모션 재고 부족하면
                    int usedTotalPromoProduct = (promotionQuantity / divisor) * divisor;
                    stockProduct.reducePromotionQuantity(usedTotalPromoProduct);
                    if (usedTotalPromoProduct != 0) {
                        promoProduct = new PurchaseProduct(name, (promotionQuantity / divisor), price);
                    }
                    int restPromoQuantity = promotionQuantity - usedTotalPromoProduct;
                    stockProduct.reducePromotionQuantity(restPromoQuantity);
                    stockProduct.addGeneralQuantity(restPromoQuantity);

                    int insufficientQuantity = quantity - usedTotalPromoProduct;
                    //프로모션안하면 안살래요
                    if (InputView.confirmPurchaseWithoutPromotion(name, insufficientQuantity)
                            .equals("N")) {
                        totalProduct.subtractQuantity(insufficientQuantity);
                    } else {
                        stockProduct.reduceGeneralQuantity(insufficientQuantity);
                    }
                }
                if (promoProduct != null) {
                    receipt.applyPromoItem(promoProduct, divisor);
                }
                receipt.applyGeneralItem(totalProduct);
            } else {
                receipt.applyGeneralItem(totalProduct);
                stockProduct.reduceGeneralQuantity(quantity);
            }
        }
        if (InputView.confirmUseMembership().equals("Y")) {
            receipt.useMembership();
        }
        return receipt;
    }
}