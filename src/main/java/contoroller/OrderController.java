package contoroller;


import domain.Receipt;
import java.util.List;
import repository.ProductRepository;
import service.OrderService;
import service.RequestedProduct;

public class OrderController {
    private final OrderService orderService;

    public OrderController(ProductRepository productRepository) {
        this.orderService = new OrderService(productRepository);
    }

    public void order(List<RequestedProduct> requestedProduct) {
        Receipt receipt = orderService.order(requestedProduct);
        receipt.print();
    }


}

