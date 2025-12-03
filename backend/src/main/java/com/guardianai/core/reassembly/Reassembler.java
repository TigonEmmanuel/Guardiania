package com.guardianai.core.reassembly;

public class Reassembler {
    public static String merge(String original, String sanitized) {
        // For now, prefer sanitized
        return sanitized == null ? original : sanitized;
    }
}
