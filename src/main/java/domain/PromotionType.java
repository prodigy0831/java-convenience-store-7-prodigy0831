package domain;

public enum PromotionType {
    NONE,
    BUY_ONE_GET_ONE,
    BUY_TWO_GET_ONE;

    public int calculateGiveawayQuantity(int quantity) {
        int divisor = getDivisor();
        if(divisor!=0){
            return quantity/divisor;
        }
        return quantity;
    }
    public int getDivisor() {
        if (this == BUY_ONE_GET_ONE) {
            return 2;
        }
        if (this == BUY_TWO_GET_ONE) {
            return 3;
        }
        return 1;
    }

}
