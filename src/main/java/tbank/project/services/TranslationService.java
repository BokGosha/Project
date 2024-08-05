package tbank.project.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tbank.project.SupportedLanguages;
import tbank.project.models.TranslationRepository;
import tbank.project.models.Translation;
import tbank.project.models.TranslationRequest;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationProcess translationProcess;
    private final TranslationRepository translationRepository;

    public String giveTranslation(String clientIp, String text, String sourceLanguage, String targetLanguage) {
        checkSupportedLanguages(sourceLanguage, targetLanguage);

        TranslationRequest translationRequest = new TranslationRequest(text, sourceLanguage, targetLanguage);

        String translatedText = translationProcess.translate(translationRequest);

        Translation translation = new Translation(clientIp, text, translatedText);

        translationRepository.save(translation);

        return translatedText;
    }

    private void checkSupportedLanguages(String sourceLanguage, String targetLanguage) {
        if (!SupportedLanguages.supportedLanguage(sourceLanguage)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Язык источника " + sourceLanguage + " не поддерживается Google Translate.");
        }

        if (!SupportedLanguages.supportedLanguage(targetLanguage)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Язык перевода " + targetLanguage + " не поддерживается Google Translate.");
        }
    }
}
