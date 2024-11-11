package domain;

public enum PromotionType {
    NONE(1,0),
    BUY_ONE_GET_ONE(1,1),
    BUY_TWO_GET_ONE(2,1);

    private int buyCount;
    private int giveawayCount;

    PromotionType(int buyCount,int giveawayCount){
        this.buyCount = buyCount;
        this.giveawayCount = giveawayCount;
    }

    public int getDivisor(){
        return this.buyCount + this.giveawayCount;
    }
}


//package domain;
//
//public enum PromotionType {
//    NONE,
//    BUY_ONE_GET_ONE,
//    BUY_TWO_GET_ONE;
//
//    public int getDivisor() {
//        if (this == BUY_ONE_GET_ONE) {
//            return 2;
//        }
//        if (this == BUY_TWO_GET_ONE) {
//            return 3;
//        }
//        return 1;
//    }
//}
