package contoroller;

import java.util.Map;
import repository.ProductRepository;
import repository.PromotionRepository;
import service.InputHandler;
import view.InputView;
import view.OutputView;

public class StoreController {
    private final PromotionRepository promotionRepository;
    private final ProductRepository productRepository;
    private final InputHandler inputHandler;

    public StoreController() {
        this.promotionRepository = new PromotionRepository();
        this.productRepository = new ProductRepository();
        this.inputHandler = new InputHandler(productRepository);
    }

    public void run() {
        OutputView.printProducts(productRepository);

        Map<String, Integer> requiredProductMap = getValidProductMap();

    }

    private Map<String, Integer> getValidProductMap() {
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
