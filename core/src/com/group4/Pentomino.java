package com.group4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

import static com.group4.Constants.COLS;
import static com.group4.Constants.ROWS;
import static com.group4.Constants.rotations;

/**
 * Created by Tobias on 06/11/2017.
 */

public class Pentomino extends GameLogic implements InputProcessor {
    private int[] piece=new int[9];
    private int[] rotatedPiece=new int[9];
    private int maxRow=0;
    private int maxCol=0;
    private int minCol=0;
    private int[] maxRowPerCol=new int[10];
    private int[] maxColPerRowLeft=new int[5];
    private int[] maxColPerRowRight=new int[5];
    private int row;
    private int col;
    private int p;
    private int r;
    private int minimalDropHeight=Integer.MAX_VALUE;

    private static int[][] board= new int[ROWS][COLS];

    public Pentomino(int p,int r){
        this.p=p;
        this.r=r;
        this.piece=rotations[p][r];
    }

    public boolean initPento() {
        row=0;
        col=(int)(Math.random()*COLS);
        for(int i=1;i<8;i+=2){
            if(piece[i]>maxRowPerCol[piece[i+1]+5]) //if it is the lowest point of the pentomino in that column
                maxRowPerCol[piece[i+1]+5]=piece[i]; //store it at the according index
            if(piece[i+1]<maxColPerRowLeft[piece[i]]) //if it is the point farthest to the left in that row
                maxColPerRowLeft[piece[i]]=piece[i+1]; //store it at the according index
            if(piece[i+1]>maxColPerRowRight[piece[i]]) //if it is the point farthest to the right in that row
                maxColPerRowRight[piece[i]]=piece[i+1]; //store it at the according index
            if(piece[i]>maxRow)
                maxRow=piece[i];
            if(piece[i+1]>maxCol)
                maxCol=piece[i+1];
            if(piece[i+1]<minCol)
                minCol=piece[i+1];}
        while(maxCol+col>COLS-1)
            col--;
        while(minCol+col<0)
            col++;
        int[] temp=new int[maxCol-minCol+1]; //number of col of the pentomino shape
        System.arraycopy(maxRowPerCol,minCol+5,temp,0,(maxCol-minCol+1));
        maxRowPerCol=temp;
        temp=new int[maxRow+1]; //number of rows of the pentomino shape
        System.arraycopy(maxColPerRowLeft,0,temp,0,maxRow+1);
        maxColPerRowLeft=temp;
        temp=new int[maxRow+1]; //number of rows of the pentomino shape
        System.arraycopy(maxColPerRowRight,0,temp,0,maxRow+1);
        maxColPerRowRight=temp;
        if(checkOverlap()){
            resetPento();
            return false;}
        drawPentomino();
        return true;
    }

    public boolean checkOverlap(){
        for (int i = 1; i < 8; i += 2) {
            if (board[row+piece[i]][col+piece[i+1]] > 0)  // checks if one of the squares needed is already occupied
                return true;
        }
        return false;
    }

    public boolean fall(){
        if(row+1+maxRow<ROWS){
            for(int i=minCol+col;i<=maxCol+col;i++){
                if(board[row+1+maxRowPerCol[i-(minCol+col)]][i]>0)
                    return false;
            }
            removePosition();
            row++;
            drawPentomino();
            return true;}
        else
            return false;
    }

    public int[][] getBoard(){
        return board;
    }

    public void resetPento(){
        board=new int[ROWS][COLS];
        maxRow=0;
        maxCol=0;
        minCol=0;
        maxRowPerCol=new int[10];
        maxColPerRowLeft=new int[5];
        maxColPerRowRight=new int[5];
        minimalDropHeight=Integer.MAX_VALUE;
        piece=new int[9];
        rotatedPiece=new int[9];
        r=0;
    }

    public int[] getPiece(){
        return piece;
    }

    public int getCol(){
        return col;
    }
    public int getRotation(){
        return r;
    }

    public void drawPentomino() {
        board[row][col] = piece[0];
        for (int i = 1; i < 8; i += 2){
            board[row+piece[i]][col+piece[i+1]] = piece[0];
        }
    }

    public void drawAim() {
        if(board[minimalDropHeight][col]<=0)
            board[minimalDropHeight][col] = -1;
        for (int i = 1; i < 8; i += 2){
            if(board[piece[i]+minimalDropHeight][col+piece[i+1]]<=0)
                board[piece[i]+minimalDropHeight][col+piece[i+1]] = -1;
        }
    }

    public void removeAim() {
        if(board[minimalDropHeight][col]<=0)
            board[minimalDropHeight][col] = 0;
        for (int i = 1; i < 8; i += 2){
            if(board[piece[i]+minimalDropHeight][col+piece[i+1]]<=0)
                board[piece[i]+minimalDropHeight][col+piece[i+1]] = 0;
        }
        minimalDropHeight=Integer.MAX_VALUE;
    }

    public void removePosition(){
        board[row][col] = 0;
        for (int i = 1; i < 8; i += 2){
            board[row+piece[i]][col+piece[i+1]] = 0;}
    }
    public void rotate(){
        rotatedPiece=rotations[p][(r+1)%rotations[p].length];
        int maxRowR=0;
        int maxColR=0;
        int minColR=0;
        int colR=col;
        int[] maxRowPerColR=new int[10];
        int[] maxColPerRowLeftR=new int[5];
        int[] maxColPerRowRightR=new int[5];
        for(int i=1;i<8;i+=2){
            if(rotatedPiece[i]>maxRowPerColR[rotatedPiece[i+1]+5]) //if it is the lowest point of the pentomino in that column
                maxRowPerColR[rotatedPiece[i+1]+5]=rotatedPiece[i]; //store it at the according index
            if(rotatedPiece[i+1]<maxColPerRowLeftR[rotatedPiece[i]]) //if it is the point farthest to the left in that row
                maxColPerRowLeftR[rotatedPiece[i]]=rotatedPiece[i+1]; //store it at the according index
            if(rotatedPiece[i+1]>maxColPerRowRightR[rotatedPiece[i]]) //if it is the point farthest to the right in that row
                maxColPerRowRightR[rotatedPiece[i]]=rotatedPiece[i+1]; //store it at the according index
            if(rotatedPiece[i]>maxRowR)
                maxRowR=rotatedPiece[i];
            if(rotatedPiece[i+1]>maxColR)
                maxColR=rotatedPiece[i+1];
            if(rotatedPiece[i+1]<minColR)
                minColR=rotatedPiece[i+1];}
        while(maxColR+colR>COLS-1)
            colR--;
        while(minColR+colR<0)
            colR++;
        int[] temp=new int[maxColR-minColR+1]; //number of col of the pentomino shape
        System.arraycopy(maxRowPerColR,minColR+5,temp,0,(maxColR-minColR+1));
        maxRowPerColR=temp;
        removePosition();
        for(int i=1;i<rotatedPiece.length;i+=2)
            if(row+rotatedPiece[i]>=ROWS||board[row+rotatedPiece[i]][colR+rotatedPiece[i+1]]>0){
                rotatedPiece=rotations[p][(r)%rotations[p].length];
                drawPentomino();
                return;}
        this.piece=rotations[p][(++r)%rotations[p].length];
        maxRow=0;
        maxCol=0;
        minCol=0;
        maxRowPerCol=new int[10];
        maxColPerRowLeft=new int[5];
        maxColPerRowRight=new int[5];
        for(int i=1;i<8;i+=2){
            if(rotatedPiece[i]>maxRowPerCol[rotatedPiece[i+1]+5]) //if it is the lowest point of the pentomino in that column
                maxRowPerCol[rotatedPiece[i+1]+5]=rotatedPiece[i]; //store it at the according index
            if(rotatedPiece[i+1]<maxColPerRowLeft[rotatedPiece[i]]) //if it is the point farthest to the left in that row
                maxColPerRowLeft[rotatedPiece[i]]=rotatedPiece[i+1]; //store it at the according index
            if(rotatedPiece[i+1]>maxColPerRowRight[rotatedPiece[i]]) //if it is the point farthest to the right in that row
                maxColPerRowRight[rotatedPiece[i]]=rotatedPiece[i+1]; //store it at the according index
            if(rotatedPiece[i]>maxRow)
                maxRow=rotatedPiece[i];
            if(rotatedPiece[i+1]>maxCol)
                maxCol=rotatedPiece[i+1];
            if(rotatedPiece[i+1]<minCol)
                minCol=rotatedPiece[i+1];}
        while(maxCol+col>COLS-1)
            col--;
        while(minCol+col<0)
            col++;
        temp=new int[maxCol-minCol+1]; //number of col of the pentomino shape
        System.arraycopy(maxRowPerCol,minCol+5,temp,0,(maxCol-minCol+1));
        maxRowPerCol=temp;
        drawPentomino();
    }

    public  boolean checkOutOfBoard(){
        for (int i = 1; i < 8; i += 2) {
            // i is the row, i+1 is the column
            if (row+piece[i] < 0 || //if it is above the board
                    row+piece[i] >= board.length || //if it is below the board
                    col+piece[i+1] < 0 || //if it is to the left of the board
                    col+piece[i+1] >= board[row].length) //if it is to the right of the board
                return true; //it is out of the board
        }
        return false; //it is not out of the board
    }


    public boolean movePentominoLeft(){
        if (row >= ROWS || //if it is below the board
                col-1+minCol< 0) //if it is to the left of the board
            return false;
        removePosition();
        for(int i=1;i<piece.length;i+=2)
            if(board[piece[i]+row][piece[i+1]-1+col]>0){
                drawPentomino();
                return false;}
        col--;
        drawPentomino();
        return true;
    }

    public boolean movePentominoRight(){
        if (row >= ROWS || //if it is below the board
                col+1+maxCol >= COLS) //if it is to the right of the board
            return false;
        removePosition();
        for(int i=1;i<piece.length;i+=2)
            if(board[piece[i]+row][piece[i+1]+1+col]>0){
                drawPentomino();
                return false;}
        col++;
        drawPentomino();
        return true;
    }

    public void aimDrop(){
        for(int i=minCol+col;i<=maxCol+col;i++){
            for(int j=row+maxRowPerCol[i-(minCol+col)]+1;j<ROWS;j++)
                if(board[j][i]>0)
                    if(j-maxRowPerCol[i-(minCol+col)]-1<minimalDropHeight)
                        minimalDropHeight=j-maxRowPerCol[i-(minCol+col)]-1;
            if(ROWS-1-maxRowPerCol[i-(minCol+col)]<minimalDropHeight)
                minimalDropHeight=ROWS-1-maxRowPerCol[i-(minCol+col)];
        }
    }

    public void drop(){
        row=minimalDropHeight;
        drawPentomino();
    }

    public int getMinimalDropHeight(){
        return minimalDropHeight;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!super.getPause()) {
            if (keycode == Input.Keys.UP) {
                removeAim();
                rotate();
                aimDrop();
                drawAim();
            }
            if (keycode == Input.Keys.LEFT) {
                removeAim();
                movePentominoLeft();
                aimDrop();
                drawAim();
            }
            if (keycode == Input.Keys.RIGHT) {
                removeAim();
                movePentominoRight();
                aimDrop();
                drawAim();
            }
            if (keycode == Input.Keys.SPACE) {
                removePosition();
                drop();
            }
            if (keycode == Input.Keys.R) {
                resetPento();
                super.reset();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(!super.getPause()){
            if(screenX>6*Gdx.graphics.getWidth()/10&&screenX<(6*Gdx.graphics.getWidth()/10+Gdx.graphics.getWidth()/4)&&screenY<2*Gdx.graphics.getHeight()/3&&screenY>(2*Gdx.graphics.getHeight()/3-Gdx.graphics.getHeight()/10)){
                resetPento();
                super.reset();
            }
            else if(screenX>6*Gdx.graphics.getWidth()/10&&screenX<(6*Gdx.graphics.getWidth()/10+Gdx.graphics.getWidth()/4)&&screenY<5*Gdx.graphics.getHeight()/6&&screenY>(5*Gdx.graphics.getHeight()/6-Gdx.graphics.getHeight()/10)){
                super.setPause(true);
            }
            else if(screenX<Gdx.graphics.getWidth()/2 && screenY>Gdx.graphics.getHeight()/4 && screenY<16*Gdx.graphics.getHeight()/17){
                removeAim();
                movePentominoLeft();
                aimDrop();
                drawAim();
            }
            else if(screenX>=Gdx.graphics.getWidth()/2 && screenY>Gdx.graphics.getHeight()/4 && screenY<16*Gdx.graphics.getHeight()/17){
                removeAim();
                movePentominoRight();
                aimDrop();
                drawAim();
            }
            if(screenY<Gdx.graphics.getHeight()/4){
                removeAim();
                rotate();
                aimDrop();
                drawAim();
            }
            if(screenY>16*Gdx.graphics.getHeight()/17){
                removePosition();
                drop();
            }
            return false;
        }
        else {
            if(screenX>6*Gdx.graphics.getWidth()/10&&screenX<(6*Gdx.graphics.getWidth()/10+Gdx.graphics.getWidth()/4)&&screenY<2*Gdx.graphics.getHeight()/3&&screenY>(2*Gdx.graphics.getHeight()/3-Gdx.graphics.getHeight()/10)){
                resetPento();
                super.setPause(false);
                super.reset();
            }
            else if(screenX>6*Gdx.graphics.getWidth()/10&&screenX<(6*Gdx.graphics.getWidth()/10+Gdx.graphics.getWidth()/4)&&screenY<5*Gdx.graphics.getHeight()/6&&screenY>(5*Gdx.graphics.getHeight()/6-Gdx.graphics.getHeight()/10)){
                super.setPause(false);
            }
            return false;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public int[] evalPosition(){
        int cellsFull;
        int fullLines=0;
        int connections=0;
        int holes=0;
        int heighestPoint=14;
        int touchEdge=0;
        int pentominoHeight=0;
        int currentRow=-1;
        for(int i=1;i<piece.length;i+=2){
            pentominoHeight+=piece[i]+minimalDropHeight;
            if(piece[i]+minimalDropHeight<heighestPoint)
                heighestPoint=piece[i]+minimalDropHeight;
            if(piece[i]+minimalDropHeight!=currentRow) {
                currentRow = piece[i] + minimalDropHeight;
                cellsFull = 0;
                for (int c = 0; c < COLS; c++)
                    if (board[currentRow][c] != 0) {
                        cellsFull++;
                        if (board[currentRow][c] == -1) {
                            if (c + 1 < COLS &&board[currentRow][c + 1] > 0)
                                connections++;
                            if (c - 1 >= 0 && board[currentRow][c - 1] > 0)
                                connections++;
                            if (currentRow + 1<ROWS&&board[currentRow + 1][c] == 0 || currentRow + 2<ROWS&&board[currentRow + 1][c]==-1&&board[currentRow + 2][c] == 0)
                                holes++;
                            if(currentRow==ROWS-1||c==0||c==COLS-1){
                                touchEdge++;
                            }
                        }
                    } else {
                        if (currentRow - 1>0&&board[currentRow - 1][c] != 0 || currentRow - 2>0&&board[currentRow - 2][c] != 0 || currentRow - 3>0&&board[currentRow - 3][c] != 0)
                            holes++;
                    }
                if (cellsFull == COLS) {
                    fullLines++;
                }
            }
        }
        int[] evalValues={heighestPoint,pentominoHeight,fullLines,connections,holes,touchEdge}; //values to evaluate Position of the Pentomino for the bot.
        return evalValues;
    }

}
