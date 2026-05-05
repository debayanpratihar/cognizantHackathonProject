package utils;

import models.Product;
import java.io.FileWriter;
import java.util.List;

public class CsvUtils {

    public static void writeProducts(String fileName, List<Product> products) {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write("Name,Price\n");
            for (Product p : products) {
                fw.write(p.name + "," + p.price + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
