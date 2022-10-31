/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Tran Minh Nhat
 */
public class Server {

    public static int currentNumber;
    private static int currentIndex=0;
    
    public static int[] array = new int[100];

    
    public  static void RandomizeArray() {
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
        System.out.println(Arrays.toString(array));
    }
    public static void NextNumber(){
        currentNumber = array[currentIndex++];
    }

    public static void main(String args[]) {
        new Server().RandomizeArray();
        currentNumber = array[currentIndex];
    }
}
