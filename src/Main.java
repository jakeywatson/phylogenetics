package com.company;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        int lengthofsequence = 10;
        double alphavalue = 2;
        int ratecategories = 3;
        double[] rates = new double[ratecategories];

        double[] startfrequencies = new double[4];
        startfrequencies[0] = 0;//A
        startfrequencies[1] = 0.5;//C
        startfrequencies[2] = 0.5;//G
        startfrequencies[3] = 0;//T

        double[] frequencies = new double[4];
        frequencies[0] = 0;//A
        frequencies[1] = 0.4;//C
        frequencies[2] = 0.4;//G
        frequencies[3] = 0;//T

        double transversionrate = 0.1;
        double transitionrate = 0.5;

        double dt = 0.000001 ;
        double runtime = 0.000007;
        int taxa = 10;
        int loopnumber = (int)(runtime/dt);
        List<double[][]> sequences = new ArrayList<>();
        sequences.add(ComplexInput.DNASEQ(lengthofsequence, alphavalue, ratecategories, rates, startfrequencies));

        double[] branchtimes = new double[taxa];
        branchtimes[0] = 0;
        branchtimes[1] = 0.001;//0.000001
        branchtimes[2] = 0.068501; //0.0685
        branchtimes[3] = 0.072601 ; //0.0041
        branchtimes[4] = 0.072602; //0.000001
        branchtimes[5] = 0.074602; //0.002
        branchtimes[6] = 0.074603; //0.000001
        branchtimes[7] = 0.082603; //0.0081
        branchtimes[8] = 0.085603; //0.003
        branchtimes[9] = 0.087503; //0.0019

        double[] runtimes = new double[taxa];
        runtimes[0] = 0.000001;//0.0786
        runtimes[1] = 0.1364;
        runtimes[2] = 0.0111;
        runtimes[3] = 0.0087;
        runtimes[4] = 0.0094;
        runtimes[5] = 0.01;
        runtimes[6] = 0.0105;
        runtimes[7] = 0.0007;
        runtimes[8] = 0.0019;
        runtimes[9] = 0.003;

        boolean[] evolvecheck = new boolean[taxa];
        Arrays.fill(evolvecheck, true);

        double t = 0;
        Random random = new Random();


        for (int j = 0; j < loopnumber; j++) {
            for (int i = 0; i < sequences.size(); i++) {
                if (MathsOperators.doubletimecheck(t, (runtimes[i] + branchtimes[i]))) {
                    evolvecheck[i] = false;
                }
                if (evolvecheck[i]){
                    double[][] seqhold = sequences.get(i);
                    ComplexInput.evolvesequence(seqhold, frequencies, transitionrate, transversionrate, random);
                    sequences.set(i, seqhold);
                }
                double[][] toPrint = sequences.get(i);
                MathsOperators.PrintOutput(toPrint, t);
            }
            MathsOperators.Brancher(branchtimes, sequences, t);
            t = t + dt;
        }
    }
}

//method to check rate category matrix
        /*
        for (int i =0; i<ratecategories;i++){
            double number = rates[i];
            System.out.printf(" %.7f \n" ,number);
        }
        */