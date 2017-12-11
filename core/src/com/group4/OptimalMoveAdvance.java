package com.group4;

import static com.group4.Constants.rotations;

/**
 * Created by Tobias on 07/12/2017.
 */

public class OptimalMoveAdvance {
    private Pentomino pentomino;
    private int[] boardEvaluation=new int[6];
    private double maxEvalValue=0;
    private double evalValue;
    private double[][] weights =new double[2][6];
    private int bestRotation;
    private int bestPosition;
    private int[] piece;

    public OptimalMoveAdvance (Pentomino pentomino,double[][] weights){
        this.pentomino=pentomino;
        this.weights=weights;
    }
    public void calculate(){
        piece=pentomino.getPiece();
        int possibleRotations=rotations[piece[0]-1].length;
        for(int r=0;r<possibleRotations;r++){
            boardEvaluation=pentomino.evalPosition();
            for(int i=0;i<weights[0].length;i++)
                evalValue+=boardEvaluation[i]*weights[0][i];
            if(evalValue>maxEvalValue){
                maxEvalValue=evalValue;
                bestRotation=pentomino.getRotation();
                bestPosition=pentomino.getCol();
            }
            evalValue=0;
            pentomino.removeAim();
            while(pentomino.movePentominoRight()){
                pentomino.aimDrop();
                pentomino.drawAim();
                boardEvaluation=pentomino.evalPosition();
                for(int i=0;i<weights.length;i++)
                    evalValue+=boardEvaluation[i]*weights[0][i];
                if(evalValue>maxEvalValue){
                    maxEvalValue=evalValue;
                    bestRotation=pentomino.getRotation();
                    bestPosition=pentomino.getCol();
                }
                evalValue=0;
                pentomino.removeAim();}
            while(pentomino.movePentominoLeft()){
                pentomino.aimDrop();
                pentomino.drawAim();
                boardEvaluation=pentomino.evalPosition();
                for(int i=0;i<weights.length;i++)
                    evalValue+=boardEvaluation[i]*weights[0][i];
                if(evalValue>maxEvalValue){
                    maxEvalValue=evalValue;
                    bestRotation=pentomino.getRotation();
                    bestPosition=pentomino.getCol();
                };
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
