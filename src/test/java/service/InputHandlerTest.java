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
    static void setUp() {
        productRepository = new ProductRepository();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자 입력이 비어있거나 null 또는 공백일 때")
    void EmptyOrNullInputTest(String input) {
        assertThatThrownBy(() -> {
            InputHandler.processInput(input, productRepository);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[]", "[],[콜라-1]", "콜라1,물", "[콜라-1],[물3]"})
    @DisplayName("사용자 입력이 잘못된 포맷일 때")
    void InvalidFormatInputTest(String input) {
        assertThatThrownBy(() -> {
            InputHandler.processInput(input, productRepository);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[왕꿈틀이-1]"})
    @DisplayName("사용자 입력이 잘못된 포맷일 때")
    void NotFoundProductNameTest(String input) {
        assertThatThrownBy(() -> {
            InputHandler.processInput(input, productRepository);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }
}
