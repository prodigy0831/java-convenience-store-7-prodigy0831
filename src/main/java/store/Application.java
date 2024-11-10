package store;

import contoroller.OrderController;
import contoroller.StoreController;
import domain.Membership;
import java.time.Clock;
import java.util.List;
import repository.ProductRepository;
import repository.PromotionRepository;
import service.RequestedProduct;
import view.InputView;

public class Application {
    public static void main(String[] args) {

        PromotionRepository promotionRepository = new PromotionRepository();
        ProductRepository productRepository = new ProductRepository(promotionRepository);
        StoreController storeController = new StoreController(productRepository);
        OrderController orderController = new OrderController(productRepository);

        Membership.resetPoints();
        //여기서 먼저 멤버십 만들어서 한도액으로 8000원 설정하기.
        //여기부터 while로 묶어서 또 구매한다고 하면.
        while (true) {
            storeController.showStock();
            List<RequestedProduct> requestedProducts = storeController.getValidRequestedProduct();

            orderController.order(requestedProducts);

            String answer = InputView.confirmAdditionalPurchase();
            if (!answer.equalsIgnoreCase("Y")) {
                break;
            }
        }
    }
}
