package domain;

import java.util.List;
import repository.PromotionRepository;

public class Product {
    private String name;
    private int price;
    private int promotionQuantity = 0;
    private int generalQuantity = 0;
    private String promotion;
    private PromotionType promotionType;

    private static final List<Promotion> promotions = PromotionRepository.getPromotions();

    //만들어지는 시점에 validate를 체크한다
    public Product(String name, int price, int quantity, String promotion, boolean isPromo) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.promotionType = parsePromotionType(promotion);

        if (isPromo) {
            this.promotionQuantity = quantity;
        } else {
            this.generalQuantity = quantity;
        }
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
    public int getPromotionQuantity(){
        return promotionQuantity;
    }
    public int getGeneralQuantity(){
        return generalQuantity;
    }
    public int getTotalQauntity(){
        return promotionQuantity+generalQuantity;
    }
    public void addPromotionQuantity(int quantity){
        this.promotionQuantity+=quantity;
    }
    public void addGeneralQuantity(int quantity){
        this.generalQuantity+=quantity;
    }
    public void reducePromotionQuantity(int quantity){
        if(this.promotionQuantity>=quantity){
            this.promotionQuantity-=quantity;
        }else{
            throw new IllegalArgumentException("프로모션 재고 수량 부족");
        }
    }
    public void reduceGeneralQuantity(int quantity){
        if(this.generalQuantity>=quantity){
            this.generalQuantity-=quantity;
        }else{
            throw new IllegalArgumentException("재고 부족");
        }
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }
}
