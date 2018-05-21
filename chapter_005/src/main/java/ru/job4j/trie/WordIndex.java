package ru.job4j.trie;

import java.io.*;
import java.util.Set;

public class WordIndex {
    private TrieNode root = new TrieNode(null, null);
    private String delimiters = " .,;:?![]{}()/'\\\"\n\r" + (char) 65279;

    //Загрузка данных из файла и построение индекса.
    public void loadFile(String filename) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename), "UTF8"))) {
            root = new TrieNode(null, null);
            TrieNode node = root;
            Integer index = 0;
            Integer startIndex = 0;
            int i;
            while ((i = br.read()) != -1) {
                index++;
                Character c = (char) i;
                if (delimiters.contains(c.toString())) {
                    if (node != root) {
                        node.getIndexes().add(startIndex);
                        node = root;
                        startIndex = index;
                    } else {
                        startIndex = index;
                    }
                } else {
                    node.getChildren().putIfAbsent(c, new TrieNode(node, c));
                    node = node.getChildren().get(c);
                }
            }
            if (node != root) {
                node.getIndexes().add(startIndex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Возвращает список позиций слова в файле.
    // Если данного слова в файле нет, то возвращается null.
    public Set<Integer> getIndexes4Word(String searchWord) {
        TrieNode node = root;
        for (char c: searchWord.toCharArray()) {
            node = node.getChildren().get(c);
            if (node == null) {
                return null;
            }
        }
        return node.getIndexes();
    }

    @Override
    public String toString() {
        return root.toString();
    }
}