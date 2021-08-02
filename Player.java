/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

/**
 *
 * @author Sam
 */
public class Player {

    boolean human;
    boolean p1;
    int limit;
    boolean alphaBeta;


    public Player() {
        human = true;
    }

    public Player(int depth, boolean max) {
        human = false;
        p1 = max;
        limit = depth;
        alphaBeta = false;
        
    }
    


}
