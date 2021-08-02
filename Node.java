/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sam
 */
public class Node {
    int numChips;
    boolean p1;
    int score;
    int bestMove;
    int move;
    ArrayList<Node> children;
    public String[][] state;

    public Node(boolean player1){
        //state = s;
        //move = mv;
        setP1(player1);
        children = new ArrayList<Node>();
    }
    
    public String getString(){
    if(p1){
    return "X";
    }
    else{
    return "O";
    }
    }
    
    public void addChild(Node child){
    children.add(child);
    }
    
    public int getNumChips() {
        return numChips;
    }

    public void setNumChips(int numChips) {
        this.numChips = numChips;
    }

    public boolean isP1() {
        return p1;
    }

    public void setP1(boolean p1) {
        this.p1 = p1;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }
    
    
}
