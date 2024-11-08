package contoroller;

import domain.Product;
import domain.Recipt;
import java.util.List;
import java.util.Map;
import repository.ProductRepository;
import service.OrderService;

public class OrderController {
    private OrderService orderService;

    public void processOrder(Map<String,Integer> requiredItems,ProductRepository productRepository){
        this.orderService = new OrderService(productRepository);
        Recipt receipt = new Recipt();
        requiredItems.forEach((item,quantity)->{
            int price = orderService.getProductPrice(item);
            receipt.addPurchasedItem(item,quantity,price);
        });
        }


    }

