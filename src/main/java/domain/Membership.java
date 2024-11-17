package domain;

public class Membership {
    private static final int MAX_MEMBERSHIP_DISCOUNT_AMOUNT = 8000;
    private static final double DISCOUNT_RATE = 0.3;

    private int point;

    public Membership() {
        this.point = MAX_MEMBERSHIP_DISCOUNT_AMOUNT;
    }

    public int useMembership(int price) {
        int discountAmount = (int) (price * DISCOUNT_RATE);
        if (point < discountAmount) {
            discountAmount = point;
        }
        point -= discountAmount;
        return discountAmount;
    }
}
