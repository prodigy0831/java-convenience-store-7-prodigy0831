package domain;

import java.util.List;
import repository.PromotionRepository;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;
    private PromotionType promotionType;

    private static final List<Promotion> promotions = PromotionRepository.getPromotions();


    //만들어지는 시점에 validate를 체크한다
    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
        this.promotionType = parsePromotionType(promotion);
    }

    private PromotionType parsePromotionType(String promotion) {
        for (Promotion promo : promotions) {
            if (promo.getName().equals(promotion)) {
                return promo.getPromotionType();
            }
        }
        return PromotionType.NONE;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getPromotion() {
        return promotion;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }
}
