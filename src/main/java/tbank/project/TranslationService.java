package tbank.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final RestTemplate restTemplate;
    private final DataSource dataSource;

    public String giveTranslation(String clientIp, String text, String sourceLanguage, String targetLanguage) {
        List<String> wordList = List.of(text.split(" "));
        List<CompletableFuture<String>> futures = new ArrayList<>();
        List<String> translatedWords = new ArrayList<>();

        try (Connection connection = dataSource.getConnection()) {
            for (String word : wordList) {
                CompletableFuture<String> future = CompletableFuture
                        .supplyAsync(() -> translateWord(word, sourceLanguage, targetLanguage));

                futures.add(future);
            }

            for (CompletableFuture<String> future : futures) {
                translatedWords.add(future.join());
            }

            String translatedText = String.join(" ", translatedWords);

            saveTranslationToDatabase(connection, clientIp, text, translatedText);

            return translatedText;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к базе данных", e);
        }
    }

    private void saveTranslationToDatabase(Connection connection,
            String clientIp,
            String originalText,
            String translatedText) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO translation " +
                "(client_ip, original_text, translated_text) VALUES (?, ?, ?)")) {

            statement.setString(1, clientIp);
            statement.setString(2, originalText);
            statement.setString(3, translatedText);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось сохранить перевод в базе данных", e);
        }
    }

    private String translateWord(String word, String sourceLanguage, String targetLanguage) {
        if (!word.isEmpty()) {
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl={sourceLanguage}&tl=" +
                    "{targetLanguage}&dt=t&q={word}";

            try {
                String response = restTemplate.getForObject(url, String.class, sourceLanguage, targetLanguage, word);

                JsonNode rootNode = new ObjectMapper().readTree(response);

                return rootNode.get(0).get(0).get(0).asText();
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Ошибка при обработке JSON-ответа из Google Translate API: ", e);
            }
        }

        return "";
    }
}
