package repository;

import camp.nextstep.edu.missionutils.DateTimes;
import domain.Promotion;
import domain.PromotionType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PromotionRepository {
    private final List<Promotion> promotions = new ArrayList<>();

    public PromotionRepository() {
        loadPromotions();
    }

    private void loadPromotions() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/promotions.md"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] data = line.split(",");
                String name = data[0];
                int buy = Integer.parseInt(data[1]);
                int get = Integer.parseInt(data[2]);

                PromotionType promotionType = PromotionType.NONE;
                if (buy == 1 && get == 1) {
                    promotionType = PromotionType.BUY_ONE_GET_ONE;
                }
                if (buy == 2 && get == 1) {
                    promotionType = PromotionType.BUY_TWO_GET_ONE;
                }
//
                LocalDate startDate = LocalDate.parse(data[3]);
                LocalDate endDate = LocalDate.parse(data[4]);

                LocalDateTime currentDateTime = DateTimes.now();
                LocalDate currentDate = currentDateTime.toLocalDate();
                if (!currentDate.isBefore(startDate) && !currentDate.isAfter(endDate)) {
                    promotions.add(new Promotion(name, promotionType, startDate, endDate));
                }

            }
        } catch (IOException e) {
            System.out.println("[ERROR] 파일 로딩 중 오류 발생");
        }
    }

    public List<Promotion> getPromotions() {
        return Collections.unmodifiableList(promotions);
    }

    public Optional<PromotionType> getPromotionTypeByName(String promotionName) {
        return promotions.stream()
                .filter(promotion -> promotion.getName().equals(promotionName))
                .map(Promotion::getPromotionType)
                .findFirst();
    }
}
