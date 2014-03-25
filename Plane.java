import java.util.*;
import java.util.TreeSet;
import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
import javax.imageio.*;
import com.sun.image.codec.jpeg.*;

//Given 3 points (x1,y1,z1), (x2,y2,z2) and (x3,y3,z3) in a 3-dimensional Cartesian space.

public class Plane
{    
    private static double calcDist(float[] p1, float[] p2)
    {
        double result = 0;
        for (int i=0; i<p1.length; i++)
        {
             result += (p1[i] - p2[i]) * (p1[i] - p2[i]);
        }    
        return Math.sqrt(result);
    }
    
    //(a) Find line L through (x1,y1,z1) and (x2,y2,z2); use vector form
    public static String findLine(double x1, double y1, double z1, 
                                  double x2, double y2, double z2)
    {
        String line = "x="+x1+(x2-x1)+"*t\n" + 
                      "y="+y1+(y2-y1)+"*t\n" +
                      "z="+z1+(z2-z1)+"*t\n";
        return line;
    }
    
    //(b) Find plane P consisting of these 3 points
    public static String findPlane(double x1, double y1, double z1, 
                                   double x2, double y2, double z2, 
                                   double x3, double y3, double z3)
    {
        double[][] D = new double[3][3];
        D[0][0] = x1; D[0][1] = y1; D[0][2] = z1;
        D[1][0] = x2; D[1][1] = y2; D[1][2] = z2;
        D[2][0] = x3; D[2][1] = y3; D[2][2] = z3;
        double detD = determinant(D);
            
        double[][] matA = new double[3][3];
        matA[0][0] = 1; matA[0][1] = y1; matA[0][2] = z1;
        matA[1][0] = 1; matA[1][1] = y2; matA[1][2] = z2;
        matA[2][0] = 1; matA[2][1] = y3; matA[2][2] = z3;
        double detA = determinant(matA);            
            
        double[][] matB = new double[3][3];
        matB[0][0] = x1; matB[0][1] = 1; matB[0][2] = z1;
        matB[1][0] = x2; matB[1][1] = 1; matB[1][2] = z2;
        matB[2][0] = x3; matB[2][1] = 1; matB[2][2] = z3;
        double detB = determinant(matB); 
        
        double[][] matC = new double[3][3];
        matC[0][0] = x1; matC[0][1] = y1; matC[0][2] = 1;
        matC[1][0] = x2; matC[1][1] = y2; matC[1][2] = 1;
        matC[2][0] = x3; matC[2][1] = y3; matC[2][2] = 1;
        double detC = determinant(matC);           
        
        double a = detA / detD;
        double b = detB / detD;
        double c = detC / detD;
        
        return a+"*x"+b+"*y"+c+"*z=1";
    }
    //This is to calculate the determinant of a matrix 
    public static double determinant(double[][] mat) 
    {
        double result = 0;
        if(mat.length == 1) 
        {
            result = mat[0][0];
            return result;
        }
        if(mat.length == 2) 
        {
            result = mat[0][0] * mat[1][1] - mat[0][1] * mat[1][0];
            return result;
        }
        for(int i = 0; i < mat[0].length; i++) 
        {
            double temp[][] = new double[mat.length - 1][mat[0].length - 1];
            for(int j = 1; j < mat.length; j++) 
            {
                for(int k = 0; k < mat[0].length; k++) 
                {
                    if(k < i) 
                    {
                        temp[j - 1][k] = mat[j][k];
                    } else if(k > i) {
                        temp[j - 1][k - 1] = mat[j][k];
                    }
                }
            }
            result += mat[0][i] * Math.pow(-1, (double)i) * determinant(temp);
        }
        return result;
        
    } 
    
    
        
    
    
}  