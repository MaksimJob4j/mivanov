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
}
