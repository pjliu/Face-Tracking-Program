/**
 * Write a description of class PascalTriangle here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PascalTriangle
{
    public static void displayPascalTriangle(String [] args, int i) 
    {
        int [][] triangle = new int[i][];//Row, Col

        for (int row=0; row<triangle.length; row++) 
        {
            triangle[row] = new int[row+1];
            triangle[row][0] = 1;
            triangle[row][row] = 1;
            
            for (int col=1; col<row; col++) 
            {
                triangle[row][col] = triangle[row-1][col] + triangle[row-1][col-1];
            }
            
            for (int col=0; col<triangle[row].length; col++) 
            {
                System.out.print("    " + triangle[row][col]);
            }
            System.out.println("");           
        }
    }    
}
