package tbank.project.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Translation {
    private String clientIp;
    private String originalText;
    private String translatedText;
}
