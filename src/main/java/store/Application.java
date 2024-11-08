package store;

import contoroller.OrderController;
import contoroller.StoreController;
import java.util.Map;
import repository.ProductRepository;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        OrderController orderController = new OrderController();

        storeController.showStock();
        Map<String,Integer> requiredProductMap = storeController.getValidProductMap();
        ProductRepository productRepository = storeController.getProductRepository();
        orderController.processOrder(requiredProductMap,productRepository);
    }
}
