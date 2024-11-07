package service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import repository.ProductRepository;

public class InputHandlerTest {
    private static ProductRepository productRepository;
    @BeforeAll
    static void setUp(){
        productRepository = new ProductRepository();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자 입력이 비어있거나 null 또는 공백일 때")
    void EmptyOrNullInputTest(String input){
        assertThatThrownBy(()->{
            InputHandler.processInput(input,productRepository);
        } ).isInstanceOf(IllegalArgumentException.class);

    }
}
