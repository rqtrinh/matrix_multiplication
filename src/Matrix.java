public class Matrix {
  public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }

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
            int [][] P = strassen(n/2, addition(n, A11, A22), addition(n, B11, B22));
            int [][] Q = strassen(n/2, addition(n, A21, A22), B11);
            int [][] R = strassen(n/2, A11, subtraction(n, B12, B22));
            int [][] S = strassen(n/2, A22, subtraction(n, B21, B11));
            int [][] T = strassen(n/2, addition(n, A11, A12), B22);
            int [][] U = strassen(n/2, subtraction(n, A21, A11), addition(n, B11, B12));
            int [][] V = strassen(n/2, subtraction(n, A21, A22), addition(n, B11, B12));

            //Calculate C11, C12, C21, C22 from our recursive calls
            int [][] C11 = addition(n, subtraction(n, addition(n, P, S), T), V);
            int [][] C12 = addition(n, R, T);            
            int [][] C21 = addition(n, Q, S);
            int [][] C22 = addition(n, subtraction(n, addition(n, P, R), Q), U);

            //Create C matrix from its qudrants and return complete matrix
            int [][] C = repartition(n, C11, C12, C21, C22);
            return C;
        }


    }

    //Matrix addition for matrix of matching width and height
    public int[][] addition(int n, int[][] A, int[][] B)
    {
        int [][] C = new int[n][n];
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

     //Matrix subtraction for matrix of matching width and height
    public int[][] subtraction(int n, int[][] A, int[][] B)
    {
        int [][] C = new int[n][n];
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
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
                for(int i = 0; i < n/2; i++)
                {
                    for(int j = 0; j < n/2; j++)
                    {
                        quadrant[i][j] = matrix[i][j];
                    }
                }
                break;
            case 12:
                for(int i = 2; i < n/2; i++)
                {
                    for(int j = 0; j < (n/2)+2; j++)
                    {
                        quadrant[i][j] = matrix[i][j];
                    }
                }
                break;
            case 21:
                for(int i = 0; i < (n/2)+2; i++)
                {
                    for(int j = 0; j < n/2; j++)
                    {
                        quadrant[i][j] = matrix[i][j];
                    }
                }                
                break;
            case 22:
                for(int i = 0; i < (n/2)+2; i++)
                {
                    for(int j = 0; j < (n/2)+2; j++)
                    {
                        quadrant[i][j] = matrix[i][j];
                    }
                }
                break;
        }
    return quadrant;
    }

    public int [][] repartition(int n, int [][] C11, int [][] C12, int [][] C21, int [][] C22 )
    {
        //initialize return matrix
        int [][] complete = new int[n*2][n*2];

        //repartition C11
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                complete[i][j] = C11[i][j];
            }
        }
        //repartition C12
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                complete[i+2][j] = C11[i][j];
            }
        }
        //repartition C21
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                complete[i][j+2] = C21[i][j];
            }
        }
        //repartition C22
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                complete[i+2][j+2] = C22[i][j];
            }
        }

        //return reparitioned matrix
        return complete;

    }
}
