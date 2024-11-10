//package service;
//
//import domain.Product;
//import domain.PromotionType;
//import domain.PurchaseProduct;
//import domain.Receipt;
//import java.util.List;
//import repository.ProductRepository;
//import view.InputView;
//
//public class OrderService {
//    private final ProductRepository productRepository;
//
//    public OrderService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    public Receipt order(List<RequestedProduct> requestedProductList) {
//        Receipt receipt = new Receipt();
//
//        for (RequestedProduct requestedProduct : requestedProductList) {
//            String name = requestedProduct.getProductName();
//            int quantity = requestedProduct.getQuantity();
//            int price = requestedProduct.getPrice();
//
//            Product stockProduct = productRepository.findProductByName(name);
//            int promotionStock = stockProduct.getPromotionQuantity();
//
//            PurchaseProduct purchaseProduct = new PurchaseProduct(name, quantity, price);
//            //프로모션중일때
//            if (stockProduct.hasPromo()) {
//                PromotionType promotionType = stockProduct.getPromotionType();
//                int divisor = promotionType.getDivisor();
//                //1개 무료로 받을 수 있을 때
//                if (quantity % divisor == divisor - 1 && promotionStock >= quantity + 1) {
//                    if (InputView.confirmFreeItemAddition(name).equals("Y")) {
//                        purchaseProduct.addGiveawayQuantity();
//                        quantity += 1;
//                    }
//                }
//                //프로모션 재고 충분하면
//                if (promotionStock >= quantity) {
//                    receipt.applyPromoItem(name, quantity / divisor, price);
//                } else {//프로모션 재고 없으면
//                    receipt.applyPromoItem(name, promotionStock / divisor, price);
//                    stockProduct.reducePromotionQuantity((promotionStock / divisor) * divisor);
//                    int restPromotionProductQuantity = stockProduct.getPromotionQuantity();
//                    stockProduct.reducePromotionQuantity(restPromotionProductQuantity);
//                    stockProduct.addGeneralQuantity(restPromotionProductQuantity);
//
//                    int insufficientQuantity = quantity - promotionStock;
//                    //프로모션안하면 안살래요
//                    if (InputView.confirmPurchaseWithoutPromotion(name, insufficientQuantity)
//                            .equals("N")) {
//                        purchaseProduct.subtractQuantity(insufficientQuantity);
//                    }
//                }
//            }
//
//            receipt.applyGeneralItem(purchaseProduct);
//
//        }
//
//        if (InputView.confirmUseMembership().equals("Y")){
//            receipt.useMembership();
//        }
//        return receipt;
//    }
//
//}
//

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
            String name = requestedProduct.getProductName();
            int quantity = requestedProduct.getQuantity();
            int price = requestedProduct.getPrice();

            Product stockProduct = productRepository.findProductByName(name);
            int promotionStock = stockProduct.getPromotionQuantity();

            PurchaseProduct totalProduct = new PurchaseProduct(name, quantity, price);
            PurchaseProduct promoProduct = null;
            //프로모션중일때
            if (stockProduct.hasPromo()) {
                PromotionType promotionType = stockProduct.getPromotionType();
                int divisor = promotionType.getDivisor();
                //1개 무료로 받을 수 있을 때
                if (quantity % divisor == divisor - 1 && promotionStock >= quantity + 1) {
                    if (InputView.confirmFreeItemAddition(name).equals("Y")) {
                        totalProduct.addGiveawayQuantity();
                        quantity += 1;
                    }
                }
                //프로모션 재고 충분하면
                if (promotionStock >= quantity) {
                    promoProduct = new PurchaseProduct(name, quantity / divisor, price);
                    stockProduct.reducePromotionQuantity(quantity);
                } else {//프로모션 재고 부족하면
                    int usedTotalPromoProduct = (promotionStock / divisor) * divisor;
                    stockProduct.reducePromotionQuantity(usedTotalPromoProduct);
                    if (usedTotalPromoProduct != 0) {
                        promoProduct = new PurchaseProduct(name, (promotionStock / divisor), price);
                    }
                    int restPromotionProductQuantity = promotionStock - usedTotalPromoProduct;
                    stockProduct.reducePromotionQuantity(restPromotionProductQuantity);
                    stockProduct.addGeneralQuantity(restPromotionProductQuantity);

                    int insufficientQuantity = quantity - promotionStock;
                    //프로모션안하면 안살래요
                    if (InputView.confirmPurchaseWithoutPromotion(name, insufficientQuantity)
                            .equals("N")) {
                        totalProduct.subtractQuantity(insufficientQuantity);
                    }
                }
                if (promoProduct != null) {
                    receipt.applyPromoItem(promoProduct, divisor);
                }
            }
            receipt.applyGeneralItem(totalProduct);
        }
        if (InputView.confirmUseMembership().equals("Y")) {
            receipt.useMembership();
        }
        return receipt;
    }

}


