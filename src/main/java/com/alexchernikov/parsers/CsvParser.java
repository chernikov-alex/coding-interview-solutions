package com.alexchernikov.parsers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    public static void main(String[] args) {
        String resourceName = "Book1.csv";
        List<String> lines = parseCsv(resourceName);
        lines.forEach(System.out::println);
    }

    private static List<String> parseCsv(String resourceName) {
        List<String> records = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        try (InputStream is = CsvParser.class
                .getClassLoader()
                .getResourceAsStream(resourceName)) {

            if (is == null) {
                throw new RuntimeException("CSV not found: " + resourceName);
            }

            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

            int ch;
            while ((ch = reader.read()) != -1) {
                char c = (char) ch;

                if (c == '"') {
                    insideQuotes = !insideQuotes;
                    // do NOT append quotes
                } else if (c == '\n' && !insideQuotes) {
                    records.add(current.toString());
                    current.setLength(0);
                } else {
                    current.append(c);
                }
            }

            // last record (if file doesn't end with newline)
            if (current.length() > 0) {
                records.add(current.toString());
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV", e);
        }

        return records;


//        try (BufferedReader br = new BufferedReader(new FileReader(resourceName))) {
//            int ch;
//            while ((ch = br.read()) != -1) {
//                char character = (char) ch;
//                if (character == '"') {
//                    insideQuotes = !insideQuotes;
//                } else if (character == ',' && !insideQuotes) {
//                    records.add(current.toString().trim());
//                    current.setLength(0);
//                } else if (character == '\n' && !insideQuotes) {
//                    records.add(current.toString().trim());
//                    System.out.println(records);
//                    records.clear();
//                    current.setLength(0);
//                } else {
//                    current.append(character);
//                }
//            }
//            // Add last record if exists
//            if (current.length() > 0) {
//                records.add(current.toString().trim());
//                System.out.println(records);
//            }
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found: " + resourceName);
//        } catch (IOException e) {
//            System.err.println("Error reading file: " + resourceName);
//        }

    }


    private static void readCsv() {
        InputStream is = CsvParser.class
                .getClassLoader()
                .getResourceAsStream("Book1.csv");

        if (is == null) {
            throw new RuntimeException("Book1.csv not found on classpath");
        }

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV", e);
        }

        System.out.println("Finished reading CSV");
    }
}
