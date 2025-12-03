package com.guardianai.core.sanitizers;

import java.util.regex.Pattern;

public class Sanitizer {

    private static final Pattern KEY = Pattern.compile("(?i)sk-[A-Za-z0-9]{20,}");
    private static final Pattern URL = Pattern.compile("https?:\\/\\/[^\\s]+", Pattern.CASE_INSENSITIVE);
    private static final Pattern EMAIL = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[A-Za-z]{2,}");
    private static final Pattern PHONE = Pattern.compile("(\\+?\\d{1,3})?[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}");
    private static final Pattern NSFW = Pattern.compile("(?i)(nude|porn|sex|xxx|erotic|vagina|penis)", Pattern.CASE_INSENSITIVE);

    private static final Pattern PROFANITY = Pattern.compile("(?i)(damn|shit|fuck|bitch|asshole|bastard|dick|crap|piss|slut|whore)", Pattern.CASE_INSENSITIVE);
    private static final Pattern CUSTOM_KEYWORDS = Pattern.compile("(?i)(confidential|secret|classified|proprietary)", Pattern.CASE_INSENSITIVE);

    public static String maskKeys(String text) {
        if (text == null) return null;
        return KEY.matcher(text).replaceAll("[KEY_REMOVED]");
    }

    public static String redactUrls(String text) {
        if (text == null) return null;
        return URL.matcher(text).replaceAll("[URL_REDACTED]");
    }

    public static String maskPII(String text) {
        if (text == null) return null;
        String out = EMAIL.matcher(text).replaceAll("[EMAIL]");
        out = PHONE.matcher(out).replaceAll("[PHONE]");
        return out;
    }

    public static String sanitizeNSFW(String text) {
        if (text == null) return null;
        return NSFW.matcher(text).replaceAll("[NSFW]");
    }

    public static String sanitizeProfanity(String text) {
        if (text == null) return null;
        return PROFANITY.matcher(text).replaceAll("[PROFANITY]");
    }

    public static String sanitizeCustomKeywords(String text) {
        if (text == null) return null;
        return CUSTOM_KEYWORDS.matcher(text).replaceAll("[REDACTED]");
    }
}
