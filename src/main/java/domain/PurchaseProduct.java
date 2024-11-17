package domain;

public class PurchaseProduct {
    private String name;
    private int quantity;
    private int price;

    public PurchaseProduct(String name, int quantity,int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void subtractQuantity(int quantity){
        this.quantity-=quantity;
    }
    public void addGiveawayQuantity(){
        this.quantity+=1;
    }
}
