/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect4;

import java.sql.Time;
import java.util.Scanner;

/**
 *
 * @author Sam
 */
public class Connect4 {

    /**
     * @param args the command line arguments
     */
    public static String[][] board;
    public static boolean connect4, draw;
    public static boolean p1Turn = true;
    static Scanner input;

    String userInput;

    static Player[] players;
    int numChips;
    static int width;
    static int height;//, ab, mm;

    public static void main(String[] args) {
        // TODO code application logic here
        Connect4 game = new Connect4();
        game.setup();

//        for (int i = 1; i <= 20; i++) {
//            players[0].limit=i;
//            connect4 = false;
//            draw = false;
//            p1Turn = true;
//            board = new String[7][6];
        game.play();

//        }
//        System.out.println(ab);
//        System.out.println(mm);
    }

    public Connect4() {
        connect4 = false;
        draw = false;
        input = new Scanner(System.in);
        players = new Player[2];
        width = 0;
        height = 0;
//        ab = 0;
//        mm = 0;

    }

    public void setup() {
        int useLim = 0;
        while (!isNumeric(userInput)) {
            System.out.println("Choose Number of Chips To Win:\n(Default is 4)");
            //check if int
            userInput = input.nextLine();
        }
        numChips = Integer.parseInt(userInput);
        userInput = "z";
        while (!(width >= numChips || height >= numChips)) {
            while (!isNumeric(userInput)) {
                System.out.println("Choose the Width of Board:\n(Default is 7)");
                //}

                userInput = input.nextLine();
            }
            width = Integer.parseInt(userInput);

            userInput = "z";
            while (!isNumeric(userInput)) {
                System.out.println("Choose the Height of Board:\n(Default is 6)");
                userInput = input.nextLine();
            }
            height = Integer.parseInt(userInput);
        }
        userInput = "z";
        board = new String[width][height];
        //check if int
        for (int i = 0; i < 2; i++) {
            while (!(userInput.toLowerCase().equals("h") || userInput.toLowerCase().equals("c"))) {

                System.out.println("Select Player " + (i + 1) + " Type:\n(H for Human, C for Computer)");
                userInput = input.nextLine();
            }
            if (userInput.toLowerCase().equals("h")) {
                players[i] = new Player();
            } else if (userInput.toLowerCase().equals("c")) {
                //Select minmax algorithm
                while (!(userInput.toLowerCase().equals("dls") || userInput.toLowerCase().equals("dfs"))) {
                    System.out.println("Select Algorithm Type:\n(DFS for Depth-First Search, DLS for Depth-Limited Search)");
                    userInput = input.nextLine();
                }
                if (userInput.toLowerCase().equals("dfs")) {
                    players[i] = new Player(Integer.MAX_VALUE, i == 0);
                } else if (userInput.toLowerCase().equals("dls")) {
                    while (useLim < 1) {
                        while (!isNumeric(userInput)) {
                            System.out.println("Select Depth Limit: \n(must be greater than 0)");
                            userInput = input.nextLine();
                        }
                        useLim = Integer.parseInt(userInput);
                    }
                    players[i] = new Player(useLim, i == 0);
                    useLim = 0;
                }
                while (!(userInput.toLowerCase().equals("y") || userInput.toLowerCase().equals("n"))) {
                    System.out.println("Would You Like To Use AlphaBeta Pruning? (y for yes, n for no)");
                    userInput = input.nextLine();
                }
                if (userInput.toLowerCase().equals("y")) {
                    players[i].alphaBeta = true;
                }
            }
            userInput = "z";
        }
    }

    public void play() {
        print();
        int place = width + 1;
        while (!connect4 && !draw) {

            if (p1Turn) {
                System.out.print("Player 1's Turn. \n");
                if (players[0].human) {
                    while (place > width || place <= 0) {
                        while (!isNumeric(userInput)) {
                            System.out.print("Choose Column to Place Chip:\n");
                            userInput = input.nextLine();
                        }
                        place = Integer.parseInt(userInput);
                    }
                    placeChip(place, "X");
                    place = width + 1;
                    userInput = "z";
                } else {
                    Node root = new Node(true);
                    if (players[0].alphaBeta) {
//                        final long startTime = System.nanoTime();
                        alphaBeta(players[0].limit, root, Integer.MIN_VALUE, Integer.MAX_VALUE);
//                        final long endTime = System.nanoTime();
//                        System.out.println("Total execution time: " + (endTime - startTime));
                    } else {
//                        final long startTime = System.nanoTime();
                        miniMax(players[0].limit, root);
//                        final long endTime = System.nanoTime();
//                        System.out.println("Total execution time: " + (endTime - startTime));
                    }
                    System.out.print("Computer Player 1 Plays " + root.bestMove + " \n");
                    placeChip(root.bestMove, "X");
                }
            } else if (!p1Turn) {
                System.out.print("Player 2's Turn.\n");
                if (players[1].human) {
                    while (place > width || place <= 0) {
                        while (!isNumeric(userInput)) {
                            System.out.print("Choose Column to Place Chip:\n");
                            userInput = input.nextLine();
                        }
                        place = Integer.parseInt(userInput);
                    }
                    placeChip(place, "O");
                    place = width + 1;
                    userInput = "z";
                } else {
                    Node root2 = new Node(false);

                    if (players[1].alphaBeta) {
//                        final long startTime = System.nanoTime();
                        alphaBeta(players[1].limit, root2, Integer.MIN_VALUE, Integer.MAX_VALUE);
//                        final long endTime = System.nanoTime();
//                        System.out.println("Total execution time: " + (endTime - startTime));
                    } else {
//                        final long startTime = System.nanoTime();
                        miniMax(players[1].limit, root2);
//                        final long endTime = System.nanoTime();
//                        System.out.println("Total execution time: " + (endTime - startTime));
                    }

                    System.out.print("Computer Player 2 Plays " + root2.bestMove + " \n");
                    placeChip(root2.bestMove, "O");
                }
            }
            print();
            connect4 = checkWin();
            draw = checkDraw();
            p1Turn = !p1Turn;

        }
        if (!draw) {
            if (p1Turn) {
                System.out.println("Player 2 Wins!");
                //mm++;
            } else {
                System.out.println("Player 1 Wins!");
                //ab++;
            }
        } else {
            System.out.println("Draw!");
        }
    }

    public void placeChip(int column, String player) {
        int i = 0;

        while (i < height && board[column - 1][i] != null) {
            i++;
        }
        if (i >= height) {
            System.err.println("Column Full");
            p1Turn = !p1Turn;
        } else {
            board[column - 1][i] = player;
        }
    }

    public static void print() {

        for (int i = height - 1; i >= 0; i--) {
            System.out.print("|");
            for (int j = 0; j < width; j++) {
                if (board[j][i] == null) {
                    System.out.print("_");
                } else {
                    System.out.print(board[j][i]);
                }
                System.out.print("|");
            }
            System.out.print("\n");

        }
        for (int i = 1; i <= width; i++) {
            System.out.print("|" + i);
        }
        System.out.print("|\n");
    }

    public int alphaBeta(int depth, Node root, int alpha, int beta) {
        int score;
        //root.bestMove = 1;
        if (root.isP1()) {
            score = Integer.MIN_VALUE;
        } else {
            score = Integer.MAX_VALUE;
        }
        if (checkWin() || checkDraw()) {
            if (root.isP1()) {
                return score(root.isP1()) * (depth + 1);
            } else {
                return score(!root.isP1()) * (depth + 1);
            }
        } else if (depth == 0) {
//            System.out.println("----------");
//            print();
//            System.out.println("----------");
//            System.out.println(score(root.state, !root.isP1()));
            return score(root.isP1());

        } else {
            for (int i = 0; i < width; i++) {
                int j = (i + width / 2) % width + 1;
                if (board[j - 1][height - 1] == null) {

                    //System.out.println(i+" "+j);
                    placeChip(j, root.getString());
                    Node child = new Node(!root.isP1());

                    root.addChild(child);

                    int newScore = alphaBeta(depth - 1, child, alpha, beta);

                    reverseMove(j);
                    if (root.isP1()) {
                        if (newScore > score) {
                            score = newScore;
                            root.bestMove = j;
                            alpha = Integer.max(alpha, score);
                        }
                        if (alpha >= beta) {
                            return alpha;
                        }

                    } else {
                        if (newScore < score) {
                            score = newScore;
                            root.bestMove = j;
                            beta = Integer.min(beta, score);
                        }
                        if (beta <= alpha) {
                            return beta;
                        }

                    }
                }
            }

        }
        return score;

    }

    public int miniMax(int depth, Node root) {
        int score;
        //root.bestMove = 1;
        if (root.isP1()) {
            score = Integer.MIN_VALUE;
        } else {
            score = Integer.MAX_VALUE;
        }
        if (checkWin() || checkDraw()) {
            if (root.isP1()) {
                return score(root.isP1()) * (depth + 1);
            } else {
                return score(!root.isP1()) * (depth + 1);
            }
        } else if (depth == 0) {
//            System.out.println("----------");
//            print();
//            System.out.println("----------");
//            System.out.println(score(root.state, !root.isP1()));
            return score(root.isP1());

        } else {
            for (int i = 0; i < width; i++) {
                int j = (i + width / 2) % width + 1;
                if (board[j - 1][height - 1] == null) {

                    placeChip(j, root.getString());
                    Node child = new Node(!root.isP1());

                    root.addChild(child);

                    int newScore = miniMax(depth - 1, child);

                    reverseMove(j);
                    if (root.isP1()) {
                        if (newScore > score) {
                            score = newScore;
                            root.bestMove = j;

                        }

                    } else {
                        if (newScore < score) {
                            score = newScore;
                            root.bestMove = j;

                        }

                    }
                }
            }

        }
        return score;

    }

    public int score(boolean p1) {

        //if 4 in a row Integer max
        //if opponent 4 Integer min
        // 100*3 in arows + 2 in arows - opp3&2
        //implement block function
        int[] player = checkStreaks(p1);
        int[] opp = checkStreaks(!p1);
        int score = 0;
        for (int i = 0; i < numChips - 1; i++) {
            score += (player[i] - opp[i]) * (Math.pow(100, i));
        }
        // int score = player[0] + 100 * player[1] + 100000 * player[2] - opp[0] - 100 * opp[1] - 100000 * opp[2];
        return score;
    }

    public boolean checkWin() {
        int[] check = checkStreaks(p1Turn);
        int[] check2 = checkStreaks(!p1Turn);
        if (check[numChips - 2] > 0 || check2[numChips - 2] > 0) {
            return true;
        }
        return false;
    }

    public boolean checkDraw() {
        for (int i = 0; i < width; i++) {
            if (board[i][height - 1] == null) {
                return false;
            }
        }
        return true;
    }

    public int[] checkStreaks(boolean p1) {
        int[] score = new int[numChips - 1];
        String player;

        if (p1) {
            player = "X";

        } else {
            player = "O";

        }
        int tempScore = 0;
        int empty = 0;
        int fin = 0;

        // horizontal check
        for (int i = 0; i < height; i++) {
            tempScore = 0;
            for (int j = 0; j < width; j++) {
                if (board[j][i] == null) {
                    empty++;
                    fin = Integer.max(tempScore, fin);
                    if (j == width - 1) {
                        if (fin > 1 && (empty + fin) >= numChips) {

                            score[Integer.min(numChips - 2, fin - 2)]++;
                        }
                        tempScore = 0;
                        fin = 0;
                    }
                    tempScore = 0;
                } else if (board[j][i] == player) {

                    tempScore++;
                    fin = tempScore;
                    if (j == width - 1) {
                        if (fin > 1 && (empty + fin) >= numChips) {

                            score[Integer.min(numChips - 2, fin - 2)]++;
                        }
                        tempScore = 0;
                        fin = 0;
                    }
                } else {

                    if (fin > 1 && (empty + fin) >= numChips) {

                        score[Integer.min(numChips - 2, fin - 2)]++;
                    }
                    tempScore = 0;
                    empty = 0;
                    fin = 0;
                }
            }
        }
        tempScore = 0;
        //vertical check
        for (int i = 0; i < width; i++) {
            tempScore = 0;
            for (int j = 0; j < height; j++) {
                if (board[i][j] == player) {
                    tempScore++;
                    if (j == height - 1 && tempScore > 1 && (height - j) >= (numChips - tempScore)) {

                        score[Integer.min(numChips - 2, tempScore - 2)]++;
                    }
                } else if (board[i][j] == null) {
                    if (j == 0) {

                        break;
                    }
                    if (tempScore > 1 && (height - j) >= (numChips - tempScore)) {

                        score[Integer.min(numChips - 2, tempScore - 2)]++;
                    }
                    tempScore = 0;
                } else {
                    tempScore = 0;
                }
            }
        }
        tempScore = 0;
        //diagonal check only check if possible
        //ASCL2R
        //col1

        for (int j = 0; j < height - numChips + 1; j++) {
            tempScore = 0;
            empty = 0;
            fin = 0;
            int m = 0;
            int n = j;
            while (n < height && m < width) {
                if (board[m][n] == player) {
                    tempScore++;
                    fin = tempScore;
                } else if (board[m][n] == null) {
                    empty++;
                    if (m == width - 1 || n == height - 1) {
                        if (fin > 1 && (empty + fin) >= numChips) {

                            score[Integer.min(numChips - 2, fin - 2)]++;
                        }
                    }
//                    if (tempScore > 1) {//&& (height-j)>(numChips-tempScore)
//                        score[Integer.min(numChips - 2, tempScore - 2)]++;
//                    }
                    tempScore = 0;
                } else {
                    if (fin > 1 && (empty + fin) >= numChips) {

                        score[Integer.min(numChips - 2, fin - 2)]++;
                    }
                    tempScore = 0;
                    empty = 0;
                    fin = 0;
                }
                m++;
                n++;
            }
            if (tempScore > 1) {//&& (height-j)>(numChips-tempScore)
                score[Integer.min(numChips - 2, tempScore - 2)]++;
            }

        }
        //col

        for (int j = 1; j < width - numChips + 1; j++) {
            tempScore = 0;
            empty = 0;
            fin = 0;
            int m = j;
            int n = 0;
            while (n < height && m < width) {
                if (board[m][n] == player) {
                    tempScore++;
                    fin = tempScore;
                } else if (board[m][n] == null) {
//                    if (tempScore > 1) {//&& (height-j)>(numChips-tempScore)
//                        score[Integer.min(numChips - 2, tempScore - 2)]++;
//                    }
                    empty++;
                    if (m == width - 1 || n == height - 1) {
                        if (fin > 1 && (empty + fin) >= numChips) {

                            score[Integer.min(numChips - 2, fin - 2)]++;
                        }
                    }
                    tempScore = 0;
                } else {
                    if (fin > 1 && (empty + fin) >= numChips) {

                        score[Integer.min(numChips - 2, fin - 2)]++;
                    }
                    tempScore = 0;
                    empty = 0;
                    fin = 0;
                }
                m++;
                n++;
            }
            if (tempScore > 1) {//&& (height-j)>(numChips-tempScore)
                score[Integer.min(numChips - 2, tempScore - 2)]++;
            }
        }

        //ASCR2L
        for (int j = 0; j < height - numChips + 1; j++) {
            tempScore = 0;
            empty = 0;
            fin = 0;
            int m = width - 1;
            int n = j;
            while (n < height && m >= 0) {
                if (board[m][n] == player) {
                    tempScore++;
                    fin = tempScore;
                } else if (board[m][n] == null) {

                    empty++;
                    if (m == 0 || n == height - 1) {
                        if (fin > 1 && (empty + fin) >= numChips) {

                            score[Integer.min(numChips - 2, fin - 2)]++;
                        }
                    }
                    tempScore = 0;
                } else {
                    if (fin > 1 && (empty + fin) >= numChips) {

                        score[Integer.min(numChips - 2, fin - 2)]++;
                    }
                    tempScore = 0;
                    empty = 0;
                    fin = 0;
                }
                m--;//need to be at start?
                n++;
            }
            if (tempScore > 1) {//&& (height-j)>(numChips-tempScore)
                score[Integer.min(numChips - 2, tempScore - 2)]++;
            }

        }
        //col

        for (int j = width - 2; j > 2; j--) {
            tempScore = 0;
            empty = 0;
            fin = 0;
            int m = j;
            int n = 0;
            while (n < height && m >= 0) {
                if (board[m][n] == player) {
                    tempScore++;
                    fin = tempScore;
                } else if (board[m][n] == null) {

                    empty++;
                    if (m == 0 || n == height - 1) {
                        if (fin > 1 && (empty + fin) >= numChips) {

                            score[Integer.min(numChips - 2, fin - 2)]++;
                        }
                    }
                    tempScore = 0;
                } else {
                    if (fin > 1 && (empty + fin) >= numChips) {

                        score[Integer.min(numChips - 2, fin - 2)]++;
                    }
                    tempScore = 0;
                    empty = 0;
                    fin = 0;
                }
                m--;
                n++;
            }
            if (tempScore > 1) {
                score[Integer.min(numChips - 2, tempScore - 2)]++;
            }
        }

        ////////////////////////////////
        return score;
    }

    public void reverseMove(int i) {
        int j = height - 1;
        while (board[i - 1][j] == null) {
            j--;
        }
        board[i - 1][j] = null;
    }

    private String[][] cloneState(String[][] state) {
        String[][] clone = new String[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (state[i][j] == null) {
                    clone[i][j] = null;
                } else if (state[i][j] == "X") {
                    clone[i][j] = "X";
                } else if (state[i][j] == "O") {
                    clone[i][j] = "O";
                }
            }
        }
        return clone;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}
