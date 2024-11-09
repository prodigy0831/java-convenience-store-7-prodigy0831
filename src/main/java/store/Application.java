package store;

import contoroller.OrderController;
import contoroller.StoreController;
import java.util.List;
import java.util.Map;
import repository.ProductRepository;
import service.RequestedProduct;

public class Application {
    public static void main(String[] args) {
        ProductRepository productRepository = new ProductRepository();

        StoreController storeController = new StoreController(productRepository);
        OrderController orderController = new OrderController(productRepository);
        //여기서 먼저 멤버십 만들어서 한도액으로 8000원 설정하기.
        //여기부터 while로 묶어서 또 구매한다고 하면.
        storeController.showStock();
        List<RequestedProduct> requestedProductList = storeController.getValidRequestedProduct();

        orderController.Order(requestedProductList);
    }
}
