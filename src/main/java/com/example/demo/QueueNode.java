package com.example.demo;

import javafx.scene.layout.AnchorPane;

public class QueueNode {
    NodeAnchor head=null;

    public void add(AnchorPane data){
        NodeAnchor newNode=new NodeAnchor(data);
        if(head==null){
            head=newNode;
        }
        else{
            NodeAnchor temp=head;
            while(temp.next!=null){
                temp=temp.next;
            }
            temp.next=newNode;
        }
    }

    public NodeAnchor getHead(){
        return head;
    }


}
