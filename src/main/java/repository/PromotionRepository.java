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
    public static final String PROMOTIONS_FILE_PATH = "src/main/resources/promotions.md";
    public static final String FILE_LOADING_ERROR_MESSAGE = "[ERROR] 파일 로딩 중 오류 발생";
    public static final String DELIMITER = ",";
    public static final int NAME_INDEX = 0;
    public static final int BUY_INDEX = 1;
    public static final int GET_INDEX = 2;
    public static final int START_DATE_INDEX = 3;
    public static final int END_DATE_INDEX = 4;

    private final List<Promotion> promotions = new ArrayList<>();

    public PromotionRepository() {
        loadPromotions();
    }

    private void loadPromotions() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROMOTIONS_FILE_PATH))) {
            skipHeader(reader);
            processPromotionLines(reader);
        } catch (IOException e) {
            System.out.println(FILE_LOADING_ERROR_MESSAGE);
        }
    }

    private void processPromotionLines(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                processPromotionLine(line);
            }
        }
    }

    private void processPromotionLine(String line) {
        String[] data = line.split(DELIMITER);
        String name = data[NAME_INDEX];
        int buy = Integer.parseInt(data[BUY_INDEX]);
        int get = Integer.parseInt(data[GET_INDEX]);

        PromotionType promotionType = PromotionType.fromBuyAndGet(buy, get);

        LocalDate startDate = LocalDate.parse(data[START_DATE_INDEX]);
        LocalDate endDate = LocalDate.parse(data[END_DATE_INDEX]);

        addPromotionIfActive(startDate, endDate, name, promotionType);
    }

    private void addPromotionIfActive(LocalDate startDate, LocalDate endDate, String name,
                                      PromotionType promotionType) {
        LocalDateTime currentDateTime = DateTimes.now();
        LocalDate currentDate = currentDateTime.toLocalDate();
        if (!currentDate.isBefore(startDate) && !currentDate.isAfter(endDate)) {
            promotions.add(new Promotion(name, promotionType, startDate, endDate));
        }
    }

    private void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
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
