<h1 align="center"> Multi-threaded gauss elimination method </h1> <br>

## Table of Contents
* [Usage examples](#usage-examples)
* [Technologies](#technologies)
* [Range of functionalities](#range-of-functionalities)
* [Startup](#startup)

## Usage examples
```
args: 4 false input.txt output.txt
```

input. txt
```
4
2 1 -1 2
4 5 -3 6
-2 5 -2 6
4 11 -1 8
5 9 4 2
```

output.txt
```
4
1.0 0.0 0.0 0.0 
0.0 1.0 0.0 0.0 
0.0 0.0 1.0 0.0 
0.0 0.0 0.0 1.0 
0.857142857142857 -2.142857142857143 0.14285714285714235 2.7857142857142856
```

compare the results
```
[expected]:      0.8571428571428568 -2.142857142857143 0.1428571428571416 2.785714285714285
[calculated]:    0.857142857142857 -2.142857142857143 0.14285714285714235 2.7857142857142856
```

The generated diekert graph can be pasted here  [Graphiz](https://dreampuf.github.io/GraphvizOnline/#digraph%20G%20%7B%0D%0AA_1_2%20-%3E%20B_1_1_2%0D%0AB_1_1_2%20-%3E%20C_1_1_2%0D%0AA_1_2%20-%3E%20B_1_2_2%0D%0AB_1_2_2%20-%3E%20C_1_2_2%0D%0AA_1_2%20-%3E%20B_1_3_2%0D%0AB_1_3_2%20-%3E%20C_1_3_2%0D%0AA_1_2%20-%3E%20B_1_4_2%0D%0AB_1_4_2%20-%3E%20C_1_4_2%0D%0AA_1_3%20-%3E%20B_1_1_3%0D%0AB_1_1_3%20-%3E%20C_1_1_3%0D%0AA_1_3%20-%3E%20B_1_2_3%0D%0AB_1_2_3%20-%3E%20C_1_2_3%0D%0AA_1_3%20-%3E%20B_1_3_3%0D%0AB_1_3_3%20-%3E%20C_1_3_3%0D%0AA_1_3%20-%3E%20B_1_4_3%0D%0AB_1_4_3%20-%3E%20C_1_4_3%0D%0AA_2_3%20-%3E%20B_2_2_3%0D%0AB_2_2_3%20-%3E%20C_2_2_3%0D%0AA_2_3%20-%3E%20B_2_3_3%0D%0AB_2_3_3%20-%3E%20C_2_3_3%0D%0AA_2_3%20-%3E%20B_2_4_3%0D%0AB_2_4_3%20-%3E%20C_2_4_3%0D%0AC_1_3_2%20-%3E%20B_2_3_3%0D%0AC_1_4_2%20-%3E%20B_2_4_3%0D%0AC_1_3_3%20-%3E%20C_2_3_3%0D%0AC_1_4_3%20-%3E%20C_2_4_3%0D%0AC_1_2_2%20-%3E%20A_2_3%0D%0AC_1_2_3%20-%3E%20A_2_3%0D%0AA_1_2%20%5Blabel%3D%3CA%3Csub%3E1%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse2%2C%20style%3Dfilled%5D%3B%0D%0AC_1_1_2%20%5Blabel%3D%3CC%3Csub%3E1%2C1%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_1_2%20%5Blabel%3D%3CB%3Csub%3E1%2C1%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_2_2%20%5Blabel%3D%3CC%3Csub%3E1%2C2%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_2_2%20%5Blabel%3D%3CB%3Csub%3E1%2C2%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_3_2%20%5Blabel%3D%3CC%3Csub%3E1%2C3%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_3_2%20%5Blabel%3D%3CB%3Csub%3E1%2C3%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_4_2%20%5Blabel%3D%3CC%3Csub%3E1%2C4%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_4_2%20%5Blabel%3D%3CB%3Csub%3E1%2C4%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AA_1_3%20%5Blabel%3D%3CA%3Csub%3E1%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse2%2C%20style%3Dfilled%5D%3B%0D%0AC_1_1_3%20%5Blabel%3D%3CC%3Csub%3E1%2C1%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_1_3%20%5Blabel%3D%3CB%3Csub%3E1%2C1%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_2_3%20%5Blabel%3D%3CC%3Csub%3E1%2C2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_2_3%20%5Blabel%3D%3CB%3Csub%3E1%2C2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_3_3%20%5Blabel%3D%3CC%3Csub%3E1%2C3%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_3_3%20%5Blabel%3D%3CB%3Csub%3E1%2C3%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_4_3%20%5Blabel%3D%3CC%3Csub%3E1%2C4%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_4_3%20%5Blabel%3D%3CB%3Csub%3E1%2C4%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AA_2_3%20%5Blabel%3D%3CA%3Csub%3E2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddodgerblue4%2C%20style%3Dfilled%5D%3B%0D%0AC_2_2_3%20%5Blabel%3D%3CC%3Csub%3E2%2C2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddodgerblue2%2C%20style%3Dfilled%5D%3B%0D%0AB_2_2_3%20%5Blabel%3D%3CB%3Csub%3E2%2C2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dgold4%2C%20style%3Dfilled%5D%3B%0D%0AC_2_3_3%20%5Blabel%3D%3CC%3Csub%3E2%2C3%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddodgerblue2%2C%20style%3Dfilled%5D%3B%0D%0AB_2_3_3%20%5Blabel%3D%3CB%3Csub%3E2%2C3%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dgold4%2C%20style%3Dfilled%5D%3B%0D%0AC_2_4_3%20%5Blabel%3D%3CC%3Csub%3E2%2C4%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddodgerblue2%2C%20style%3Dfilled%5D%3B%0D%0AB_2_4_3%20%5Blabel%3D%3CB%3Csub%3E2%2C4%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dgold4%2C%20style%3Dfilled%5D%3B%0D%0A%7D)
to see Foata Noraml Form of threads (each color is different independent set of threads).

## Technologies
- ejml 0.41
- jama 1.0.3

## Range of functionalities
- generating a diekert graph in a dot format
- generating nxn matrices
- checking the result matrix

## Startup
Run the program with the following arguments:
- n - rozmiar macierzy do wygenerowania
- generate input matrix - false if you wat solve your matrix 
- input - nazwa wygenerowanego pliku z danymi wejściowymi
- output - nazwa pliku z rozwiązaniem
``` 
java -jar main.jar [n] [generate matrix] [input.txt] [output.txt]
```

## Sources
- [checker](https://github.com/macwozni/Matrices)
- [Graphiz](https://dreampuf.github.io/GraphvizOnline/#digraph%20G%20%7B%0D%0AA_1_2%20-%3E%20B_1_1_2%0D%0AB_1_1_2%20-%3E%20C_1_1_2%0D%0AA_1_2%20-%3E%20B_1_2_2%0D%0AB_1_2_2%20-%3E%20C_1_2_2%0D%0AA_1_2%20-%3E%20B_1_3_2%0D%0AB_1_3_2%20-%3E%20C_1_3_2%0D%0AA_1_2%20-%3E%20B_1_4_2%0D%0AB_1_4_2%20-%3E%20C_1_4_2%0D%0AA_1_3%20-%3E%20B_1_1_3%0D%0AB_1_1_3%20-%3E%20C_1_1_3%0D%0AA_1_3%20-%3E%20B_1_2_3%0D%0AB_1_2_3%20-%3E%20C_1_2_3%0D%0AA_1_3%20-%3E%20B_1_3_3%0D%0AB_1_3_3%20-%3E%20C_1_3_3%0D%0AA_1_3%20-%3E%20B_1_4_3%0D%0AB_1_4_3%20-%3E%20C_1_4_3%0D%0AA_2_3%20-%3E%20B_2_2_3%0D%0AB_2_2_3%20-%3E%20C_2_2_3%0D%0AA_2_3%20-%3E%20B_2_3_3%0D%0AB_2_3_3%20-%3E%20C_2_3_3%0D%0AA_2_3%20-%3E%20B_2_4_3%0D%0AB_2_4_3%20-%3E%20C_2_4_3%0D%0AC_1_3_2%20-%3E%20B_2_3_3%0D%0AC_1_4_2%20-%3E%20B_2_4_3%0D%0AC_1_3_3%20-%3E%20C_2_3_3%0D%0AC_1_4_3%20-%3E%20C_2_4_3%0D%0AC_1_2_2%20-%3E%20A_2_3%0D%0AC_1_2_3%20-%3E%20A_2_3%0D%0AA_1_2%20%5Blabel%3D%3CA%3Csub%3E1%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse2%2C%20style%3Dfilled%5D%3B%0D%0AC_1_1_2%20%5Blabel%3D%3CC%3Csub%3E1%2C1%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_1_2%20%5Blabel%3D%3CB%3Csub%3E1%2C1%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_2_2%20%5Blabel%3D%3CC%3Csub%3E1%2C2%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_2_2%20%5Blabel%3D%3CB%3Csub%3E1%2C2%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_3_2%20%5Blabel%3D%3CC%3Csub%3E1%2C3%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_3_2%20%5Blabel%3D%3CB%3Csub%3E1%2C3%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_4_2%20%5Blabel%3D%3CC%3Csub%3E1%2C4%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_4_2%20%5Blabel%3D%3CB%3Csub%3E1%2C4%2C2%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AA_1_3%20%5Blabel%3D%3CA%3Csub%3E1%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse2%2C%20style%3Dfilled%5D%3B%0D%0AC_1_1_3%20%5Blabel%3D%3CC%3Csub%3E1%2C1%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_1_3%20%5Blabel%3D%3CB%3Csub%3E1%2C1%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_2_3%20%5Blabel%3D%3CC%3Csub%3E1%2C2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_2_3%20%5Blabel%3D%3CB%3Csub%3E1%2C2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_3_3%20%5Blabel%3D%3CC%3Csub%3E1%2C3%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_3_3%20%5Blabel%3D%3CB%3Csub%3E1%2C3%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AC_1_4_3%20%5Blabel%3D%3CC%3Csub%3E1%2C4%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddarksalmon%2C%20style%3Dfilled%5D%3B%0D%0AB_1_4_3%20%5Blabel%3D%3CB%3Csub%3E1%2C4%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dchartreuse4%2C%20style%3Dfilled%5D%3B%0D%0AA_2_3%20%5Blabel%3D%3CA%3Csub%3E2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddodgerblue4%2C%20style%3Dfilled%5D%3B%0D%0AC_2_2_3%20%5Blabel%3D%3CC%3Csub%3E2%2C2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddodgerblue2%2C%20style%3Dfilled%5D%3B%0D%0AB_2_2_3%20%5Blabel%3D%3CB%3Csub%3E2%2C2%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dgold4%2C%20style%3Dfilled%5D%3B%0D%0AC_2_3_3%20%5Blabel%3D%3CC%3Csub%3E2%2C3%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddodgerblue2%2C%20style%3Dfilled%5D%3B%0D%0AB_2_3_3%20%5Blabel%3D%3CB%3Csub%3E2%2C3%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dgold4%2C%20style%3Dfilled%5D%3B%0D%0AC_2_4_3%20%5Blabel%3D%3CC%3Csub%3E2%2C4%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Ddodgerblue2%2C%20style%3Dfilled%5D%3B%0D%0AB_2_4_3%20%5Blabel%3D%3CB%3Csub%3E2%2C4%2C3%3C%2Fsub%3E%3E%2C%20fillcolor%3Dgold4%2C%20style%3Dfilled%5D%3B%0D%0A%7D)
