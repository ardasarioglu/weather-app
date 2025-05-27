package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class TreeNode {
    char value;
    boolean isEndOfWord;
    Map<Character, TreeNode> children;

    public TreeNode(char value) {
        this.value = value;
        this.isEndOfWord = false;
        this.children = new HashMap<>();
    }
}
