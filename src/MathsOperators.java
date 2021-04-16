package com.company;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class MathsOperators {
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static void ShuffleArray(int[] array) {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            if (index != i) {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

    public static double nextTime(double rate) {
        return (-1 * Math.log(1 - Math.random()) / rate);
    }

    public static double[] addElement(double[] initial, double value) {
        double[] extended = new double[initial.length + 1];
        for (int i = 0; i < initial.length; i++) {
            extended[i] = initial[i];
        }
        extended[initial.length - 1] = value;
        return extended;
    }
    //returns true if equal
    public static Boolean doubletimecheck(double t, double branchtime) {
        double diff = (t - branchtime);
        boolean difftest = Math.abs(diff) <= 0.000000001;
        return difftest;
    }

    public static void PrintOutput(double[][] sequence, double time) {
            System.out.printf("\nNew Mutation, Time: %.7f %d\n ", time, sequence.hashCode());
            for (int j = 0; j < sequence.length; j++) {
                String character = new String();
                if (sequence[j][0] == 1) {
                    character = "A";
                } else if (sequence[j][0] == 2) {
                    character = "C";
                } else if (sequence[j][0] == 3) {
                    character = "G";
                } else if (sequence[j][0] == 4) {
                    character = "T";
                }
                System.out.printf(character);
            }
        /*
        for (int j=0;j< sequence.length; j++ ){
            System.out.printf("\n%.2f", sequence[j][1]);
        }
        */
            System.out.printf("\nEnd Mutation \n");
        }

    public static void Brancher(double[] times, List<double[][]> sequences, double t) {
        boolean checker = false;
        for (int i = 0; i < times.length; i++) {
            checker = doubletimecheck(t, times[i]);
            //checker resets to false over all, needs to break loop.
            if (checker == true) {
                double[][] seq = sequences.get(sequences.size() - 1);
                double[][]seqholder = new double[seq.length][seq[0].length];
                for (int j=0; j< seq.length; j++) {
                    seqholder[j][0] = seq[j][0];
                    seqholder[j][1] = seq[j][1];
                }
                sequences.add(seqholder);
            }
        }
    }
    public static void SEQFINISH(double[] starttimes, double[] runtimes, List<double[][]> sequences, List<double[][]> finalsequences, double t) {
        for (int i = 0; i < sequences.size(); i++){
            if (MathsOperators.doubletimecheck(t, (runtimes[i] + starttimes[i]) ) ) {
                finalsequences.add(sequences.get(i));
            }
        }
    }
}
