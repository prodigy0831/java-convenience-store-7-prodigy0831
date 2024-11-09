package domain;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import camp.nextstep.edu.missionutils.test.NsTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;
import service.RequestedProduct;

public class RecieptTest extends NsTest {
    private List<RequestedProduct> requestedProductList;
    private Receipt receipt;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        requestedProductList = new ArrayList<>();
        productRepository = new ProductRepository();

        // 예시 제품들로 RequestedProduct 객체 생성
        requestedProductList.add(new RequestedProduct("콜라", 3, productRepository));
        requestedProductList.add(new RequestedProduct("에너지바", 5, productRepository));

        // Receipt 객체 초기화
        receipt = new Receipt(requestedProductList);
    }
    @Test
    void 영수증_출력_테스트() {
        assertSimpleTest(() -> {
            List<RequestedProduct> requestedProductList = new ArrayList<>();
            requestedProductList.add(new RequestedProduct("콜라", 3, productRepository));
            requestedProductList.add(new RequestedProduct("에너지바", 5, productRepository));

            Receipt receipt = new Receipt(requestedProductList);
            receipt.print();

            String expectedOutput = "=========== W 편의점 ===========\n" +
                    "상품명       수량     금액\n" +
                    "콜라        3개      3,000원\n" +
                    "에너지바     5개     10,000원\n" +
                    "===============================\n" +
                    "총 구매 금액: 13,000원\n" +
                    "===============================\n";

            assertThat(output().replaceAll("\\s", "")).isEqualTo(expectedOutput.replaceAll("\\s", ""));
        });
    }

    @Override
    protected void runMain() {

    }
}
