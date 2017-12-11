package com.group4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;

import static com.group4.Constants.COLS;
import static com.group4.Constants.ROWS;
import static com.group4.GameScreen.game;

/**
 * Created by Tobias on 07/11/2017.
 */

public abstract class GameLogic {
    int[] piece=new int[9];
    private int maxRow=0;
    private int maxCol=0;
    private int minCol=0;
    private Pentomino pentomino1;
    private int r;
    private int p;
    private Pentomino pentomino2;
    private Pentomino pentomino;
    private boolean run=false;
    private OptimalMoveAdvance optimalMove;

    private static Pentomino[] pentos=new Pentomino[2];
    private static int highscore;
    private static int[][] listOfClumps = new int[ROWS][COLS];
    private static int clumpNumber;
    private static int fullLines;
    private static int[][] board= new int[ROWS][COLS];
    private static int totalPentominos;
    private static int totalGames;
    private static double[][] weights;
    private boolean pause;


    public GameLogic() {}

    /**
     * The method initialises the pentomino on the board.
     * @return boolean.
     */

    public boolean init() {
        if(pentos[0]==null){
            p=(int) (Math.random()*12);
            pentos[0]=pentomino1=new Pentomino(p,0);
            p=(int) (Math.random()*11);
            pentos[1]=pentomino2=new Pentomino(p,0);}
        else{
            pentos[0]=pentos[1];
            p=(int) (Math.random()*12);
            pentos[1]=pentomino1=new Pentomino(p,0);
        }
        pentomino=pentos[0];
        if(!pentomino.initPento())
            return false;
        aimDrop();
        board=pentomino.getBoard();
        totalPentominos++;
        OptimalMoveAdvance optimal=new OptimalMoveAdvance(pentomino,weights);
        optimal.calculate();
        Gdx.input.setInputProcessor(pentomino);
        return true;
    }

    public void setWeights(double[][] weights){
        this.weights=weights;
    }

    /**
     * Returns the total number of pentominos on the board.
     * @return int.
     */
    public int getTotalPentominos(){
        return totalPentominos;
    }

    /**
     * Getter to display the next Pentomino on the right side of the screen.
     * @return int[].
     */

    public int[] displayNext() {
        return pentos[1].getPiece();
    }
    /**
     * The method calculates the score for each fall of a pentomino.
     * @param "n".
     * @return nothing.
     */

    public void addScore(int n){
        highscore+=n*(n*100);
    }

    /** This method returns/displays the score.
     * @return int (highscore).
     */
    public int getScore(){
        return highscore;
    }

    /** This method resets the score to zero.
     * @return int (highscore).
     */
    public void resetScore(){
        this.highscore=0;
    }

    /** This method removes a pentomino from its previous position (to be shifted further down).
     * @return nothing.
     */

    public void remove() {
        pentomino.removePosition();
    }
    /** This method checks whether a given pentomino can be shifted down (if the spaces below it are unoccupied.)
     * @return true (boolean) if the piece can move down.
     */

    public boolean getRun(){
        return run;
    }
    /** This method shifts the pentomino down if it's possible to do so.
     * @param "run".
     * @ return nothing.
     */

    public void setRun(boolean run){
        this.run=run;
    }
    /**
     * This method resets the game when it's over or when the user chooses to do so.
     * @return nothing.
     */

    public void reset() {
        highscore=0;
        totalPentominos=0;
        listOfClumps = new int[ROWS][COLS];
        pentos=new Pentomino[2];
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.setScreen(new GameScreen(game));
    }

    /** This method predicts where the piece is gonna land after its fall.
     * @return nothing.
     */

    public void aimDrop() {
        pentomino.aimDrop();
        pentomino.drawAim();
    }

    /** This method makes the piece drop.
     * @return nothing.
     */

    public void drop() {
        pentomino.drop();
    }
    /** This method shifts the pentomino left.
     * @return nothing.
     */


    public void moveLeft() {
        pentomino.movePentominoLeft();
        aimDrop();
    }
    /** This method shifts the pentomino right.
     * @return nothing.
     */

    public void moveRight() {
        pentomino.movePentominoRight();
        aimDrop();
    }
    /** This method checks whether the pentomino does indeed fall down.
     * @return true(boolean) if the pentomino falls.
     */

    public boolean fall(){
        return pentomino.fall();
    }
    /** This method gets the board and displays it.
     * @return board(int[][])
     */

    public int[][] getBoard(){
        return board;
    }
    /** This method gets all the clumps, i.e, all the pentominoes which are stuck to each other without gaps.
     * @return the list of clumps(int[][]).
     */

    public int[][] getClumps(){
        return listOfClumps;
    }
    /**
     Removes completely filled horizontal lines of the board.
     @param : the number of cells of that row that is occupied by a pentomino
     (@param r: the current row of the board
     @param: the current column of the board)?
     */

    public void checkFullLines(){
        removeLines();
        if(fullLines>0){
            identifyClumps();
            //realisticFall();
            ArrayList<Integer> possibleClumps=new ArrayList<Integer>();
            for(int i=1;i<=clumpNumber;i++)
                possibleClumps.add(i);
            realisticFall(possibleClumps);
            addScore(fullLines);}
        if(fullLines>0)
            checkFullLines();
    }
    /** Deletes the rows which are filled so that the pentomino can fall down.
     * @return nothing.
     */

    public void removeLines(){
        int cellsFull;
        int r = ROWS-1; //lowest row of the board
        fullLines=0;
        do{
            cellsFull=0;
            for (int c=0; c<COLS; c++)
                if (board[r][c]>0)
                    cellsFull++;
            if (cellsFull==COLS){
                fullLines++;
                moveBoard(r);
                r++;
            }r--; //next check row above
        }while(cellsFull!=0 && r>=0); //break when a row is complete empty: no full row can come above
    }
    /**
     * Checks how each of the pentomino clumps can fall down.
     * @param possibleClumps
     * @return nothing.
     */

    public void realisticFall(ArrayList<Integer> possibleClumps) {
        if(possibleClumps.size()>0){
            ArrayList<Integer> newPossible= new ArrayList<Integer>();
            for(Integer i:possibleClumps) {
                newPossible.add(i);
                outerloop:
                for (int c = 0; c < COLS; c++)
                    for (int r = ROWS - 1; r >= 0; r--)
                        if (listOfClumps[r][c] == i)
                            if (r < ROWS - 1)
                                if (board[r + 1][c] > 0) {
                                    newPossible.remove(i);
                                    break outerloop;
                                } else {
                                    break;
                                }
                            else {
                                newPossible.remove(i);
                                break outerloop;
                            }
            }
            for(int r=ROWS-1;r>=0;r--)
                for (int c=0; c<COLS; c++)
                    if (listOfClumps[r][c]>0){
                        for(Integer i:newPossible)
                            if(listOfClumps[r][c]==i){
                                board[r+1][c]=board[r][c];
                                board[r][c]=0;
                                listOfClumps[r+1][c]=listOfClumps[r][c];
                                listOfClumps[r][c]=0;
                            }
                    }
            realisticFall(newPossible);
        }
    }
    /**
     * Identifies all of the pentomino clumps.
     * @return nothing.
     */

    public void identifyClumps() {
        clumpNumber=0;
        listOfClumps=new int[ROWS][COLS];
        for(int i=ROWS-1;i>=0;i--)
            for(int j=0;j<COLS;j++)
                if(board[i][j]>0)
                    if(!inClumps(i,j)){
                        ++clumpNumber;
                        identifyClump(j,i,clumpNumber);}
    }
    /**
     * Identifies each individual clump.
     * @param (col, rows, pentomino piece).
     * @return nothing.
     */

    private void identifyClump(int col,int row,int piece) {
        if(row>=0 && row<ROWS && col>=0 && col<COLS){
            // if (col,row) has already been checked, do nothing
            if (listOfClumps[row][col]!=0||board[row][col]<=0){
                return;}
            else {
                listOfClumps[row][col]=piece;
                identifyClump(col+1,row,piece);
                identifyClump(col-1,row,piece);
                identifyClump(col,row-1,piece);
                identifyClump(col,row+1,piece);
                return;
            }
        }
        else
            return;
    }
    /**
     * Checks whether a pentomino clump exists.
     * @param (i, j).
     * @return true(boolean) if there exists a pentomino clump.
     */

    private boolean inClumps(int i,int j) {
        if(listOfClumps[i][j]>0)
            return true;
        return false;
    }

    /**
     Moves all pentominoes in the board above the row to remove one line down.
     @param remove: the line that should be removed
     (@param r: the current row of the board
     @param: the current column of the board)?
     */
    public void moveBoard(int remove){
        for (int r=remove; r>=0; r--)
            for (int c=0; c<board[r].length; c++){
                if (r==0) //row 0 is not replaced by the row above (there is no row above), but is a new empty row
                    board[r][c]=0;
                else
                    board[r][c]=board[r-1][c]; //replace row with row above
            }
    }
    /**
     * Gets and returns the lines on the board which are full.
     * @return all the lines which are full(int).
     */

    public int getFullLines(){
        return fullLines;
    }
    /**
     * Converts an integer into a string.
     * @param x .
     * @return String.
     */

    public String toString(int x){
        return ""+x;
    }
    public void setPause(boolean b) {
        this.pause=b;
    }
    public boolean getPause(){
        return pause;
    }
}
