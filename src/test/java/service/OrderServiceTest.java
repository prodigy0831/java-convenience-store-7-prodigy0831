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
    @DisplayName("프로모션 재고 소진 후 일반 재고 소진시 프로모션 적용 여부를 묻지 않아야 한다.")
    void lackOfPromoStockTest() {
        assertSimpleTest(() -> {
            run("[콜라-11]", "Y", "Y","Y","[콜라-3]","Y","Y","[물-1]","N","N","N");
            assertThat(output().replaceAll("\\s", "")).contains("콜라1,000원6개");
        });
    }
    @Test
    @DisplayName("2+1 프로모션 재고 부족시 프로모션을 적용하고 남은 프로모션 재고를 일반 재고로 넘겨야 한다.")
    void lackOfPromoStockTest2() {
        assertSimpleTest(() -> {
            run("[콜라-9]", "Y", "Y","[콜라-1]","Y","N");
            assertThat(output().replaceAll("\\s", "")).contains("내실돈700");
        });
    }
    @Test
    @DisplayName("1+1 프로모션 재고 부족시 프로모션을 적용하고 남은 프로모션 재고를 일반 재고로 넘겨야 한다.")
    void lackOfPromoStockTest3() {
        assertSimpleTest(() -> {
            run("[초코바-8]", "Y", "Y","Y","[초코바-2]","Y","N");
            assertThat(output().replaceAll("\\s", "")).contains("초코바1,200원2개");
        });
    }

    @Test
    @DisplayName("멤버십 할인의 최대 한도는 8000원이어야 한다.")
    void useMembershipPointTest() {
        assertSimpleTest(() -> {
            run("[정식도시락-5]","Y","N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-8,000");
        });
    }

    @Test
    @DisplayName("다회차 결제에도 멤버십 할인의 최대 한도는 8000원이어야 한다.")
    void useMembershipPointTest2() {
        assertSimpleTest(() -> {
            run("[정식도시락-4]","Y","Y","[정식도시락-4]","Y","N");
            assertThat(output().replaceAll("\\s", "")).contains("멤버십할인-320");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
