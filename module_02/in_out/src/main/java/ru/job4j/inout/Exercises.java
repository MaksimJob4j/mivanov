package ru.job4j.inout;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Exercises {

    public boolean isNumber(InputStream in) {
        List<Integer> sizes = Arrays.asList(1, 2, 4, 8);
        int b;
        int last = 1;
        int size = 0;
        boolean notNull = false;
        try (ByteArrayInputStream stream = (ByteArrayInputStream) in) {
            while ((b = stream.read()) != -1) {
                notNull = notNull || b != 0;
                last = b;
                size++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return notNull && (last % 2 == 0) && (sizes.contains(size));
    }

    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {

            int intChar;
            if (abuse == null || abuse.length == 0) {
                while ((intChar = reader.read()) != -1) {
                    writer.write(intChar);
                }
            } else {
                StringBuilder testString = new StringBuilder();
                boolean acceptChar;
                while ((intChar = reader.read()) != -1) {
                    testString.append((char) intChar);
                    acceptChar = true;
                    for (String anAbuse : abuse) {
                        if (anAbuse.length() > testString.length()
                                && testString.toString().equals(anAbuse.substring(0, testString.length()))) {
                            acceptChar = false;
                            break;
                        } else if ((anAbuse.length() == testString.length()
                                && testString.toString().equals(anAbuse))) {
                            testString = new StringBuilder();
                            acceptChar = false;
                            break;
                        }
                    }
                    if (acceptChar) {
                        writer.write(testString.charAt(0));
                        testString.delete(0, 1);
                    }
                }
                if (testString.length() > 0) {
                    writer.write(testString.toString().toCharArray());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
