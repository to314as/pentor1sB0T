package com.group4;

/**
 * Created by Tobias on 07/12/2017.
 */
public class RankedArray implements Comparable{
    double[][] array=new double[2][6];
    public RankedArray(double[][] array){this.array=array;}

    @Override
    public int compareTo(Object o) {
        RankedArray ro= (RankedArray) o;
        return ((Double)this.array[1][0]).compareTo(ro.getScore());
    }

    public void setScore(double score) {
        array[1][0]=score;
    }

    public double getScore() {
        return array[1][0];
    }

    public double getWeight(int i) {
        return array[0][i];
    }

    public double[][] getWeights() {
        return array;
    }
}
