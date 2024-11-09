package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    public static String readItem() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예:[사이다-2],[감자칩-1])");
        return Console.readLine();
    }
    public static String confirmFreeItemAddition(String productName){
        System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n",productName);
        return Console.readLine();
    }
    public static String confirmPurchaseWithoutPromotion(String productName,int quantity){
        System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n",productName,quantity);
        return Console.readLine();
    }
}
