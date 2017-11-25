/**
 4. Поиск текста в файле. [#1106]
 1. Нужно осуществлять обход файловой системы и поиск заданного текста в файловой системе.
 public class ParallerSearch(String root, String text, List<String> exts) { }
 ,где
 root - путь до папки откуда надо осуществлять поиск.
 text - заданных текст.
 exts - расширения файлов в которых нужно делать поиск.

 Приложения должно искать текст в файлах (в том числе и в подкаталогах) и сохранять адрес файла.
 List<String> result(); - возвращает список всех файлов.

 Логика приложения.
 1. Запустить код.
 2. Внутри запустить несколько потоков. Объяснить для чего нужно делать потоки.
 3. Дождаться завершения поиска.
 4. Вывести на консоль результат.
 */
package ru.job4j.threads;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.FileVisitResult.*;

public class ParallerSearch {
    private List<String> files;
    private String root;
    private String text;
    private List<String> exts;
    private PathMatcher matcher;
    private Integer treadCount = 0;


    public ParallerSearch(String root, String text, List<String> exts){
        this.root = root;
        this.text = text;
        this.exts = exts;
        files = new ArrayList<String>();
    }

    class Visitor extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            findText(file);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return CONTINUE;
        }

    }

    List<String> result() throws IOException, InterruptedException {
        makeMatcher();
        Visitor visitor = new Visitor();
        Files.walkFileTree(Paths.get(root), visitor);

        synchronized (this.treadCount) {
            while (this.treadCount > 0) {
//                treadCount.wait();
                this.treadCount.wait(100);
            }
        }

        return files;
    }

    private void findText(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            Thread thread = new Thread(){

                @Override
                public void run() {

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file.toFile()));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            if (line.contains(text)){
                                synchronized (ParallerSearch.this.files) {
                                    ParallerSearch.this.files.add(file.toString());
                                }
                                break;
                            }
                        }
                    } catch (IOException e) {
                            e.printStackTrace();
                    } finally {
                        synchronized (ParallerSearch.this.treadCount) {
                            ParallerSearch.this.treadCount--;
//                            ParallerSearch.this.treadCount.notify();
                        }
                    }

                }
            };
            synchronized (this.treadCount) {
                this.treadCount++;
            }
            thread.start();

        }
    }

    private void makeMatcher() {
        StringBuilder pattern = new StringBuilder();
        pattern.append("*.");
        if (exts.size() == 0) {
            pattern.append("*");
        } else if (exts.size() == 1) {
            pattern.append(exts.get(0));
        } else {
            pattern.append("{");
            for (int i = 0; i < exts.size(); i++) {
                if (i > 0) {
                    pattern.append(",");
                }
                pattern.append(exts.get(i));
            }
            pattern.append("}");
        }

        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern.toString());
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        List<String> ests = new ArrayList<>();
        ests.add("TXT");
        ests.add("java");

        ParallerSearch search = new ParallerSearch("c:\\projects", "class", ests);

        for (String res: search.result()) {
            System.out.println(res);
        }
    }
}
