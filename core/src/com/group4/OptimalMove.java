package com.group4;

import static com.group4.Constants.rotations;

/**
 * Created by Tobias on 01/12/2017.
 */

public class OptimalMove extends GameLogic{
    private Pentomino pentomino;
    private double pentominoHeight=0;
    private int[] boardEvaluation=new int[5];
    private double maxEvalValue=0;
    private double evalValue;
    private int dropHeight;
    private int heightWeight=800;
    private int clearsWeight=1200;
    private int connectionsWeight=400;
    private int touchGroundWeight=500;
    private int holesWeight=-200;
    private int bestRotation;
    private int bestPosition;

    public OptimalMove(Pentomino pentomino){
        this.pentomino=pentomino;
    }

    public void playBot(){
        piece=pentomino.getPiece();
        int possibleRotations=rotations[piece[0]-1].length;
        for(int r=0;r<possibleRotations;r++){
            dropHeight=pentomino.getMinimalDropHeight();
            for(int i=1;i<piece.length;i+=2)
                pentominoHeight+=piece[i]+dropHeight;
            boardEvaluation=pentomino.evalPosition();
            evalValue=heightWeight*(pentominoHeight/(5*15))+clearsWeight*(boardEvaluation[0]/5)+connectionsWeight*(boardEvaluation[1]/10)+holesWeight*(boardEvaluation[2]/15)+touchGroundWeight*(boardEvaluation[3]/5);
            if(evalValue>maxEvalValue){
                maxEvalValue=evalValue;
                bestRotation=pentomino.getRotation();
                bestPosition=pentomino.getCol();
            }
            pentominoHeight=0;
            evalValue=0;
            pentomino.removeAim();
            while(pentomino.movePentominoRight()){
                pentomino.aimDrop();
                pentomino.drawAim();
                dropHeight=pentomino.getMinimalDropHeight();
                for(int i=1;i<piece.length;i+=2)
                    pentominoHeight+=piece[i]+dropHeight;
                boardEvaluation=pentomino.evalPosition();
                evalValue=heightWeight*(pentominoHeight/(5*15))+clearsWeight*(boardEvaluation[0]/5)+connectionsWeight*(boardEvaluation[1]/10)+holesWeight*(boardEvaluation[2]/15)+touchGroundWeight*(boardEvaluation[3]/5);
                if(evalValue>maxEvalValue){
                    maxEvalValue=evalValue;
                    bestRotation=pentomino.getRotation();
                    bestPosition=pentomino.getCol();
                }
                pentominoHeight=0;
                evalValue=0;
                pentomino.removeAim();}
            while(pentomino.movePentominoLeft()){
                pentomino.aimDrop();
                pentomino.drawAim();
                dropHeight=pentomino.getMinimalDropHeight();
                for(int i=1;i<piece.length;i+=2)
                    pentominoHeight+=piece[i]+dropHeight;
                boardEvaluation=pentomino.evalPosition();
                System.out.println(100*(boardEvaluation[0]/5)+(boardEvaluation[1]/10)-(boardEvaluation[2]/15));
                evalValue=heightWeight*(pentominoHeight/(5*15))+clearsWeight*(boardEvaluation[0]/5)+connectionsWeight*(boardEvaluation[1]/10)+holesWeight*(boardEvaluation[2]/15)+touchGroundWeight*(boardEvaluation[3]/5);
                if(evalValue>maxEvalValue){
                    maxEvalValue=evalValue;
                    bestRotation=pentomino.getRotation();
                    bestPosition=pentomino.getCol();
                }
                pentominoHeight=0;
                evalValue=0;
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
