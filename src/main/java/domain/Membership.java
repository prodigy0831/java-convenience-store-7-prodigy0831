package domain;

public class Membership {
    private static final int MAX_MEMBERSHIP_DISCOUNT_AMOUNT = 8000;
    private int point;

    public Membership() {
        this.point = MAX_MEMBERSHIP_DISCOUNT_AMOUNT;
    }

    public int useMembership(int price) {
        int discountAmount = (int) (price * 0.3);
        if (point < discountAmount) {
            discountAmount = point;
        }
        point -= discountAmount;
        return discountAmount;
    }

}
