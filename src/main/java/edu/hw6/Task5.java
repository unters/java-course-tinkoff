package edu.hw6;

import edu.hw6.task5.HackerNews;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task5 {
    private static final Logger LOGGER = LogManager.getLogger();

    /* I have intentionally left Task5#main() uncommented.  */
    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        for (long newsId : HackerNews.getTopStories()) {
            Optional<String> title = HackerNews.getNewsTitle(newsId);
            if (title.isPresent()) {
                LOGGER.info("Title for newsId %d is \"%s\".".formatted(newsId, title.get()));
            } else {
                LOGGER.info("Could not retrieve title for newsId %d.".formatted(newsId));
            }
        }
    }

    private Task5() {
    }
}
