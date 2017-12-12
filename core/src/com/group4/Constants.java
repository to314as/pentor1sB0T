package com.group4;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Tobias on 07/11/2017.
 */

public class Constants {
    public final static int ROWS=15;
    public final static int COLS=5;
    public final static int MARGIN=10;

    public final static int PADDING=100;
    public final static int WIDTH = COLS*100;
    public final static int SIDE = 750;
    public final static int TIMESPAN_NORMAL = 1000;
    public final static int TIMESPAN_FAST = 100;
    public final static int TIMESPAN_SUPERFAST = 1;
    public final static int POOLSIZE = 20;
    public final static int EVALTIME=10;
    public static final double VARIABILITY = 0.8;
    public static final double MUTATION_CONSTANT = 0.5;
    public static final double MUTATION_PROBAPILITY= 0.05;
    public static final double OFFSPRING_PROPORTION=0.1;

    public final static int[][][] rotations ={{
                { 1, 1,0,2,0,3,0,4,0 },  // I in its default orientation.
                { 1, 0,1,0,2,0,3,0,4 },  // Describes piece 1 (the "I" pentomino) in its horizontal orientation.
            },
            {
                { 2, 1,-1,1,0,1,1,2,0 } // X in its only orientation.
            },
            {
                { 3, 0,1,1,1,2,1,2,2 }, // Z in its default orientation
                { 3, 1,-2,1,-1,1,0,2,-2 } // Z 90 degrees rotated to the right
            },
            {
                { 4, 1,0,2,0,2,1,2,2 }, // V in its default orientation
                { 4, 0,1,0,2,1,0,2,0 }, // V 90 degrees rotated to the right
                { 4, 0,1,0,2,1,2,2,2 }, // V 180 degrees rotated
                { 4, 1,0,2,-2,2,-1,2,0 }, // V 90 degrees rotated to the left
            },
            {
                { 5, 0,1,0,2,1,1,2,1 }, // T in its default orientation
                { 5, 1,-2,1,-1,1,0,2,0 }, // T 90 degrees rotated to the right
                { 5, 1,0,2,-1,2,0,2,1 }, // T 180 degrees rotated
                { 5, 1,0,1,1,1,2,2,0 }, // T 90 degrees rotated to the left
            },
            {
                { 6, 1,0,1,1,2,1,2,2 }, // W in its default orientation
                { 6, 0,1,1,-1,1,0,2,-1 }, // W 90 degrees rotated to the right
                { 6, 0,1,1,1,1,2,2,2 }, // W 180 degrees rotated
                { 6, 1,-1,1,0,2,-2,2,-1 }, // W 90 degrees rotated to the left
            },
            {
                { 7, 0,2,1,0,1,1,1,2 }, // U in its default orientation
                { 7, 0,1,1,0,2,0,2,1 }, // U 90 degrees rotated to the right
                { 7, 0,1,0,2,1,0,1,2 }, // U 180 degrees rotated
                { 7, 0,1,1,1,2,0,2,1 }, // U 90 degrees rotated to the left
            },
            {
                { 8, 1,0,2,0,3,0,3,1 }, // L in its default orientation
                { 8, 0,1,0,2,0,3,1,0 }, // L 90 degrees rotated to the right
                { 8, 0,1,1,1,2,1,3,1 }, // L 180 degrees rotated
                { 8, 1,-3,1,-2,1,-1,1,0 }, // L 90 degrees rotated to the left
            },
            {
                { 9, 1,-1,1,0,2,-1,3,-1 }, // N in its default orientation
                { 9, 0,1,0,2,1,2,1,3 }, // N 90 degrees rotated to the right
                { 9, 1,0,2,-1,2,0,3,-1 }, // N 180 degrees rotated
                { 9, 0,1,1,1,1,2,1,3 }, // N 90 degrees rotated to the left
            },
            {
                { 10, 1,-1,1,0,2,0,3,0 }, // Y in its default orientation
                { 10, 1,-2,1,-1,1,0,1,1 }, // Y 90 degrees rotated to the right
                { 10, 1,0,2,0,2,1,3,0 }, // Y 180 degrees rotated
                { 10, 0,1,0,2,0,3,1,1 }, // Y 90 degrees rotated to the left
            },
            {
                { 11, 0,1,1,-1,1,0,2,0 }, // F in its default orientation
                { 11, 1,-1,1,0,1,1,2,1 }, // F 90 degrees rotated to the right
                { 11, 1,0,1,1,2,-1,2,0 }, // F 180 degrees rotated
                { 11, 1,0,1,1,1,2,2,1 }, // F 90 degrees rotated to the left
            },
            {
                { 12, 0,1,1,0,1,1,2,0 }, // P in its default orientation
                { 12, 0,1,0,2,1,1,1,2 }, // P 90 degrees rotated to the right
                { 12, 1,-1,1,0,2,-1,2,0 }, // P 180 degrees rotated
                { 12, 0,1,1,0,1,1,1,2 }, // P 90 degrees rotated to the left
            }
        };
public static final Color BACKGROUND_COLOR = Color.WHITE;
}

