/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author Tran Minh Nhat
 */
public class Constant {

    public static final int FPS = 60;
    public static final int UPS = 60;

    public static final int ORIGINAL_TILE_SIZE = 16;
    public static final int SCALE = 4;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    public static final int NUMBER_AMOUNT = 10;
    public static final int COLUMN = 20;
    public static final int ROW = 10;
    public static final int PANEL_WIDTH = TILE_SIZE * COLUMN;
    public static final int PANEL_HEIGHT = TILE_SIZE * ROW;

    public static final int START_TIME_BY_SECOND = 60;
    public static String REMAINING_TIME;

    public static final int PORT = 5555;
    public static final String ADDRESS = "localhost";

    public static boolean PAUSE = false;
    public static boolean COMPLETE = false;

}
