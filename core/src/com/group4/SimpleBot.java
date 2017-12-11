package com.group4;

import static com.group4.Constants.rotations;

/**
 * Created by Tobias on 01/12/2017.
 */

public class SimpleBot extends GameLogic{
    private Pentomino pentomino;
    private int pentominoHeight=0;
    private int maxPentominoHeight=0;
    private int dropHeight;
    private int bestRotation;
    private int bestPosition;

    public SimpleBot (Pentomino pentomino){
        this.pentomino=pentomino;
    }

    public void playBot(){
        piece=pentomino.getPiece();
        int possibleRotations=rotations[piece[0]-1].length;
        for(int r=0;r<possibleRotations;r++){
            dropHeight=pentomino.getMinimalDropHeight();
            for(int i=1;i<piece.length;i+=2)
                pentominoHeight+=piece[i]+dropHeight;
            if(pentominoHeight>maxPentominoHeight){
                maxPentominoHeight=pentominoHeight;
                bestRotation=pentomino.getRotation();
                bestPosition=pentomino.getCol();
            }
            pentominoHeight=0;
            pentomino.removeAim();
            while(pentomino.movePentominoRight()){
                pentomino.aimDrop();
                pentomino.drawAim();
                dropHeight=pentomino.getMinimalDropHeight();
                for(int i=1;i<piece.length;i+=2)
                    pentominoHeight+=piece[i]+dropHeight;
                if(pentominoHeight>maxPentominoHeight){
                    maxPentominoHeight=pentominoHeight;
                    bestRotation=pentomino.getRotation();
                    bestPosition=pentomino.getCol();
                }
                pentominoHeight=0;
                pentomino.removeAim();}
            while(pentomino.movePentominoLeft()){
                pentomino.aimDrop();
                pentomino.drawAim();
                dropHeight=pentomino.getMinimalDropHeight();
                for(int i=1;i<piece.length;i+=2)
                    pentominoHeight+=piece[i]+dropHeight;
                if(pentominoHeight>maxPentominoHeight){
                    maxPentominoHeight=pentominoHeight;
                    bestRotation=pentomino.getRotation();
                    bestPosition=pentomino.getCol();
                }
                pentominoHeight=0;
                pentomino.removeAim();}
            pentomino.rotate();
            pentomino.aimDrop();
            pentomino.drawAim();
        }
        int r=pentomino.getRotation();
        while(r%possibleRotations!=bestRotation){
            pentomino.removeAim();
            pentomino.rotate();
            pentomino.aimDrop();
            pentomino.drawAim();
            r++;
        }
        int col=pentomino.getCol();
        while(col!=bestPosition){
            if(col>bestPosition){
                pentomino.removeAim();
                pentomino.movePentominoLeft();
                pentomino.aimDrop();
                pentomino.drawAim();
                col--;
            }
            if(col<bestPosition){
                pentomino.removeAim();
                pentomino.movePentominoRight();
                pentomino.aimDrop();
                pentomino.drawAim();
                col++;
            }
        }
    }
}
