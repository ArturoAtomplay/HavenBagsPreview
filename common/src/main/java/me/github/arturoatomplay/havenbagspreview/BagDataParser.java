package me.github.arturoatomplay.havenbagspreview;

import com.google.gson.Gson;

public class BagDataParser {
    private static final Gson gson = new Gson();

    public static class ParseResult {
        public final String content;
        public final int size;
        public final boolean valid;

        public ParseResult(String content, int size) {
            this.content = content;
            this.size = size;
            this.valid = content != null && !content.isEmpty() && size > 0;
        }

        public static ParseResult invalid() {
            return new ParseResult(null, 0);
        }
    }

    public static BackpackData parseJson(String json) {
        try {
            BackpackData data = gson.fromJson(json, BackpackData.class);
            return (data != null && data.items != null) ? data : null;
        } catch (Exception e) {
            return null;
        }
    }
}
