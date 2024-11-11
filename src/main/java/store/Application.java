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

        Membership membership = new Membership();
        OrderController orderController = new OrderController(productRepository, membership);

        boolean isPurchasing = true;
        while (isPurchasing) {
            storeController.showStock();
            List<RequestedProduct> requestedProducts = storeController.getValidRequestedProduct();

            orderController.order(requestedProducts);

            String answer = InputView.confirmAdditionalPurchase();
            isPurchasing = answer.equalsIgnoreCase("Y");
        }
    }
}
