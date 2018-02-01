package ru.job4j.threads.examplesFileVisitor;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
    String partOfName;
    String partOfContent;
    Set<Path> foundFiles = new HashSet<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        boolean containsName = true;
        if (partOfName != null && !file.getFileName().toString().contains(partOfName)) {
            containsName = false;
        }

        String content = new String(Files.readAllBytes(file));
        boolean containsContent = true;
        if (partOfContent != null && !content.contains(partOfContent)) {
            containsContent = false;
        }

        if (containsName && containsContent) {
            foundFiles.add(file);
        }

        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) {
//        MyFileVisitor visitor = new MyFileVisitor();
//        Path path = new Path() {
//        }
//        visitor.visitFile()
    }
}


