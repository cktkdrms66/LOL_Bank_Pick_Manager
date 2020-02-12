package com.example.lol_ban_pick_manager;

import android.app.Application;

import java.util.ArrayList;

public class Tree {
    public Node[] roots = new Node[ApplicationClass.maxNodeNum];
    private int rootNum = 0;

    public static class Node{
        BanPickActivity.PickClass data;
        int index;
        int childIndex;
        boolean isRoot = false;
        Node parent = null;
        Node[] child = new Node[ApplicationClass.maxNodeNum];
        private int childNum = 0;

        public Node(BanPickActivity.PickClass champion){
            data = champion;
            index = 0;
            childIndex = 0;
            isRoot = true;
            for(int i = 0; i < ApplicationClass.maxNodeNum; i++){
                child[i] = null;
            }
        }
        public Node(BanPickActivity.PickClass champion, int index, int childIndex){
            data = champion;
            this.index = index;
            this.childIndex = childIndex;
            for(int i = 0; i < ApplicationClass.maxNodeNum; i++){
                child[i] = null;
            }
        }
        public void add(Node node){
            node.parent = this;
            child[childNum] = node;
            childNum++;
        }
        public void remove(int index){
            child[index] = null;
            for(int i = index; i < ApplicationClass.maxNodeNum-1; i++){
                child[i] = child[i+1];
            }
        }

        public boolean isOktoInsertNode(){
            if(childNum == ApplicationClass.maxNodeNum){
                return false;
            }
            return true;
        }
        public int getChildNum(){
            return childNum;
        }
    }

    public boolean isOktoInsertRoot(){
        if(rootNum == ApplicationClass.maxNodeNum){
            return false;
        }
        return true;
    }
    public void setRoot(Node node) {
        node.isRoot =true;
        roots[rootNum] = node;
        rootNum++;
    }

    public int getRootNum() {
        return rootNum;
    }

    public Node getRoot(int index){
        return roots[index];
    }
    public static Node makeNode(BanPickActivity.PickClass pickClass, int index, int childIndex){
        return new Node(pickClass, index, childIndex);
    }
    public static Node makeRoot(BanPickActivity.PickClass pickClass){
        return new Node(pickClass);
    }

}
