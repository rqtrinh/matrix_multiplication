//Import necessary libraries
import java.io.FileWriter;
import java.util.Random;

public class Matrix {
    public static void main(String[] args) throws Exception 
    {
        //Creating objects so we can test methods
        Matrix matrix =  new Matrix();
        Random random = new Random();
        //This will be the amount of trials for randomly generated input data
        int randomMatrixTrial = 100;
        //This will be the amount of trials for the same set of input data
        int sameMatrixTrial = 10;

        //Generate a new txt file for every test
        FileWriter init = new FileWriter("data.txt", false);
        init.close();

        //Test for 2 to the power of 1-9, testing sizes 2-512
        for(int power = 1; power < 10; power++)
        {
            //Start and finish times for our timer
            long start, finish; 
            //Sum of all randomly generated matrix runtimes
            long totalClassic = 0;
            long totalDivideConquer = 0;
            long totalStrassen = 0;

            //Size of matrix is 2 to the power of 1-9
            int n = (int)Math.pow(2, power);

            //Initialize size n x n matrix
            int[][] A = new int[n][n];
            int[][] B = new int[n][n];
            int[][] C = new int[n][n];

            //We will test 1000 random matrices and find their average run time
            for (int trial = 0; trial < randomMatrixTrial; trial++)
            {
                //Populate matrixes with random integers -10 to 10
                for(int i = 0; i < n; i++)
                {
                    for(int j = 0; j < n; j++)
                    {
                        A[i][j] = random.nextInt(21)-10;
                        B[i][j] = random.nextInt(21)-10;
                    }
                }
                
                //The sum of all matrix runtimes that have the same input
                long classic = 0; 
                long divideConquer = 0; 
                long strassen = 0;

                //For each randomly generated matrix we will test it 20 times and take the avg
                for (int sameTrial = 0;  sameTrial< sameMatrixTrial; sameTrial++)
                {
                    //We will test all three methods in nano seconds
                    start =  System.nanoTime();
                    C = matrix.classical(n, A, B);
                    finish = System.nanoTime();
                    classic += finish-start; //Find the sum

                    start =  System.nanoTime();
                    C = matrix.divideConquer(n, A, B);
                    finish = System.nanoTime();
                    divideConquer += finish-start; //Find the sum

                    start =  System.nanoTime();
                    C = matrix.strassen(n, A, B);
                    finish = System.nanoTime();
                    strassen += finish-start; //Find the sum 
                }

                //Find the average runtime of all trials that have the same input and add it to the total sum
                totalClassic += (classic/sameMatrixTrial);
                totalDivideConquer += (divideConquer/sameMatrixTrial);
                totalStrassen += (strassen/sameMatrixTrial);
            }

            //Find the average runtime of each of all the random matrix runtimes
            totalClassic = totalClassic/randomMatrixTrial;
            totalDivideConquer = totalDivideConquer/randomMatrixTrial;
            totalStrassen = totalStrassen/randomMatrixTrial;

            //Write all of our data to a data.txt file 
            FileWriter writer = new FileWriter("data.txt", true);
            writer.write("Size " + n + " results:" + 
                        " Classical = "  + totalClassic + " nanoseconds," +
                        " Divide and Conquer = " + totalDivideConquer + " nanoseconds," +
                        " Strassen = " + totalStrassen + " nanoseconds\n");
            writer.close();
        }
    }


    //Classical Matrix Multiplication
    public int[][] classical(int n, int [][] A, int [][] B)
    {
        int [][] C = new int[n][n];
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                for(int k = 0; k < n; k++)
                {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    //Divide and conquer approach
    public int[][] divideConquer(int n, int [][] A, int [][] B)
    {
        //base case for divide and conquer approach
        if(n == 2) 
        {
            int c11 = A[0][0]*B[0][0] + A[0][1]*B[1][0];
            int c12 = A[0][0]*B[0][1] + A[0][1]*B[1][1];
            int c21 = A[1][0]*B[0][0] + A[1][1]*B[1][0];
            int c22 = A[1][0]*B[0][1] + A[1][1]*B[1][1];

            //Create return matrix
            int [][] C = { {c11, c12},
                           {c21, c22} };
            
            return C;
        }    

        else
        {
            //Partition A matrix
            int [][] A11 = partition(n, 11, A);
            int [][] A12 = partition(n, 12, A);
            int [][] A21 = partition(n, 21, A);
            int [][] A22 = partition(n, 22, A);

            //Partition B matrix
            int [][] B11 = partition(n, 11, B);
            int [][] B12 = partition(n, 12, B);
            int [][] B21 = partition(n, 21, B);
            int [][] B22 = partition(n, 22, B);

            //Add matrices after we recursively call
            int [][] C11 = addition(divideConquer(n/2, A11, B11), divideConquer(n/2, A12, B21));
            int [][] C12 = addition(divideConquer(n/2, A11, B12), divideConquer(n/2, A12, B22));
            int [][] C21 = addition(divideConquer(n/2, A21, B11), divideConquer(n/2, A22, B21));
            int [][] C22 = addition(divideConquer(n/2, A21, B12), divideConquer(n/2, A22, B22));

            //Combine matrix
            int [][] C = combine(n, C11, C12, C21, C22);
            return C;
        }
    }

    public int[][] strassen(int n, int [][] A, int [][] B)
    {
        //Base case for strassen approach
        if(n == 2) 
        {
            int c11 = A[0][0]*B[0][0] + A[0][1]*B[1][0];
            int c12 = A[0][0]*B[0][1] + A[0][1]*B[1][1];
            int c21 = A[1][0]*B[0][0] + A[1][1]*B[1][0];
            int c22 = A[1][0]*B[0][1] + A[1][1]*B[1][1];

            //Create return matrix
            int [][] C = { {c11, c12},
                           {c21, c22} };
            
            return C;
        }

        //Recursive calls
        else
        { 
            //Partition A matrix
            int [][] A11 = partition(n, 11, A);
            int [][] A12 = partition(n, 12, A);
            int [][] A21 = partition(n, 21, A);
            int [][] A22 = partition(n, 22, A);

            //Partition B matrix
            int [][] B11 = partition(n, 11, B);
            int [][] B12 = partition(n, 12, B);
            int [][] B21 = partition(n, 21, B);
            int [][] B22 = partition(n, 22, B);

            //Recursivley call strassen
            int [][] P = strassen(n/2, addition(A11, A22), addition(B11, B22));
            int [][] Q = strassen(n/2, addition(A21, A22), B11);
            int [][] R = strassen(n/2, A11, subtraction(B12, B22));
            int [][] S = strassen(n/2, A22, subtraction(B21, B11));
            int [][] T = strassen(n/2, addition(A11, A12), B22);
            int [][] U = strassen(n/2, subtraction(A21, A11), addition(B11, B12));
            int [][] V = strassen(n/2, subtraction(A12, A22), addition(B21, B22));

            //Calculate C11, C12, C21, C22 from our recursive calls
            int [][] C11 = addition(subtraction(addition(P, S), T), V);
            int [][] C12 = addition(R, T);            
            int [][] C21 = addition(Q, S);
            int [][] C22 = addition(subtraction(addition(P, R), Q), U);

            //Create C matrix from its qudrants and return complete matrix
            int [][] C = combine(n, C11, C12, C21, C22);
            return C;
        }


    }

    //Matrix addition for matrix of matching width and height
    public int[][] addition(int[][] A, int[][] B)
    {
        int [][] C = new int[A.length][A[0].length];
        for(int i = 0; i < A.length; i++)
        {
            for(int j = 0; j < A[0].length; j++)
            {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

     //Matrix subtraction for matrix of matching width and height
    public int[][] subtraction(int[][] A, int[][] B)
    {
        int [][] C = new int[A.length][A[0].length];
        for(int i = 0; i < A.length; i++)
        {
            for(int j = 0; j < A[0].length; j++)
            {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    //Partition matrix of matching width and height
    public int[][] partition(int n, int part, int[][] matrix)
    {
        //Initialize return qudrant
        int[][] quadrant = new int[n/2][n/2];
        //Switch statement for which area we will copy and return
        switch (part) 
        {
            //C11
            case 11:
                for(int i = 0; i < (n/2); i++)
                {
                    for(int j = 0; j < (n/2); j++)
                    {
                        quadrant[i][j] = matrix[i][j];
                    }
                }
                break;
            //C12
            case 12:
                for(int i = 0; i < (n/2); i++)
                {
                    for(int j = 0; j < (n/2); j++)
                    {
                        quadrant[i][j] = matrix[i][j+quadrant[0].length];
                    }
                }
                break;

            //C21
            case 21:
                for(int i = 0; i < (n/2); i++)
                {
                    for(int j = 0; j < (n/2); j++)
                    {
                        quadrant[i][j] = matrix[i+quadrant.length][j];
                    }
                }                
                break;
            //C22
            case 22:
                for(int i = 0; i < (n/2); i++)
                {
                    for(int j = 0; j < (n/2); j++)
                    {
                        quadrant[i][j] = matrix[i+quadrant.length][j+quadrant[0].length];
                    }
                }
                break;
        }
    return quadrant;
    }

    public int [][] combine(int n, int [][] C11, int [][] C12, int [][] C21, int [][] C22 )
    {
        //Initialize return matrix
        int [][] complete = new int[n][n];

        //Loop through n/2
        for(int i = 0; i < n/2; i++)
        {
            for(int j = 0; j < n/2; j++)
            {
                //Combine C11, C12, C21, C22
                complete[i][j] = C11[i][j];
                complete[i][j+(n/2)] = C12[i][j];
                complete[i+(n/2)][j] = C21[i][j];
                complete[i+(n/2)][j+(n/2)] = C22[i][j];
            }
        }
        return complete;
    }
}
