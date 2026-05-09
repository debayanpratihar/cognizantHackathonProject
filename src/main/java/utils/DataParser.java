package utils;

public class DataParser {
    public static int priceToInt(String text) {
        if (text == null) return -1;
        String cleaned = text.replaceAll("[^0-9]", "");
        if (cleaned.isEmpty()) return -1;
        return Integer.parseInt(cleaned);
    }
}