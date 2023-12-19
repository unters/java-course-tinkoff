package edu.hw8.task1.toxicserver;

import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ToxicQuotesBook {
    private static final Map<String, String> QUOTERS_MAP = Map.of(
        "личности", "Не переходи на личности там, где их нет",
        "оскорбления", "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
        "глупый", "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
        "интеллект", "Чем ниже интеллект, тем громче оскорбления"
    );

    public static String getQuoteByKeyword(String keyword) {
        return QUOTERS_MAP.getOrDefault(keyword.toLowerCase().trim(), getDefaultQuote());
    }

    private static String getDefaultQuote() {
        return "Бе-бе-бе";
    }
}
