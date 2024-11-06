package view;

import domain.Product;
import repository.ProductRepository;

public class OutputView {
    public static void printProducts(ProductRepository productRepository) {
        for(Product product: productRepository.getProducts()){
            System.out.println("- "+product.getName()+" "+product.getPrice()+"원 "+product.getQuantity()+"개 "+product.getPromotion());
        }
    }
}
