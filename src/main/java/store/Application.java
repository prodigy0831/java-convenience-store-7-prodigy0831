package store;

import contoroller.OrderController;
import contoroller.StoreController;
import domain.Membership;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import repository.ProductRepository;
import repository.PromotionRepository;
import service.RequestedProduct;
import view.InputView;

public class Application {
    public static void main(String[] args) {
        System.out.println(LocalDateTime.now());
//        Clock clock = Clock.systemDefaultZone();
//        Clock clock = Clock.fixed(LocalDate.of(2024, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        PromotionRepository promotionRepository = new PromotionRepository();
        ProductRepository productRepository = new ProductRepository(promotionRepository);
        StoreController storeController = new StoreController(productRepository);
        OrderController orderController = new OrderController(productRepository);

        Membership.resetPoints();

        //여기서 먼저 멤버십 만들어서 한도액으로 8000원 설정하기.
        //여기부터 while로 묶어서 또 구매한다고 하면.
        while(true){
            storeController.showStock();
            List<RequestedProduct> requestedProductList = storeController.getValidRequestedProduct();

            orderController.Order(requestedProductList);

            String answer = InputView.confirmAdditionalPurchase();
            if(!answer.equalsIgnoreCase("Y")){
                break;
            }
        }

    }
}
