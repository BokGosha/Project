package tbank.project;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tbank.project.models.Translation;

@Repository
@RequiredArgsConstructor
public class TranslationRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void save(Translation translation) {
        jdbcTemplate.update("INSERT INTO translation (client_ip, original_text, translated_text) VALUES (?, ?, ?)",
                translation.getClientIp(), translation.getOriginalText(), translation.getTranslatedText());
    }
}
