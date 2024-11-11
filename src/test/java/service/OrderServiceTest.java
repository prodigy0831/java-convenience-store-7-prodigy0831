package service;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.Application;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class  OrderServiceTest extends NsTest {


    @Test
    @DisplayName("1+1 프로모션 재고 부족 테스트")
    void insufficientStockTest1() {
        assertSimpleTest(() -> {
            run("[감자칩-6]", "Y", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("감자칩2");
        });
    }

    @Test
    @DisplayName("2+1프로모션 재고 부족 테스트")
    void insufficientStockTest2() {
        assertSimpleTest(() -> {
            run("[사이다-9]", "N", "N", "N");
            assertThat(output().replaceAll("\\s", "")).contains("사이다6");
        });
    }

    @Test
    @DisplayName("프로모션 재고 부족시 프로모션을 적용하고 남은 프로모션 재고를 일반 재고로 넘겨야 한다.")
    void lackOfPromoStockTest() {
        assertSimpleTest(() -> {
            run("[콜라-11]", "N", "N","Y");
            assertThat(output().replaceAll("\\s", "")).contains("사이다6");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
