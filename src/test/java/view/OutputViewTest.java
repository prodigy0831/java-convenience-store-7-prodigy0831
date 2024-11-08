package view;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;

public class OutputViewTest {
    private static ProductRepository productRepository;
    @BeforeAll
    static void setUp(){
        productRepository = new ProductRepository();

    }

    @Test
    @DisplayName("출력 확인")
    void test() throws IOException {
        int expectedLineCount = countNonEmptyLines("src/main/resources/products.md") - 1; // 헤더 제외
        assertThat(productRepository.getProducts().size()).isEqualTo(expectedLineCount);
    }

    private int countNonEmptyLines(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int lineCount = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // 빈 줄 제외
                    lineCount++;
                }
            }
            return lineCount;
        }
    }
}
