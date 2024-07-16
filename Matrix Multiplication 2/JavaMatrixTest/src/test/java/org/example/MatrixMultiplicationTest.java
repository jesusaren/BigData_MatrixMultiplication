package org.example;

import java.util.Random;

public class TestMatrixMultiply {
    public static double[][] generateMatrix(int size) {
        Random random = new Random();
        double[][] matrix = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextDouble();
            }
        }

        return matrix;
    }

    public static double runExperiment(int matrixSize, int numRuns) {
        double[][] A = generateMatrix(matrixSize);
        double[][] B = generateMatrix(matrixSize);

        long startTime, endTime;
        double totalTime = 0;

        for (int i = 0; i < numRuns; i++) {
            startTime = System.nanoTime();
            Matrix.multiply(A, B);
            endTime = System.nanoTime();
            totalTime += (endTime - startTime) / 1e9;
        }

        return totalTime / numRuns;
    }

    public static void main(String[] args) {
        int[] sizes = {100, 200, 300, 400, 500};
        int numRuns = 10;

        for (int size : sizes) {
            double avgTime = runExperiment(size, numRuns);
            System.out.printf("Average time for size %d: %.6f seconds%n", size, avgTime);
        }
    }
}