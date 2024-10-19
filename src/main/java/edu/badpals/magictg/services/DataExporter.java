package edu.badpals.magictg.services;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataExporter {


    public static void exportData(String fileName, String format, String data) {
        String exportDir = "exports";
        File dir = new File(exportDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(exportDir).append("/").append(fileName).append(".").append(format.toLowerCase());
        String filePath = sb.toString();

        switch (format) {
            case "JSON":
                saveAsJson(filePath, data);
                break;
            /*case "XML":
                saveAsXml(filePath, data);
                break;*/
            case "BIN":
                saveAsBinary(filePath, data);
                break;
            case "TXT":
                saveAsText(filePath, data);
                break;
            default:
                break;
        }
    }

    private static void saveAsJson(String filePath, String data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private static void saveAsBinary(String filePath, String data) {
        try {
            Files.write(Paths.get(filePath), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveAsText(String filePath, String data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
