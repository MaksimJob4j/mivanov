package ru.job4j.inout.search;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.*;

public class Search {

    public List<File> files(String parent, List<String> exts) {
        List<File> files = new ArrayList<>();
        if (exts != null && exts.size() != 0) {
            File file = new File(parent);
            if (file.exists()) {
                Queue<File> filesForCheck = new LinkedList<>();
                filesForCheck.offer(file);
                while (!filesForCheck.isEmpty()) {
                    File[] currentFiles = filesForCheck.poll().listFiles();
                    if (currentFiles != null) {
                        for (File f : currentFiles) {
                            if (f.isDirectory()) {
                                filesForCheck.offer(f);
                            } else if (exts.contains(FilenameUtils.getExtension(f.getName()))) {
                                files.add(f);
                            }
                        }
                    }
                }

            }
        }
        return files;
    }
}
