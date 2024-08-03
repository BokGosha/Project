package tbank.project;

import java.util.Arrays;
import java.util.List;

public class SupportedLanguages {
    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("af", "sq", "am", "ar", "hy", "az", "eu",
            "be", "bn", "bs", "bg", "ca", "ceb", "ny", "zh-CN", "zh-TW", "co", "hr", "cs", "da", "nl", "en", "eo",
            "et", "tl", "fi", "fr", "fy", "gl", "ka", "de", "el", "gu", "ht", "ha", "haw", "iw", "hi", "hmn", "hu",
            "is", "ig", "id", "ga", "it", "ja", "jw", "kn", "kk", "km", "ko", "ku", "ky", "lo", "la", "lv", "lt", "lb",
            "mk", "mg", "ms", "ml", "mt", "mi", "mr", "mn", "my", "ne", "no", "or", "ps", "fa", "pl", "pt", "pa", "ro",
            "ru", "sm", "gd", "sr", "st", "sn", "sd", "si", "sk", "sl", "so", "es", "su", "sw", "sv", "tg", "ta", "te",
            "th", "tr", "uk", "ur", "uz", "vi", "cy", "xh", "yi", "yo", "zu");

    public static boolean supportedLanguage(String language) {
        return SUPPORTED_LANGUAGES.contains(language.toLowerCase());
    }
}
