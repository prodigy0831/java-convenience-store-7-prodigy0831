package store;

import repository.ProductRepository;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();
        OutputView.printProducts(productRepository);
    }
}
