package contoroller;

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
        //outputview에는 repository를 넘기면 안된다. products의 배열을 넘겨야 한다.
        OutputView.printProducts(productRepository);
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
    public ProductRepository getProductRepository(){
        return productRepository;
    }
}
