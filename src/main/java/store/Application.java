package store;

import contoroller.OrderController;
import contoroller.StoreController;
import java.util.Map;
import repository.ProductRepository;

public class Application {
    public static void main(String[] args) {
        StoreController storeController = new StoreController();
        OrderController orderController = new OrderController();
        //여기서 먼저 멤버십 만들어서 한도액으로 8000원 설정하기.
        //여기부터 while로 묶어서 또 구매한다고 하면.
        storeController.showStock();
        Map<String, Integer> requiredProductMap = storeController.getValidProductMap();
        ProductRepository productRepository = storeController.getProductRepository();

        orderController.Order(requiredProductMap, productRepository);
    }
}
