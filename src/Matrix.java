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

    public int[][] strassen(int n, int [][] A, int [][] B){
        if(n == 2) 
        {
            int c11 = A[0][0]*B[0][0] + A[0][1]*B[1][0];
            int c12 = A[0][0]*B[0][1] + A[0][1]*B[1][1];
            int c21 = A[1][0]*B[0][0] + A[1][1]*B[1][0];
            int c22 = A[1][0]*B[0][1] + A[1][1]*B[1][1];

            int [][] C = { {c11, c12},
                           {c21, c22} };
            
            return C;
        }
        else
        { 
            return C;
        }


    }

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
        int [][] complete = new int[n*2][n*2];
        return complete;

    }
}
