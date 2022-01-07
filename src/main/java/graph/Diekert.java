package graph;

import other.FileManager;

import java.util.Objects;

public class Diekert {
    private StringBuilder diekertGraph;

    public String generateDiekert(int n) {
        StringBuilder diekertGraph = new StringBuilder("digraph G {\n");

        appendSimpleAB_BC(n, diekertGraph);
        appendDirectCB(n, diekertGraph);
        appendDirectCC(n, diekertGraph);
        appendDirectCA(n, diekertGraph);

        setFNFStyle(n, diekertGraph);
        diekertGraph.append("}");

        this.diekertGraph = diekertGraph;
        return diekertGraph.toString();
    }

    public void saveDiekerGraph(String fileName) {
        FileManager fileManager = new FileManager(fileName);
        fileManager.save(diekertGraph.toString());
    }

    private void appendSimpleAB_BC(int n, StringBuilder stringBuilder) {
        for (int i = 1; i < n; i++)
            for (int k = i + 1; k <= n; k++)
                for (int j = i; j <= n + 1; j++) {
                    stringBuilder.append(getProd("A", i, j, k, "B", i, j, k));
                    stringBuilder.append(getProd("B", i, j, k, "C", i, j, k));
                }
    }

    private void appendDirectCB(int n, StringBuilder stringBuilder) {
        for (int iC = 1; iC < n; iC++) {
            int iB = iC + 1;
            int kC = iB;

            for (int j = iB + 1; j <= n + 1; j++)
                for (int k_b = iB + 1; k_b <= n; k_b++)
                    stringBuilder.append(getProd("C", iC, j, kC, "B", iB, j, k_b));
        }
    }

    private void appendDirectCC(int n, StringBuilder stringBuilder) {
        for (int i1 = 1; i1 < n - 1; i1++) {
            int i2 = i1 + 1;

            for (int k = i2 + 1; k <= n; k++)
                for (int j = i2 + 1; j <= n + 1; j++)
                    stringBuilder.append(getProd("C", i1, j, k, "C", i2, j, k));
        }
    }

    private void appendDirectCA(int n, StringBuilder stringBuilder) {
        for (int iC = 1; iC < n - 1; iC++) {
            int iA = iC + 1;
            int j = iA;

            int kA1 = iA;
            for (int k_a = iA + 1; k_a <= n; k_a++)
                stringBuilder.append(getProd("C", iC, j, kA1, "A", iA, -1, k_a));

            for (int k_c = iA + 1; k_c <= n; k_c++) {
                int kA = k_c;
                stringBuilder.append(getProd("C", iC, j, k_c, "A", iA, -1, kA));
            }
        }
    }

    private void setFNFStyle(int n, StringBuilder stringBuilder) {
        ColorName colorName = new ColorName();
        for (int i = 1; i < n; i++) {
            String color_A = colorName.getColor();
            String color_B = colorName.getColor();
            String color_C = colorName.getColor();

            for (int k = i + 1; k <= n; k++) {
                stringBuilder.append("A_").append(i).append("_").append(k).append(" [label=<A<sub>").append(i).append(",").append(k).append("</sub>>, ").append("fillcolor=").append(color_A).append(", style=filled];\n");

                for (int j = i; j <= n + 1; j++)
                    stringBuilder.append("C_").append(i).append("_").append(j).append("_").append(k).append(" ").append("[label=<C<sub>").append(i).append(",").append(j).append(",").append(k).append("</sub>>, ").append("fillcolor=").append(color_B).append(", style=filled];").append("\n").append("B_").append(i).append("_").append(j).append("_").append(k).append(" ").append("[label=<B<sub>").append(i).append(",").append(j).append(",").append(k).append("</sub>>, ").append("fillcolor=").append(color_C).append(", style=filled];\n");
            }
        }
    }

    private String getProd(String label1, int i1, int j1, int k1, String label2, int i2, int j2, int k2) {
        String down = "_";
        String prodStr;
        if (Objects.equals(label1, "A")) {
            prodStr = "A" + down + i1 + down + k1 + " -> " + label2 + down + i2 + down + j2 + down + k2 + "\n";
        } else if (Objects.equals(label2, "A")) {
            prodStr = label1 + down + i1 + down + j1 + down + k1 + " -> " + "A" + down + i2 + down + k2 + "\n";
        } else {
            prodStr = label1 + down + i1 + down + j1 + down + k1 + " -> " + label2 + down + i2 + down + j2 + down + k2 + "\n";
        }
        return prodStr;
    }
}
