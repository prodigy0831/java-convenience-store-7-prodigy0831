package domain;

public enum PromotionType {
    NONE(1, 0),
    BUY_ONE_GET_ONE(1, 1),
    BUY_TWO_GET_ONE(2, 1);

    private final int buyCount;
    private final int giveawayCount;

    PromotionType(int buyCount, int giveawayCount) {
        this.buyCount = buyCount;
        this.giveawayCount = giveawayCount;
    }

    public int getDivisor() {
        return this.buyCount + this.giveawayCount;
    }

    public static PromotionType fromBuyAndGet(int buy, int get) {
        for (PromotionType type : PromotionType.values()) {
            if (type.buyCount == buy && type.giveawayCount == get) {
                return type;
            }
        }
        return NONE;
    }
}