package com.guardianai.ai.client;

import java.util.List;
import java.util.Map;

public class OpenRouterResponse {
    public String output;
    public List<Map<String, Object>> choices;

    // helper to extract best text
    public String extractText() {
        if (output != null) return output;
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> first = choices.get(0);
            Object t = first.get("text");
            if (t instanceof String) return (String) t;
            Object message = first.get("message");
            if (message instanceof Map) {
                Object content = ((Map) message).get("content");
                if (content instanceof String) return (String) content;
            }
            Object delta = first.get("delta");
            if (delta instanceof Map) {
                Object c = ((Map) delta).get("content");
                if (c instanceof String) return (String) c;
            }
        }
        return null;
    }
}
