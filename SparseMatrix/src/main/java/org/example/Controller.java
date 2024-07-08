package org.example;


import org.example.matrix.*;
import org.example.matrixbuilders.*;
import org.example.operators.MatrixMultiplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    CoordinateBuilder reader = new CoordinateBuilder();
    List<CoordinateMatrix> matrix = new ArrayList<>();

    public void controller() {
        try {
            matrix = reader.matrixReader("src\\main\\resources\\bcsstk12.mtx");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CoordinateToCRS crsConverter = new CoordinateToCRS();
        CRSMatrix crsMatrix = crsConverter.convert(matrix);
        CoordinateToCCS ccsConverter = new CoordinateToCCS();
        CCSMatrix ccsMatrix = ccsConverter.convert(matrix);
        //System.out.println("CRS:" + crsMatrix.getRow_ptr() + " " + crsMatrix.getCol() + " " + crsMatrix.getValue());
        //System.out.println("CCS:" + ccsMatrix.getCol_ptr() + " " + ccsMatrix.getRow() + " " + ccsMatrix.getValue());
        SparseMatrix  sparseMatrix = benchmarkMultiplication(crsMatrix, ccsMatrix);
        //printSparseMatrix(sparseMatrix);
        //printDenseMatrix(sparseMatrix);
    }

    private SparseMatrix benchmarkMultiplication (CRSMatrix crsMatrix, CCSMatrix ccsMatrix){
        MatrixMultiplication matrixMultiplication = new MatrixMultiplication();
        double startTime = System.currentTimeMillis();
        SparseMatrix sparseMatrix = matrixMultiplication.multiply(crsMatrix, ccsMatrix);
        double endTime = System.currentTimeMillis();
        double elapsedTime = endTime - startTime;
        System.out.println(elapsedTime / 1000 + " seconds");
        return sparseMatrix;
    }

    private void printSparseMatrix(SparseMatrix sparseMatrix) {
        for (CoordinateMatrix coordinateMatrix : sparseMatrix.getCoordinates()) {
            System.out.println(coordinateMatrix.getRow() + " " + coordinateMatrix.getCol() + " " + coordinateMatrix.getValue());
        }
    }

    private void printDenseMatrix(SparseMatrix sparseMatrix) {
        SparseToDenseMatrix denseConverter = new SparseToDenseMatrix();
        DenseMatrix denseMatrix = denseConverter.convert(sparseMatrix);
        double[][] matrix = denseMatrix.getMatrix();
        for (int i = 0; i < denseMatrix.getSize(); i++) {
            for (int j = 0; j < denseMatrix.getSize(); j++) {
                System.out.printf(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
