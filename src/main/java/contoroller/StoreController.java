package contoroller;

import java.util.Map;
import repository.ProductRepository;
import view.OutputView;

public class StoreController {
    public void run(){
        ProductRepository productRepository = new ProductRepository();

        OutputView.printProducts(productRepository);
    }

}
