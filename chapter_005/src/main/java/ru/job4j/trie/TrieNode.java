package ru.job4j.trie;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Setter
@Getter
public class TrieNode {
    private TrieNode parent;
    private Character key;
    private Set<Integer> indexes = new TreeSet<>();
    private Map<Character, TrieNode> children = new HashMap<>();

    public TrieNode(TrieNode parent, Character key) {
        this.parent = parent;
        this.key = key;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (indexes.size() > 0) {
            sb.append(getParentsKey(parent));
            sb.append(key);
            sb.append(" ins:");
            for (Integer i : indexes) {
                sb.append(" ");
                sb.append(i);
            }
            sb.append("\n\r");
        }
        for (Map.Entry<Character, TrieNode> node: children.entrySet()) {
            sb.append(node.getValue().toString());
        }
        return sb.toString();
    }

    private String getParentsKey(TrieNode parent) {
        if (parent.parent == null) {
            return "";
        }
        return getParentsKey(parent.parent) + parent.key;
    }
}
