package ru.job4j.inout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;

public class Analizy {
    private final static Logger LOGGER = LogManager.getLogger(Analizy.class);

    public void unavailable(String source, String target) {
        LOGGER.traceEntry();
        try (BufferedReader read = new BufferedReader(new FileReader(source));
             PrintWriter out = new PrintWriter(new FileOutputStream(target))
        ) {
            String start = null;
            String s;
            while ((s = read.readLine()) != null) {
                if (s.matches("^[45]00.*") && start == null) {
                    start = s.substring(4);
                }
                if (s.matches("^[23]00.*") && start != null) {
                    out.format("%s;%s%n", start, s.substring(4));
                    start = null;
                }
            }
        } catch (Exception e) {
            LOGGER.traceEntry(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        try (PrintWriter out = new PrintWriter(new FileOutputStream("unavailable.csv"))) {
            out.println("15:01:30;15:02:32");
            out.println("15:10:30;23:12:32");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
