import gauss.GaussianElimination;
import graph.Diekert;
import matrixChecker.Generator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.err.print("wrong amount of arguments");
            System.exit(1);
        }

        // generate Dickert graph in dot format
        Diekert diekert = new Diekert();
        diekert.generateDiekert(Integer.parseInt(args[0]));
        diekert.saveDiekerGraph("diekert_graph_ "+ args[0] +".dot");

        // generate matrix
        if (args[1].equals("true")) {
            String[] arg = {args[0], args[1], "generated_example.txt"};
            try {
                Generator.generateMatrix(arg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        // solve matrix
        GaussianElimination gaussianElimination = new GaussianElimination(args[2], args[3]);
        gaussianElimination.solve();
        gaussianElimination.check();
    }
}


