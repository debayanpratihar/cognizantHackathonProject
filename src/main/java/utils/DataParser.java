package utils;

public class DataParser {

    public static int priceToInt(String priceText) {
        return Integer.parseInt(
                priceText.replace("₹", "")
                        .replace(",", "")
                        .trim()
        );
    }
}