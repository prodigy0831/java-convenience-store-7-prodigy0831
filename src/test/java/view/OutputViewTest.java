package view;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.ProductRepository;

public class OutputViewTest {
    private static ProductRepository productRepository;

    @BeforeAll
    static void setUp() {
        productRepository = new ProductRepository();

    }

    @Test
    @DisplayName("출력 확인")
    void test() throws IOException {
        int expectedProductCount = countNonEmptyLines("src/main/resources/products.md");
        assertThat(productRepository.getProducts().size()).isEqualTo(expectedProductCount);
    }

    private int countNonEmptyLines(String filePath) throws IOException {
        Set<String> productNames = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // 빈 줄 제외
                    String productName = line.split(",")[0].trim();
                    productNames.add(productName);
                }
            }
        }
        return productNames.size();
    }
}
