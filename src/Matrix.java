//Import necessary libraries
import java.util.Arrays;

public class Matrix {
    public static void main(String[] args) throws Exception 
    {
        Matrix matrix =  new Matrix();
        int [][] A = {{3,0,3,1},
                      {5,3,1,0},
                      {3,0,3,1},
                      {1,2,2,5}};
        int [][] B = {{2,4,5,2},
                      {1,3,2,1},
                      {3,0,5,0},
                      {0,4,1,4}};
        int [][] C =  matrix.divideConquer(4, A, B);
        for (int [] row : C)
        {
            System.out.println(Arrays.toString(row));
        }
        System.out.println(A);
    }


    //Traditional Matrix Multiplication
    public int[][] traditional(int n, int [][] A, int [][] B)
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

            //create return matrix
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

            int [][] C11 = addition(divideConquer(n/2, A11, B11), divideConquer(n/2, A12, B21));
            int [][] C12 = addition(divideConquer(n/2, A11, B12), divideConquer(n/2, A12, B22));
            int [][] C21 = addition(divideConquer(n/2, A21, B11), divideConquer(n/2, A22, B21));
            int [][] C22 = addition(divideConquer(n/2, A21, B12), divideConquer(n/2, A22, B22));

            int [][] C = repartition(n, C11, C12, C21, C22);
            return C;
        }
    }

    public int[][] strassen(int n, int [][] A, int [][] B)
    {
        //base case for strassen approach
        if(n == 2) 
        {
            int c11 = A[0][0]*B[0][0] + A[0][1]*B[1][0];
            int c12 = A[0][0]*B[0][1] + A[0][1]*B[1][1];
            int c21 = A[1][0]*B[0][0] + A[1][1]*B[1][0];
            int c22 = A[1][0]*B[0][1] + A[1][1]*B[1][1];

            //create return matrix
            int [][] C = { {c11, c12},
                           {c21, c22} };
            
            return C;
        }

        //recursive calls
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
            int [][] C = repartition(n, C11, C12, C21, C22);
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

    public int[][] partition(int n, int part, int[][] matrix)
    {
        int[][] quadrant = new int[n/2][n/2];
        switch (part) 
        {
            case 11:
                for(int i = 0; i < (n/2); i++)
                {
                    for(int j = 0; j < (n/2); j++)
                    {
                        quadrant[i][j] = matrix[i][j];
                    }
                }
                break;
            case 12:
                for(int i = 0; i < (n/2); i++)
                {
                    for(int j = 0; j < (n/2); j++)
                    {
                        quadrant[i][j] = matrix[i][j+quadrant[0].length];
                    }
                }
                break;
            case 21:
                for(int i = 0; i < (n/2); i++)
                {
                    for(int j = 0; j < (n/2); j++)
                    {
                        quadrant[i][j] = matrix[i+quadrant.length][j];
                    }
                }                
                break;
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

    public int [][] repartition(int n, int [][] C11, int [][] C12, int [][] C21, int [][] C22 )
    {
        //initialize return matrix
        int [][] complete = new int[n][n];

        //repartition C11
        for(int i = 0; i < n/2; i++)
        {
            for(int j = 0; j < n/2; j++)
            {
                complete[i][j] = C11[i][j];
            }
        }
        //repartition C12
        for(int i = 0; i < n/2; i++)
        {
            for(int j = 0; j < n/2; j++)
            {
                complete[i][j+(n/2)] = C12[i][j];
            }
        }
        //repartition C21
        for(int i = 0; i < n/2; i++)
        {
            for(int j = 0; j < n/2; j++)
            {
                complete[i+(n/2)][j] = C21[i][j];
            }
        }
        //repartition C22
        for(int i = 0; i < n/2; i++)
        {
            for(int j = 0; j < n/2; j++)
            {
                complete[i+(n/2)][j+(n/2)] = C22[i][j];
            }
        }

        //return reparitioned matrix
        return complete;

    }
}
