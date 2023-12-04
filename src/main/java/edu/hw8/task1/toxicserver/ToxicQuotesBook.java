package edu.hw8.task1.toxicserver;

import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ToxicQuotesBook {
    private static final Map<String, String> QUOTERS_MAP = new HashMap<>();

    static {
        QUOTERS_MAP.put(
            "личности",
            "Не переходи на личности там, где их нет"
        );
        QUOTERS_MAP.put(
            "оскорбления",
            "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами"
        );
        QUOTERS_MAP.put(
            "глупый",
            "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."
        );
        QUOTERS_MAP.put(
            "интеллект",
            "Чем ниже интеллект, тем громче оскорбления"
        );
    }

    public static String getQuoteByKeyword(String keyword) {
        return QUOTERS_MAP.getOrDefault(keyword.toLowerCase().trim(), getDefaultQuote());
    }

    private static String getDefaultQuote() {
        return "Бе-бе-бе";
    }
}
