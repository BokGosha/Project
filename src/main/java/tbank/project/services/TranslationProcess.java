package tbank.project.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tbank.project.models.TranslationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class TranslationProcess {
    private final RestTemplate restTemplate;
    private static final Executor TRANSLATION_EXECUTOR = Executors.newFixedThreadPool(10);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public String translate(TranslationRequest translationRequest) {
        List<String> wordList = List.of(translationRequest.getText().split(" "));
        List<CompletableFuture<String>> futures = new ArrayList<>();
        List<String> translatedWords = new ArrayList<>();

        for (String word : wordList) {
            CompletableFuture<String> future = CompletableFuture
                    .supplyAsync(() -> translateWord(word, translationRequest.getSourceLanguage(),
                            translationRequest.getTargetLanguage()), TRANSLATION_EXECUTOR);

            futures.add(future);
        }

        for (CompletableFuture<String> future : futures) {
            translatedWords.add(future.join());
        }

        return String.join(" ", translatedWords);
    }

    private String translateWord(String word, String sourceLanguage, String targetLanguage) {
        if (!word.isEmpty()) {
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl={sourceLanguage}&tl=" +
                    "{targetLanguage}&dt=t&q={word}";

            try {
                String response = restTemplate.getForObject(url, String.class, sourceLanguage, targetLanguage, word);

                JsonNode rootNode = objectMapper.readTree(response);

                return rootNode.get(0).get(0).get(0).asText();
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Ошибка при обработке JSON-ответа из Google Translate API: ", e);
            }
        }

        return "";
    }
}
