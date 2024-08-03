package tbank.project.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TranslationRequest {
    private String text;
    private String sourceLanguage;
    private String targetLanguage;
}
