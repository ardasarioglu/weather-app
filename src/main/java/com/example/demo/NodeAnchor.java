package com.example.demo;

import javafx.scene.layout.AnchorPane;

public class NodeAnchor {
    public AnchorPane data;
    public NodeAnchor next;

    public NodeAnchor(AnchorPane data){
        this.data=data;
        this.next=null;
    }
}
