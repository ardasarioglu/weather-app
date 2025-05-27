package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class CityTree {
    private final TreeNode root;

    public CityTree() {
        root = new TreeNode('\0');
    }

    public void insert(String city) {
        TreeNode current = root;
        for (char c : city.toLowerCase().toCharArray()) {
            current.children.putIfAbsent(c, new TreeNode(c));
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }

    public List<String> searchPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TreeNode current = root;

        for (char c : prefix.toLowerCase().toCharArray()) {
            if (!current.children.containsKey(c)) {
                return results;
            }
            current = current.children.get(c);
        }

        collectAllWords(current, prefix.toLowerCase(), results);
        return results;
    }

    private void collectAllWords(TreeNode node, String prefix, List<String> result) {
        if (node.isEndOfWord) {
            result.add(capitalize(prefix));
        }

        for (char c : node.children.keySet()) {
            collectAllWords(node.children.get(c), prefix + c, result);
        }
    }

    private String capitalize(String word) {
        if (word.length() == 0) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
}
