package domain;

import java.util.List;
import repository.ProductRepository;
import repository.PromotionRepository;

public class Product {
    private String name;
    private int price;
    private int quantity;
    private String promotion;
    private PromotionType promotionType;

    private static final List<Promotion> promotions = PromotionRepository.getPromotions();

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
        this.promotionType = parsePromotionType(promotion);
    }

    private PromotionType parsePromotionType(String promotion) {
        for(Promotion promo : promotions){
            if (promo.getName().equals(promotion)){
                return promo.getPromotionType();
            }
        }
        return null;
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
}
