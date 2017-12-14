package com.group4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.group4.Constants.EVALTIME;
import static com.group4.Constants.MUTATION_CONSTANT;
import static com.group4.Constants.MUTATION_PROBAPILITY;
import static com.group4.Constants.OFFSPRING_PROPORTION;
import static com.group4.Constants.POOLSIZE;
import static com.group4.Constants.VARIABILITY;

/**
 * Created by Tobias on 06/12/2017.
 */

/**
 * Heart of the project.
 * A genetic bot that first initalizes a random population of weights each one assigned
 * to one of the six features we evaluate for a pentomino(heighestPoint,pentominoHeight,fullLines,connections,holes,touchEdge)
 * which are calculated in evalPosition();
 * evaluates performance for each and performs a tournament selection to decide over parents for offspring's.
 * Updates population when children reach a certain proportion of the population and starts over.
 * Getting better and better over time(a lot of time).
 */

public class GeneticBot extends GameLogic{
    private final TetrisGame game;
    private static double[][] weights =new double[2][6];
    private static ArrayList<RankedArray> weightsPool=new ArrayList<RankedArray>();
    private static ArrayList<RankedArray> weightsOffspringPool=new ArrayList<RankedArray>();
    private static double[][] bestWeights=new double[2][6];
    private static int gameCount;
    private static int chromosome;
    private static int updates;

    public GeneticBot (TetrisGame game){this.game=game;}

    /**makes the bot evolve over time.
     * selects from the set of weights and plays with each set a certain number of times(EVALTIME).
     * Goes through the whole pool of initialized weights and in the end, performes a tournament selection to produce
     * fit offsprings which will be integrated into the pool after a certain threshhold(OFFSPRINGPROPORTION) is reached.
     * Then updates and begins again. Until 15 updates have been performed.
     */
    public void evolve(){
        if(updates<=15){
            gameCount++;
            setFitness(chromosome, super.getScore());
            if (gameCount >= EVALTIME) {
                chromosome++;
                super.setWeights(getWeights(chromosome));
                gameCount = 0;
            }
            if (chromosome >= getPoolCount() - 1) {
                TournamentSelection();
                chromosome = 0;
            }
            if (getOffspringCount() >= getPoolCount() * OFFSPRING_PROPORTION) {
                updatePool();
                updates++;
                weightsOffspringPool=new ArrayList<RankedArray>();
            }
        }
    }

    public int getChromosome(){
        return chromosome;
    }

    public int getUpdates(){
        return updates;
    }
    public int getGameCount(){
        return gameCount;
    }

    /** initializes the pool of random weights assigned for each of the six features(heighestPoint,pentominoHeight,fullLines,connections,holes,touchEdge)
     */
    public void createPool(){
        for(int p=0;p<POOLSIZE;p++){
            weights=new double[2][6];
            for(int i=0;i<weights[0].length;i++)
                weights[0][i]=-10+Math.random()*21;
            RankedArray rankedArray=new RankedArray(weights);
            weightsPool.add(rankedArray);
        }
        super.setWeights(getWeights(chromosome));
    }

    /**sets the fitness for the weights to be able to compare*/
    public void setFitness(int chromosome,int score){
        if(weightsPool.get(chromosome).getScore()==0)
            weightsPool.get(chromosome).setScore((weightsPool.get(chromosome).getScore()+score)/2);
        else{
            weightsPool.get(chromosome).setScore(score);
        }
    }

    /**performes a tournament selection*/
    public void TournamentSelection(){
        ArrayList<RankedArray> selection=new ArrayList<RankedArray>();
        double[] best={-1,0};
        double[] secondBest={-1,0};
        ArrayList<RankedArray>weightsPoolCOPY=new ArrayList<RankedArray>(weightsPool);
        while(selection.size()<weightsPool.size()*VARIABILITY){
            int i=(int)Math.random()*weightsPoolCOPY.size();
            selection.add(weightsPoolCOPY.get(i));
            weightsPoolCOPY.remove(i);
        }
        for(int i=0;i<selection.size();i++){
            if (selection.get(i).getScore()>best[0]){
                best[0]=selection.get(i).getScore();
                best[1]=i;}
            else if(selection.get(i).getScore()>secondBest[0]){
                secondBest[0]=selection.get(i).getScore();
                secondBest[1]=i;}
        }
        double[][] offspring =new double[2][6];
        for(int i=0;i<offspring[0].length;i++)
            offspring[0][i]=best[0]/(best[0]+secondBest[0]+1)*weightsPool.get((int)best[1]).getWeight(i)+secondBest[0]/(best[0]+secondBest[0]+1)*weightsPool.get((int)secondBest[1]).getWeight(i);
        mutation(offspring);
        RankedArray array=new RankedArray(offspring);
        weightsOffspringPool.add(array);
    }

    /**mutates gene with a certain probability(MUTATION_PROBAPILITY).
     *
     * @param offspring
     * @return offspring
     */
    private double[][] mutation(double[][] offspring) {
        if(Math.random()<MUTATION_PROBAPILITY){
            int i=(int)(Math.random()*offspring[0].length);
            offspring[0][i]=offspring[0][i]+(Math.pow(-1,(int)(Math.random()*2))*MUTATION_CONSTANT);
            return offspring;
        }
        return offspring;
    }

    public int getOffspringCount(){
        return weightsOffspringPool.size();
    }

    public int getPoolCount(){
        return weightsPool.size();
    }

    public double[][] getWeights(int chromosome){
        return weightsPool.get(chromosome).getWeights();
    }

    /**updates the pool with the offsprings and deletes the worst performing candidates from the list.
     *
     */
    public void updatePool(){
        Collections.sort(weightsPool);
        weightsPool.subList(0, (int) (weightsPool.size()*0.1)).clear();
        weightsPool.addAll(weightsOffspringPool);
        for(RankedArray w:weightsPool)
            System.out.println(Arrays.deepToString(w.getWeights()));
    }

}
