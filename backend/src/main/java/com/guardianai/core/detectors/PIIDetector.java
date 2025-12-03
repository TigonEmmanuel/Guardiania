package com.guardianai.core.detectors;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PIIDetector {
    private static final Pattern EMAIL = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[A-Za-z]{2,}");
    private static final Pattern PHONE = Pattern.compile("(\\+?\\d{1,3})?[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}");

    public static boolean containsPII(String text) {
        if (text == null) return false;
        Matcher m = EMAIL.matcher(text);
        if (m.find()) return true;
        m = PHONE.matcher(text);
        return m.find();
    }
}
