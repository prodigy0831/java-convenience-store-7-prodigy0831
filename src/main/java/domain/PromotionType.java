package domain;

public enum PromotionType {
    NONE,
    BUY_ONE_GET_ONE,
    BUY_TWO_GET_ONE;

    public int calculateDiscountQuantity(int quantity){
        if(this==BUY_ONE_GET_ONE){
            return quantity /2;
        }
        if (this==BUY_TWO_GET_ONE){
            return quantity/3;
        }
        return 0;
    }

}
