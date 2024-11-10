package service;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import domain.Receipt;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import repository.PromotionRepository;


public class OrderServiceTest extends NsTest {

    private ProductRepository productRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        PromotionRepository promotionRepository = new PromotionRepository();
        productRepository = new ProductRepository(promotionRepository);
        orderService = new OrderService(productRepository);

    }

//    @Test
//    @DisplayName("기본 상품 영수증 출력 테스트")
//    void generalItemPrintTest() {
//        assertSimpleTest(() -> {
//            List<RequestedProduct> requestedProductList = new ArrayList<>();
//            requestedProductList.add(new RequestedProduct("물", 3, productRepository));
//            requestedProductList.add(new RequestedProduct("정식도시락", 2, productRepository));
//
//            Receipt receipt = orderService.order(requestedProductList);
//            receipt.print();
//            assertThat(output()).contains(
//                    "===========W 편의점=============",
//                    "상품명           수량      금액",
//                    "물             3       1,500",
//                    "정식도시락         2       12,800"
//            );
//        });
//    }
//
//    @Test
//    @DisplayName("프로모션 상품 영수증 출력 테스트")
//    void promotionItemPrintTest() {
//        assertSimpleTest(() -> {
//            List<RequestedProduct> requestedProductList = new ArrayList<>();
//            requestedProductList.add(new RequestedProduct("물", 3, productRepository));
//            requestedProductList.add(new RequestedProduct("정식도시락", 2, productRepository));
//            requestedProductList.add(new RequestedProduct("콜라", 3, productRepository));
//
//            Receipt receipt = orderService.order(requestedProductList);
//            receipt.print();
//            assertThat(output()).contains(
//                    "===========W 편의점=============",
//                    "상품명           수량      금액",
//                    "물             3       1,500",
//                    "정식도시락         2       12,800",
//                    "===========증     정===========",
//                    "콜라              1"
//            );
//        });
//    }


    @Override
    protected void runMain() {

    }
}
