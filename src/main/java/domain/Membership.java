package domain;

import static java.lang.Math.max;

public class Membership {
    private static final int MAX_MEMBERSHIP_DISCOUNT_AMOUNT = 8000;
    private static int point = MAX_MEMBERSHIP_DISCOUNT_AMOUNT;

    private Membership(){

    }
    public static int useMembership(int price){
        int discountAmount = (int)(price*0.3);
        if(point<discountAmount){
            discountAmount = point;
        }
        point-=discountAmount;
        return discountAmount;

    }
}
