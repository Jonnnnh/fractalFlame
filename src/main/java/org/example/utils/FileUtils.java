package org.example.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class FileUtils {

    private static final String OUTPUT_DIR = "output";

    public static File getOutputDirectory() {
        File directory = new File(OUTPUT_DIR);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Output directory created: " + OUTPUT_DIR);
            } else {
                System.out.println("Failed to create output directory: " + OUTPUT_DIR);
            }
        }
        return directory;
    }

    public static String generateFileName(String extension) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return OUTPUT_DIR + File.separator + "fractal_" + timestamp + "." + extension;
    }
}
