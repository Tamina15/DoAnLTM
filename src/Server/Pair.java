/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import static Utils.Constant.NUMBER_AMOUNT;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author Tran Minh Nhat
 */
public class Pair {

    private ServerThread player1;
    private ServerThread player2;
    private boolean isFull = false;
    String line = null;
    BufferedReader is = null;
    PrintWriter os = null;

    public static int currentNumber;
    private static int currentIndex = 0;
    public static int[] array = new int[NUMBER_AMOUNT];

    public Pair() {
        
    }

    public Pair(ServerThread player1) {
        this.player1 = player1;
        isFull = false;
        RandomizeArray();
    }

    public Pair(ServerThread player1, ServerThread player2) {
        this.player1 = player1;
        this.player2 = player2;
        isFull = true;
        RandomizeArray();
    }

    public void AddClient(ServerThread t) {
        if (player1 != null) {
            player1 = t;
        } else if (player2 != null) {
            player2 = t;
            isFull = true;
        }

    }

    public static void RandomizeArray() {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        Random rand = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            int temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }
        NextNumber();
    }

    public static void NextNumber() {
        if (currentIndex + 1 <= NUMBER_AMOUNT) {
            currentNumber = array[currentIndex++];
        }
    }

    public boolean isFull() {
        return isFull;
    }

    public void StartGame() {
        String string = "Game:Start";

    }

    public static int getCurrentNumber() {
        return currentNumber;
    }

    public static void setCurrentNumber(int currentNumber) {
        Pair.currentNumber = currentNumber;
    }

    public ServerThread getPlayer1() {
        return player1;
    }

    public void setPlayer1(ServerThread player1) {
        this.player1 = player1;
    }

    public ServerThread getPlayer2() {
        return player2;
    }

    public void setPlayer2(ServerThread player2) {
        this.player2 = player2;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

}
