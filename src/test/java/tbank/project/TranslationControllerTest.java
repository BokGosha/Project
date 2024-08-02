package tbank.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TranslationControllerTest {
    @Mock
    private TranslationService translationService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private TranslationController translationController;

    @Test
    public void testTranslateWords_ValidLanguages() throws SQLException {
        String text = "Hello";
        String sourceLanguage = "en";
        String targetLanguage = "ru";
        String clientIp = "127.0.0.1";
        String translatedText = "Привет";

        when(request.getRemoteAddr()).thenReturn(clientIp);
        when(translationService.giveTranslation(clientIp, text, sourceLanguage, targetLanguage))
                .thenReturn(translatedText);

        ResponseEntity<String> response = translationController.translateWords(text, sourceLanguage, targetLanguage,
                request);

        assertEquals(ResponseEntity.ok(translatedText), response);
    }

    @Test
    public void testTranslateWords_UnsupportedSourceLanguage() throws SQLException {
        String text = "Hello";
        String sourceLanguage = "xx";
        String targetLanguage = "ru";

        ResponseEntity<String> response = translationController.translateWords(text, sourceLanguage, targetLanguage,
                request);

        assertEquals(ResponseEntity.badRequest()
                .body("Язык источника " + sourceLanguage + " не поддерживается Google Translate."), response);
    }

    @Test
    public void testTranslateWords_UnsupportedTargetLanguage() throws SQLException {
        String text = "Hello";
        String sourceLanguage = "en";
        String targetLanguage = "xx";

        ResponseEntity<String> response = translationController.translateWords(text, sourceLanguage, targetLanguage,
                request);

        assertEquals(ResponseEntity.badRequest()
                .body("Язык перевода " + targetLanguage + " не поддерживается Google Translate."), response);
    }
}
