package service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import repository.ProductRepository;

public class InputHandler {
    private final static String INVALID_INPUT_FORMAT = "[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.";
    private static final Pattern INPUT_PATTERN = Pattern.compile(("\\[(.+)-([0-9]+)\\]"));

    public static void processInput(String items, ProductRepository productRepository){
        if(items==null || items.isBlank()){
            throw new IllegalArgumentException(INVALID_INPUT_FORMAT);
        }
    }
}
