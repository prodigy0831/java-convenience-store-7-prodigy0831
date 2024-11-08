package repository;

import domain.Product;
import domain.Promotion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PromotionRepository {
    private static final List<Promotion> promotions =new ArrayList<>();

    static {
        loadPromotions();
    }

    private static void loadPromotions() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/promotions.md"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {

                if(line.trim().isEmpty()){
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0];
                int buy = Integer.parseInt(data[1]);
                int get = Integer.parseInt(data[2]);
                LocalDate startDate = LocalDate.parse(data[3]);
                LocalDate endDate = LocalDate.parse(data[4]);

                promotions.add(new Promotion(name,buy,get,startDate,endDate));
            }
        } catch (IOException e) {
            System.out.println("[ERROR] 파일 로딩 중 오류 발생");
        }
    }
    public static List<Promotion> getPromotions() {
        return promotions;
    }
}
