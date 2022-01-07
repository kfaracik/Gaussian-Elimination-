package gauss;

import matrixChecker.Checker;
import org.ejml.simple.SimpleMatrix;
import other.FileManager;

import java.io.IOException;

public class GaussianElimination {

    private final FileManager fileManager;
    private final SimpleMatrix matrix;
    private final SimpleMatrix matrixA;
    private final double[][][] arrB;
    private final String inputFileName;
    private final String outputFileName;
    private final int iSize;
    private final int jSize;
    private final int kSize;
    private final int n;

    private final ThreadGauss[][] threadSetA;
    private final ThreadGauss[][][] threadSet;

    public GaussianElimination(String inputFileName, String outputFileName) {
        fileManager = new FileManager(outputFileName);
        matrix = fileManager.readMatrix(inputFileName);
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;

        // set size
        n = matrix.numRows();
        iSize = n - 1;
        jSize = n + 1;
        kSize = n;
        matrixA = new SimpleMatrix(n, n + 1);
        arrB = new double[n][n + 1][n];

        // init Threads A
        threadSetA = new ThreadGauss[n - 1][n];
        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                threadSetA[i][k] = new ThreadGauss(i, k);
                threadSetA[i][k].thread = createAThread(i, k);
            }
        }

        // init Threads B C
        threadSet = new ThreadGauss[iSize][jSize][kSize];
        for (int i = 0; i < iSize; i++) {
            for (int k = i + 1; k < kSize; k++) {
                for (int j = i; j < jSize; j++) {
                    threadSet[i][j][k] = new ThreadGauss(i, j, k);
                    threadSet[i][j][k].thread = createBCThread(i, j, k);
                }
            }
        }
    }

    public void solve() {
        startThreads();

        try {
            joinThreads();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        backwardSubstitution(matrix);
        fileManager.saveMatrix(matrix);
    }

    public void check() {
        String[] arg = {inputFileName, outputFileName};
        try {
            Checker.checkMatrix(arg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Thread createAThread(int i, int k) {
        return new Thread() {
            @Override
            public void run() {
                try {
                    checkE5(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (threadSetA[i][k].conditionA) {
                    A(i, k);
                    threadSetA[i][k].conditionA.notifyAll();
                    threadSetA[i][k].setFinished();
                }

                super.run();
            }
        };
    }

    private Thread createBCThread(int i, int j, int k) {
        return new Thread() {
            @Override
            public void run() {
                try {
                    checkE1(i, k);
                    checkE3(i, k);
                    makeBC(i, j, k);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                super.run();
            }
        };
    }

    private void checkE1(int i, int k) throws InterruptedException {
        if (!threadSetA[i][k].hasFinished()) {
            synchronized (threadSetA[i][k].conditionA) {
                threadSetA[i][k].conditionA.wait();
            }
        }
    }

    private void checkE3(int i, int k) throws InterruptedException {
        if (i == 0)
            return;

        for (int jCond = i + 1; jCond < jSize; jCond++)
            synchronized (threadSet[i - 1][jCond][k - 1].conditionC) {
                if (!threadSet[i - 1][jCond][k - 1].hasFinished())
                    threadSet[i - 1][jCond][k - 1].conditionC.wait();
            }
    }

    private void makeBC(int i, int j, int k) throws InterruptedException {
        synchronized (threadSet[i][j][k]) {
            B(i, j, k);
            checkE4(i, k);
            C(i, j, k);
            threadSet[i][j][k].setFinished();

            synchronized (threadSet[i][j][k].conditionC) {
                threadSet[i][j][k].conditionC.notifyAll();
            }
        }
    }

    private void checkE4(int i, int k) throws InterruptedException {
        if (i == 0)
            return;

        for (int lastJ = i + 1; lastJ < jSize; lastJ++) {
            if (!threadSet[i - 1][lastJ][k].hasFinished())
                synchronized (threadSet[i - 1][lastJ][k].conditionC) {
                    threadSet[i - 1][lastJ][k].conditionC.wait();
                }
        }
    }

    private void checkE5(int i) throws InterruptedException {
        if (i == 0)
            return;

        for (int lastK = i; lastK < kSize; lastK++) {
            synchronized (threadSet[i - 1][i][lastK].conditionC) {
                if (!threadSet[i - 1][i][lastK].hasFinished())
                    threadSet[i - 1][i][lastK].conditionC.wait();
            }
        }
    }

    private void startThreads() {
        for (int i = 0; i < n; i++)
            for (int k = i + 1; k < n; k++)
                threadSetA[i][k].thread.start();

        for (int i = 0; i < iSize; i++)
            for (int k = i + 1; k < kSize; k++)
                for (int j = i; j < jSize; j++)
                    threadSet[i][j][k].thread.start();
    }

    private void joinThreads() throws InterruptedException {
        for (int i = 0; i < n; i++)
            for (int k = i + 1; k < n; k++)
                threadSetA[i][k].thread.join();

        for (int i = 0; i < iSize; i++)
            for (int k = i + 1; k < kSize; k++)
                for (int j = i; j < jSize; j++)
                    threadSet[i][j][k].thread.join();
    }

    private void backwardSubstitution(SimpleMatrix matrix) {
        for (int i = n - 1; i >= 0; i--) {
            for (int k = i - 1; k >= 0; k--) {
                double factor = matrix.get(k, i) / matrix.get(i, i);
                matrix.set(k, i, matrix.get(k, i) - factor * matrix.get(i, i));
                matrix.set(k, n, matrix.get(k, n) - factor * matrix.get(i, n));
            }

            matrix.set(i, n, matrix.get(i, n) / matrix.get(i, i));
            matrix.set(i, i, matrix.get(i, i) / matrix.get(i, i));
        }
    }

    private void A(int i, int k) {
        double multiplier = matrix.get(k, i) / matrix.get(i, i);
        matrixA.set(k, i, multiplier);
    }

    private void B(int i, int j, int k) {
        double multiplied = matrix.get(i, j) * matrixA.get(k, i);
        arrB[i][j][k] = multiplied;
    }

    private void C(int i, int j, int k) {
        double diff = matrix.get(k, j) - arrB[i][j][k];
        matrix.set(k, j, diff);
    }
}
