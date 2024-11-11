package view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private static final String[] YES_OPTIONS = {"Y", "y"};
    private static final String[] NO_OPTIONS = {"N", "n"};

    private static final String ITEM_INPUT_PROMPT = "구매하실 상품명과 수량을 입력해 주세요. (예:[사이다-2],[감자칩-1])";
    private static final String CONFIRM_FREE_ITEM_ADDITION_PROMPT = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String CONFIRM_PURCHASE_WITHOUT_PROMOTION_PROMPT = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private static final String CONFIRM_USE_MEMBERSHIP_PROMPT = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String CONFIRM_ADDITIONAL_PURCHASE_PROMPT = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";
    private static final String ERROR_INVALID_INPUT = "[ERROR] 올바른 값을 입력해 주세요. (Y/N)";

    public static String readItem() {
        System.out.print(System.lineSeparator());
        System.out.println(ITEM_INPUT_PROMPT);
        return Console.readLine();
    }

    public static String confirmFreeItemAddition(String productName) {
        System.out.print(System.lineSeparator());
        System.out.printf(CONFIRM_FREE_ITEM_ADDITION_PROMPT + "%n", productName);
        return validateYesOrNo();
    }

    public static String confirmPurchaseWithoutPromotion(String productName, int quantity) {
        System.out.print(System.lineSeparator());
        System.out.printf(CONFIRM_PURCHASE_WITHOUT_PROMOTION_PROMPT + "%n", productName, quantity);
        return validateYesOrNo();
    }

    public static String confirmUseMembership() {
        System.out.print(System.lineSeparator());
        System.out.println(CONFIRM_USE_MEMBERSHIP_PROMPT);
        return validateYesOrNo();
    }

    public static String confirmAdditionalPurchase() {
        System.out.print(System.lineSeparator());
        System.out.println(CONFIRM_ADDITIONAL_PURCHASE_PROMPT);
        return validateYesOrNo();
    }

    private static String validateYesOrNo() {
        while (true) {
            String input = Console.readLine().trim();
            if (isYes(input) || isNo(input)) {
                return input.toUpperCase();
            }
            System.out.println(ERROR_INVALID_INPUT);
        }
    }

    private static boolean isYes(String input) {
        for (String yesOption : YES_OPTIONS) {
            if (yesOption.equals(input)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNo(String input) {
        for (String noOption : NO_OPTIONS) {
            if (noOption.equals(input)) {
                return true;
            }
        }
        return false;
    }
}