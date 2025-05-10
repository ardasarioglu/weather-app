package com.example.demo;

public class Queue {
    WeatherNode head=null;

    public void addNode(WeatherNode newNode){
        if(head==null){head=newNode;
        }
        else{
            WeatherNode temp=head;
            while(temp.next!=null){
                temp=temp.next;
            }
            temp.next=newNode;
        }
    }
    public void clear(){head=null;}

    public WeatherNode getHead(){
        return head;
    }





}
