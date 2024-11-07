package contoroller;

import java.util.Map;
import repository.ProductRepository;
import service.InputHandler;
import view.InputView;
import view.OutputView;

public class StoreController {
    public void run() {
        ProductRepository productRepository = new ProductRepository();
        OutputView.printProducts(productRepository);

        Map<String, Integer> requiredProductMap = getValidProductMap(productRepository);

    }

    private Map<String, Integer> getValidProductMap(ProductRepository productRepository) {
        while(true){
            String inputItem = InputView.readItem();
            try{
                return InputHandler.processInput(inputItem, productRepository);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

}
