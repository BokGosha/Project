package tbank.project;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("af", "sq", "am", "ar", "hy", "az", "eu",
            "be", "bn", "bs", "bg", "ca", "ceb", "ny", "zh-CN", "zh-TW", "co", "hr", "cs", "da", "nl", "en", "eo",
            "et", "tl", "fi", "fr", "fy", "gl", "ka", "de", "el", "gu", "ht", "ha", "haw", "iw", "hi", "hmn", "hu",
            "is", "ig", "id", "ga", "it", "ja", "jw", "kn", "kk", "km", "ko", "ku", "ky", "lo", "la", "lv", "lt", "lb",
            "mk", "mg", "ms", "ml", "mt", "mi", "mr", "mn", "my", "ne", "no", "or", "ps", "fa", "pl", "pt", "pa", "ro",
            "ru", "sm", "gd", "sr", "st", "sn", "sd", "si", "sk", "sl", "so", "es", "su", "sw", "sv", "tg", "ta", "te",
            "th", "tr", "uk", "ur", "uz", "vi", "cy", "xh", "yi", "yo", "zu");

    @GetMapping("/translate")
    public ResponseEntity<String> translateWords(@RequestParam("words") String text,
                                                 @RequestParam("sourceLanguage") String sourceLanguage,
                                                 @RequestParam("targetLanguage") String targetLanguage,
                                                 HttpServletRequest request) throws SQLException {
        if (!SUPPORTED_LANGUAGES.contains(sourceLanguage.toLowerCase())) {
            return ResponseEntity.badRequest()
                    .body("Язык источника " + sourceLanguage + " не поддерживается Google Translate.");
        }

        if (!SUPPORTED_LANGUAGES.contains(targetLanguage.toLowerCase())) {
            return ResponseEntity.badRequest()
                    .body("Язык перевода " + targetLanguage + " не поддерживается Google Translate.");
        }

        String clientIp = request.getRemoteAddr();

        String translatedText = translationService.giveTranslation(clientIp, text, sourceLanguage, targetLanguage);

        return ResponseEntity.ok(translatedText);
    }
}
