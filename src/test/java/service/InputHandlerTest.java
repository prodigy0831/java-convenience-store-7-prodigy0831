package service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import repository.ProductRepository;
import repository.PromotionRepository;

public class InputHandlerTest {
    private static InputHandler inputHandler;

    @BeforeAll
    static void setUp() {
        PromotionRepository promotionRepository = new PromotionRepository();
        ProductRepository productRepository = new ProductRepository(promotionRepository);
        inputHandler = new InputHandler(productRepository);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자 입력이 비어있거나 null 또는 공백일 때")
    void EmptyOrNullInputTest(String input) {
        assertThatThrownBy(() -> {
            inputHandler.processInput(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[]", "[],[콜라-1]", "콜라1,물", "[콜라-1],[물3]"})
    @DisplayName("사용자 입력이 잘못된 포맷일 때")
    void InvalidFormatInputTest(String input) {
        assertThatThrownBy(() -> {
            inputHandler.processInput(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[왕꿈틀이-1]", "[콜라-11],[물-1],[왕꿈틀이-1]"})
    @DisplayName("존재하지 않는 상품이 입력으로 들어올 때")
    void NotFoundProductNameTest(String input) {
        assertThatThrownBy(() -> {
            inputHandler.processInput(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"[콜라-21]", "[콜라-10],[콜라-11]", "[콜라-21],[물-1],[왕꿈틀이-1]"})
    @DisplayName("현재 재고보다 많은 수량이 input으로 들어올 때")
    void OutOfStockTest(String input) {
        assertThatThrownBy(() -> {
            inputHandler.processInput(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
    }
}
