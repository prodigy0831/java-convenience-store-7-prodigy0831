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
        //outputview에는 repository를 넘기면 안된다. products의 배열을 넘겨야 한다.
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
