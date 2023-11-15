package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private static final String ENDPOINT_1 = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String ENDPOINT_2_PREFIX = "https://hacker-news.firebaseio.com/v0/item/";

    private static final HttpRequest GET_TOP_STORIES_HTTP_REQUEST =
        HttpRequest.newBuilder(URI.create(ENDPOINT_1)).build();

    private static final Pattern NEWS_TITLE_REGEX = Pattern.compile("\"title\":\"(?<title>[^\"]*)\",");

    public static long[] getTopStories() {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            String response =
                httpClient.send(GET_TOP_STORIES_HTTP_REQUEST, HttpResponse.BodyHandlers.ofString()).body();
            String[] topStories = response.substring(1, response.length() - 1).split(",");

            long[] answer = new long[topStories.length];
            for (int i = 0; i < answer.length; ++i) {
                answer[i] = Long.parseLong(topStories[i]);
            }

            return answer;
        } catch (IOException | InterruptedException e) {
            return new long[0];
        }
    }

    public static Optional<String> getNewsTitle(long newsId) {
        try (HttpClient httpClient = HttpClient.newHttpClient()) {
            HttpRequest httpRequest =
                HttpRequest.newBuilder(URI.create(ENDPOINT_2_PREFIX + Long.toString(newsId) + ".json")).build();
            String response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();

            Matcher matcher = NEWS_TITLE_REGEX.matcher(response);
            if (!matcher.find()) {
                return Optional.empty();
            }

            String title = matcher.group("title");
            return Optional.of(title);
        } catch (IOException | InterruptedException e) {
            return Optional.empty();
        }
    }

    private HackerNews() {
    }
}
