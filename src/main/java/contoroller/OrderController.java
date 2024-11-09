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

    public void Order(List<RequestedProduct> requestedProductList) {
        Receipt receipt = orderService.order(requestedProductList);
        receipt.print();
    }


}

