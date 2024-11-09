package contoroller;

import domain.Product;
import domain.Recipt;
import java.util.List;
import java.util.Map;
import repository.ProductRepository;
import service.OrderService;

public class OrderController {
    private OrderService orderService;

    public void Order(Map<String,Integer> requiredItems,ProductRepository productRepository){
        this.orderService = new OrderService(productRepository);
        Recipt receipt = new Recipt();

        }


    }

