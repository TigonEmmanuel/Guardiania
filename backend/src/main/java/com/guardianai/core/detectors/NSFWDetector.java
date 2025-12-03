package com.guardianai.core.detectors;

import java.util.regex.Pattern;

public class NSFWDetector {
    private static final Pattern NSFW = Pattern.compile("(?i)(nude|porn|sex|xxx|erotic|vagina|penis)");
    public static boolean isNSFW(String text) {
        if (text == null) return false;
        return NSFW.matcher(text).find();
    }
}
