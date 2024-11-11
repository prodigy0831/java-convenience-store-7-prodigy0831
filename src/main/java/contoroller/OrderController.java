package contoroller;


import domain.Membership;
import domain.Receipt;
import java.util.List;
import repository.ProductRepository;
import service.OrderService;
import service.RequestedProduct;

public class OrderController {
    private final OrderService orderService;

    public OrderController(ProductRepository productRepository, Membership membership) {
        this.orderService = new OrderService(productRepository,membership);
    }

    public void order(List<RequestedProduct> requestedProduct) {
        Receipt receipt = orderService.order(requestedProduct);
        receipt.print();
    }
}

