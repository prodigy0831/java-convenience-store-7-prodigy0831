package service;

import domain.Product;
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
//            if(stockProduct.hasPromo()){
//                if(stockProduct.getPromotionQuantity()>requestedProductQuantity){
//                    receipt.
//                }
//            }

        }
        return receipt;
    }

}

