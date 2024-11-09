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

    public Receipt order(List<RequestedProduct> requestedProductList) {
        Receipt receipt = new Receipt();

        for (RequestedProduct requestedProduct : requestedProductList) {
            String requestedProductName = requestedProduct.getProductName();
            int requestedProductQuantity = requestedProduct.getQuantity();
            int requestedProductPrice = requestedProduct.getPrice();

            Product stockProduct = productRepository.findProductByName(requestedProductName);
            int promotionProductQuantity = stockProduct.getPromotionQuantity();

            PurchaseProduct purchaseProduct = new PurchaseProduct(requestedProductName, requestedProductQuantity,
                    requestedProductPrice);
            //프로모션중일때
            if (stockProduct.hasPromo()) {
                PromotionType promotionType = stockProduct.getPromotionType();
                int divisor = promotionType.getDivisor();
                //1개 무료로 받을 수 있을 때
                if (requestedProductQuantity % divisor == divisor - 1
                        && promotionProductQuantity >= requestedProductQuantity + 1) {
                    if (InputView.confirmFreeItemAddition(requestedProductName).equals("Y")) {
                        purchaseProduct.addGiveawayQuantity();
                        requestedProductQuantity += 1;
                    }
                }
                //프로모션 재고 충분하면
                if (promotionProductQuantity >= requestedProductQuantity) {
                    receipt.applyPromoItem(requestedProductName, requestedProductQuantity / divisor,
                            requestedProductPrice);
                    stockProduct.reducePromotionQuantity(requestedProductQuantity);
                } else {//프로모션 재고 없으면
                    receipt.applyPromoItem(requestedProductName, requestedProductQuantity / divisor,
                            requestedProductPrice);
                    stockProduct.reducePromotionQuantity(requestedProductQuantity);
                    int restPromotionProductQuantity = stockProduct.getPromotionQuantity();
                    stockProduct.reducePromotionQuantity(restPromotionProductQuantity);
                    stockProduct.addGeneralQuantity(restPromotionProductQuantity);

                    int insufficientQuantity = requestedProductQuantity - promotionProductQuantity;
                    //프로모션안하면 안살래요
                    if (InputView.confirmPurchaseWithoutPromotion(requestedProductName, insufficientQuantity)
                            .equals("N")) {
                        purchaseProduct.subtractQuantity(insufficientQuantity);
                    }
                }
            }

            receipt.applyGeneralItem(purchaseProduct);

        }
        return receipt;
    }

}

