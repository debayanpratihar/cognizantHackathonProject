package utils;

import models.Product;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelReportManager {

    private static final Workbook workbook = new XSSFWorkbook();
    private static final ConcurrentHashMap<String, Sheet> sheets = new ConcurrentHashMap<>();

    private ExcelReportManager() {}

    private static Sheet sheet(String name) {
        return sheets.computeIfAbsent(name, n -> workbook.createSheet(n));
    }

    private static int nextRow(Sheet s) {
        return (s.getPhysicalNumberOfRows() == 0) ? 0 : s.getLastRowNum() + 1;
    }

    public static synchronized void writeProducts(String sheetName, List<Product> products) {
        Sheet s = sheet(sheetName);
        if (s.getPhysicalNumberOfRows() == 0) {
            Row h = s.createRow(0);
            h.createCell(0).setCellValue("Name");
            h.createCell(1).setCellValue("Price");
        }
        int r = nextRow(s);
        for (Product p : products) {
            Row row = s.createRow(r++);
            row.createCell(0).setCellValue(p.name);
            row.createCell(1).setCellValue(p.price);
        }
        s.autoSizeColumn(0); s.autoSizeColumn(1);
    }

    public static synchronized void writeList(String sheetName, String title, List<String> items) {
        Sheet s = sheet(sheetName);
        int r = nextRow(s);

        Row t = s.createRow(r++);
        t.createCell(0).setCellValue(title);

        Row h = s.createRow(r++);
        h.createCell(0).setCellValue("Items");

        for (String item : items) {
            Row row = s.createRow(r++);
            row.createCell(0).setCellValue(item);
        }
        s.autoSizeColumn(0);
    }

    public static synchronized void writeGiftCard(String sheetName,
                                                  String senderEmail,
                                                  String receiverEmail,
                                                  String errorMsg) {
        Sheet s = sheet(sheetName);
        if (s.getPhysicalNumberOfRows() == 0) {
            Row h = s.createRow(0);
            h.createCell(0).setCellValue("Sender Email");
            h.createCell(1).setCellValue("Receiver Email");
            h.createCell(2).setCellValue("Captured Error Message");
        }
        int r = nextRow(s);
        Row row = s.createRow(r);
        row.createCell(0).setCellValue(senderEmail);
        row.createCell(1).setCellValue(receiverEmail);
        row.createCell(2).setCellValue(errorMsg == null ? "" : errorMsg);
        s.autoSizeColumn(0); s.autoSizeColumn(1); s.autoSizeColumn(2);
    }

    public static synchronized void flush() {
        try {
            new File("output").mkdirs();
            try (FileOutputStream fos = new FileOutputStream("output/UrbanLadder_Hackathon_Output.xlsx")) {
                workbook.write(fos);
            }
            workbook.close();
            System.out.println("[INFO] Excel generated: output/UrbanLadder_Hackathon_Output.xlsx");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}