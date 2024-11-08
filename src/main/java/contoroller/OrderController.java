package contoroller;

import domain.Product;
import java.util.List;
import java.util.Map;
import repository.ProductRepository;

public class OrderController {
    private final OrderService orderService;

    public void processOrder(Map<String,Integer> requiredItems,ProductRepository productRepository){
        for(Map.Entry<String,Integer> entry : requiredItems.entrySet()){
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            List<Product> matchedProduct = productRepository.findProductByName(itemName);
            if(matchedProduct.size()==1){

            }
        }


    }
}
