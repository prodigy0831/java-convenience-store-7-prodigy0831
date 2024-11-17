package contoroller;

import domain.Product;
import java.util.List;
import java.util.Map;
import repository.ProductRepository;
import service.InputHandler;
import service.RequestedProduct;
import view.InputView;
import view.OutputView;

public class StoreController {
    private final ProductRepository productRepository;
    private final InputHandler inputHandler;

    public StoreController(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.inputHandler = new InputHandler(productRepository);
    }

    public void showStock() {
        List<Product> products = productRepository.getProducts();
        OutputView.printProducts(products);
    }

    public List<RequestedProduct> getValidRequestedProduct() {
        while (true) {
            String inputItem = InputView.readItem();
            try {
                return inputHandler.processInput(inputItem);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
