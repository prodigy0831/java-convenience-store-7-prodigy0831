package contoroller;

import java.util.Map;
import repository.ProductRepository;
import service.InputHandler;
import view.InputView;
import view.OutputView;

public class StoreController {
    private final ProductRepository productRepository;
    private final InputHandler inputHandler;

    public StoreController() {
        this.productRepository = new ProductRepository();
        this.inputHandler = new InputHandler(productRepository);
    }

    public void showStock() {
        OutputView.printProducts(productRepository);
    }

    public Map<String, Integer> getValidProductMap() {
        while (true) {
            String inputItem = InputView.readItem();
            try {
                return inputHandler.processInput(inputItem);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public ProductRepository getProductRepository(){
        return productRepository;
    }
}
