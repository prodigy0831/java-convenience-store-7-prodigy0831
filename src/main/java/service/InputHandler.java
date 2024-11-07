package service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import repository.ProductRepository;

public class InputHandler {
    private static final Pattern INPUT_PATTERN = Pattern.compile(("\\[(.+)-([0-9]+)\\]"));

    public static void processInput(String items, ProductRepository productRepository){
    }
}
