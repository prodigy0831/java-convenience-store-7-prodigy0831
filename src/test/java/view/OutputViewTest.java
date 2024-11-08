package view;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;

public class OutputViewTest {
    private static ProductRepository productRepository;
    @BeforeAll
    static void setUp(){
        productRepository = new ProductRepository();

    }
//
//    @Test
//    @DisplayName("출력 확인")
//    void test(){
//        assertThat(productRepository.getProducts().size()).isEqualTo(16);
//    }
}
