package contoroller;

import java.util.Map;
import repository.ProductRepository;
import service.InputHandler;
import view.InputView;
import view.OutputView;

public class StoreController {
    public void run(){
        ProductRepository productRepository = new ProductRepository();

        OutputView.printProducts(productRepository);
        String inputItem = InputView.readItem();
        Map<String,Integer> requiredProductMap;
        try{
            requiredProductMap = InputHandler.processInput(inputItem,productRepository);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
