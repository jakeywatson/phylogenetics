package com.company;
import org.apache.commons.math3.distribution.GammaDistribution;
import java.util.Random;

public class ComplexInput {

    public static double[][] DNASEQ(int length, double alpha, int k, double[] rates, double[]frequencies) {
        //ensuring fractions of input nucleotides, randomly distributed
        int[] characters = new int[length];
        for (int i = 0; i<(characters.length*frequencies[0]); i++){
            characters[i] = 1;
        }
        for (int i = (int)(characters.length*frequencies[0]); i<(characters.length*(frequencies[1]+frequencies[0])); i++){
            characters[i] = 2;
        }
        for (int i = (int)(characters.length*(frequencies[1]+frequencies[0])); i< (characters.length*(frequencies[2]+frequencies[1]+frequencies[0])); i++){
            characters[i] = 3;
        }
        for (int i = (int)(characters.length*(frequencies[2]+frequencies[1]+frequencies[0])); i< characters.length*(frequencies[3]+ frequencies[2]+frequencies[1]+frequencies[0]); i++){
            characters[i] = 4;
        }

        MathsOperators.ShuffleArray(characters);

        double[][] sequence = new double[length][2];
        for (int i=0; i<length; i++){
            sequence[i][0] = characters[i];
        }

        GammaDistribution distribution = new GammaDistribution(alpha, 1 / alpha);
        double[] ratecategories = new double[k];
        //finding rates for each category, filling rate category matrix
        for (int i = 1; i < k+1; i++) { //check this section thoroughly with breakpointing
            double firstterm = 2*i - 1;
            double secondterm = 2*k;
            double probability = firstterm/secondterm;
            double medianrate = distribution.inverseCumulativeProbability(probability);
             ratecategories[i-1] = medianrate;
        }
        //finding cumulative rate, to normalize the rates
        double sumrate = 0;
        for (int i = 0; i < k; i++) {
            sumrate = sumrate + ratecategories[i];
        }
        for (int i = 0; i < k; i++) {
            ratecategories[i] = ratecategories[i] / sumrate;
        }
        for (int i = 0; i < k; i++) {
             rates[i] = ratecategories[i];
        }

        int codoncount = 0;
        categorising:
        {
            while (codoncount <= length) {
                for (int i = 0; i < k; i++) {
                    sequence[codoncount][1] = ratecategories[i];
                    codoncount = codoncount + 1;
                    if (codoncount == length){
                        break categorising;
                    }
                }
            }
        }
        return sequence;
    }

    public static void evolvesequence(double[][] sequence,double[] frequencies, double alpha, double beta, Random random) {

        //define 2-rate transition matrix
        double a = alpha;
        double b = beta;

        double FreqA = frequencies[0];
        double FreqC = frequencies[1];
        double FreqG = frequencies[2];
        double FreqT = frequencies[3];

        double[][] ratematrix = new double[4][4];

        ratematrix[0][0] = 0;       ratematrix[0][1] = b*FreqC; ratematrix[0][2] = a*FreqG; ratematrix[0][3] = b*FreqT;
        ratematrix[1][0] = b*FreqA; ratematrix[1][1] = 0;       ratematrix[1][2] = b*FreqG; ratematrix[1][3] = a*FreqT;
        ratematrix[2][0] = a*FreqA; ratematrix[2][1] = b*FreqC; ratematrix[2][2] = 0;       ratematrix[2][3] = b*FreqT;
        ratematrix[3][0] = b*FreqA; ratematrix[3][1] = a*FreqC; ratematrix[3][2] = b*FreqG; ratematrix[3][3] = 0;

        //iterate through every site, check for rate first

        for (int i = 0; i < sequence.length; i++) {
            double randomtest = random.nextDouble();
            if (randomtest > sequence[i][1]) {
                sequence[i][0] = sequence[i][0];
            }
            //if random number is less than the normalised rate, site will mutate
            if (randomtest < sequence[i][1]) {
                int x = ((int) sequence[i][0])-1 ;

                //find the probabilities of transition
                double probtoA = ratematrix[x][0];
                double probtoC = ratematrix[x][1];
                double probtoG = ratematrix[x][2];
                double probtoT = ratematrix[x][3];
                double totalprob = probtoA + probtoC + probtoG + probtoT;
                //normalise probabilities
                double toA = probtoA / totalprob;
                double toC = probtoC / totalprob;
                double toG = probtoG / totalprob;
                double toT = probtoT / totalprob;
                //generate ranges for each probability
                double minA = 0;
                double maxA = toA;
                double maxC = maxA + toC;
                double maxG = maxC + toG;
                double maxT = maxG + toT;
                //Generate random and compare to range;
                double testvalue = random.nextDouble();
                if (testvalue > minA && testvalue < maxA) {
                    sequence[i][0] = 1;
                } else if (testvalue > maxA && testvalue < maxC) {
                    sequence[i][0] = 2;
                } else if (testvalue > maxC && testvalue < maxG) {
                    sequence[i][0] = 3;
                } else if (testvalue > maxG && testvalue < maxT) {
                    sequence[i][0] = 4;
                }
            }
        }
    }
}







