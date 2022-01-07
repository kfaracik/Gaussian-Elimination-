package other;

import org.ejml.simple.SimpleMatrix;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private BufferedWriter bufferedWriter;

    public FileManager(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleMatrix readMatrix(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            int n = parseMatrixSize(br.readLine());
            SimpleMatrix matrix = new SimpleMatrix(n, n + 1);

            for (int r = 0; r <= n; r++) {
                List<Double> inputRow = parseMatrixRow(br.readLine(), n, r);

                for (int c = 0; c < n; c++) {
                    if (inputRow.get(c) == null)
                        throw new NumberFormatException("matrix in " + r + " line " + c + " position!");

                    if (r == n)
                        // add extra vector intercepts
                        matrix.set(c, n, inputRow.get(c));
                    else
                        // add normal row
                        matrix.set(r, c, inputRow.get(c));
                }
            }

            return matrix;
        } catch (IOException e) {
            System.err.println("Could not open input file " + fileName);
            e.printStackTrace();
            return null;
        }
    }

    private int parseMatrixSize(String line) {
        if (line == null)
            throw new NumberFormatException("Wrong input file format: matrix dimension have to be in first line.");

        if (Integer.parseInt(line) < 2)
            throw new NumberFormatException("Wrong input file format: matrix dimension have to be grater than 1.");

        return Integer.parseInt(line);
    }

    private List<Double> parseMatrixRow(String line, int n, int r) {
        if (line == null)
            throw new NumberFormatException("Wrong input file format");

        // save matrix line to list
        List<Double> inputRow = Arrays.stream(line.split(" "))
                .collect(Collectors.toList())
                .stream()
                .map(Double::valueOf)
                .collect(Collectors.toList());

        if (inputRow.size() < n)
            throw new NumberFormatException("In line " + (r + 1) + " not enough elements was given!");

        return inputRow;
    }

    public void saveMatrix(SimpleMatrix matrix) {
        int n = matrix.numRows();
        try {
            // save matrix size
            bufferedWriter.append(String.valueOf(n)).append('\n');

            // save matrix n x n
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++)
                    bufferedWriter.append(String.valueOf(matrix.get(r, c))).append(' ');

                bufferedWriter.append('\n');
            }

            // save vector intercepts
            for (int i = 0; i < n; i++)
                bufferedWriter.append(String.valueOf(matrix.get(i, n))).append(' ');

            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(String content) {
        try {
            bufferedWriter.append(content);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
