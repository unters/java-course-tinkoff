package edu.hw6;

import edu.hw6.task5.HackerNews;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/* These links have been intentionally left for future use.
 * https://stackoverflow.com/questions/76157488/how-to-mock-httpclient-send-with-mockito
 * https://www.baeldung.com/mockito-exceptions  */
public class Task5Test {
    @Test
    void getTopStories_IOExceptionOccurs_EmptyArrayIsReturned() throws IOException, InterruptedException {
        HttpClient httpClientMock = Mockito.mock(HttpClient.class);
        try (MockedStatic<HttpClient> httpClientMockedStatic = Mockito.mockStatic(HttpClient.class)) {
            httpClientMockedStatic.when(HttpClient::newHttpClient).thenReturn(httpClientMock);
            Mockito.when(httpClientMock.send(
                Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
            )).thenThrow(IOException.class);

            // when
            long[] actualAnswer = HackerNews.getTopStories();

            // then
            assertThat(actualAnswer).isEqualTo(new long[0]);
        }
    }

    @Test
    void getNewsTitle_IOExceptionOccurs_EmptyOptionalIsReturned() throws IOException, InterruptedException {
        HttpClient httpClientMock = Mockito.mock(HttpClient.class);
        try (MockedStatic<HttpClient> httpClientMockedStatic = Mockito.mockStatic(HttpClient.class)) {
            httpClientMockedStatic.when(HttpClient::newHttpClient).thenReturn(httpClientMock);
            Mockito.when(httpClientMock.send(
                Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
            )).thenThrow(IOException.class);

            // when
            Optional<String> actualAnswer = HackerNews.getNewsTitle(37570037);

            // then
            assertThat(actualAnswer.isEmpty()).isTrue();
        }
    }

    @Test
    void getTopStories_RequestSentResponseReturnedNoExceptionOccurred_ArrayOfIdsIsReturned()
        throws IOException, InterruptedException {
        HttpClient httpClientMock = Mockito.mock(HttpClient.class);
        HttpResponse<String> httpResponseMock = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponseMock.body()).thenReturn("[37570037,38279459,38277412]");
        try (MockedStatic<HttpClient> httpClientMockedStatic = Mockito.mockStatic(HttpClient.class)) {
            httpClientMockedStatic.when(HttpClient::newHttpClient).thenReturn(httpClientMock);
            Mockito.when(httpClientMock.send(
                Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
            )).thenReturn(httpResponseMock);

            // when
            long[] actualAnswer = HackerNews.getTopStories();
            long[] expectedAnswer = new long[] {37570037, 38279459, 38277412};

            // then
            assertThat(actualAnswer).isEqualTo(expectedAnswer);
        }
    }

    @Test
    void getNewsTitle_RequestSentResponseReturnedNoExceptionOccurred_NonEmptyOptionalIsReturned()
        throws IOException, InterruptedException {
        String response = "{\"by\":\"bane\",\"descendants\":10,\"id\":38273488,\"kids\":[38282614,38281832,38281978,382"
            + "82109],\"score\":28,\"time\":1700024178,\"title\":\"Bare Metal Emulation on the Raspberry Pi – Commodore"
            + " 64\",\"type\":\"story\",\"url\":\"https://accentual.com/bmc64/\"}";

        HttpClient httpClientMock = Mockito.mock(HttpClient.class);
        HttpResponse<String> httpResponseMock = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponseMock.body()).thenReturn(response);
        try (MockedStatic<HttpClient> httpClientMockedStatic = Mockito.mockStatic(HttpClient.class)) {
            httpClientMockedStatic.when(HttpClient::newHttpClient).thenReturn(httpClientMock);
            Mockito.when(httpClientMock.send(
                Mockito.any(HttpRequest.class),
                ArgumentMatchers.<HttpResponse.BodyHandler<String>>any()
            )).thenReturn(httpResponseMock);

            // when
            Optional<String> actualAnswer = HackerNews.getNewsTitle(38273488);

            // then
            assertThat(actualAnswer.isEmpty()).isFalse();
            assertThat(actualAnswer.get()).isEqualTo("Bare Metal Emulation on the Raspberry Pi – Commodore 64");
        }
    }
}
