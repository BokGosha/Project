package tbank.project;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class TranslationController {
    private final TranslationService translationService;

    @GetMapping("/translate")
    public ResponseEntity<String> translateWords(@RequestParam("words") String text,
                                                 @RequestParam("sourceLanguage") String sourceLanguage,
                                                 @RequestParam("targetLanguage") String targetLanguage,
                                                 HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();

        String translatedText = translationService.giveTranslation(clientIp, text, sourceLanguage, targetLanguage);

        return ResponseEntity.ok(translatedText);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(ResponseStatusException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
    }
}
