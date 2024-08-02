CREATE TABLE IF NOT EXISTS translation (
    translation_id SERIAL PRIMARY KEY,
    client_ip VARCHAR(45),
    original_text TEXT,
    translated_text TEXT
);
