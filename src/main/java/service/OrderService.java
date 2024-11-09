package service;

import domain.Product;
import domain.PromotionType;
import domain.Receipt;
import java.util.List;
import repository.ProductRepository;

public class OrderService {
    private final ProductRepository productRepository;

    public OrderService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Receipt order(List<RequestedProduct> requestedProductList) {
        Receipt receipt = new Receipt(requestedProductList);

        for (RequestedProduct requestedProduct : requestedProductList) {
            String requestedProductName = requestedProduct.getProductName();
            int requestedProductQuantity = requestedProduct.getQuantity();

            Product stockProduct = productRepository.findProductByName(requestedProductName);
            //프로모션중일때
            if (stockProduct.hasPromo()) {
                //재고 충분하면
                if (stockProduct.getPromotionQuantity() > requestedProductQuantity) {
                    receipt.addPromoItem(requestedProductName, requestedProductQuantity, stockProduct);
                }
            }

        }
        return receipt;
    }

}

