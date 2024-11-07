package domain;

import java.time.LocalDate;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public PromotionType getPromotionType() {
        if (buy == 1 && get == 1) {
            return PromotionType.BUY_ONE_GET_ONE;
        }
        if (buy == 2 && get == 1) {
            return PromotionType.BUY_TWO_GET_ONE;
        }
        return null;
    }

}
