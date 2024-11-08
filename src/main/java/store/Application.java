package store;

import contoroller.OrderController;
import contoroller.StoreController;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        OrderController orderController = new OrderController();

        storeController.showStock();
        Map<String,Integer> requiredProductMap = storeController.getValidProductMap();

        orderController.processOrder(requiredProductMap);
    }
}
