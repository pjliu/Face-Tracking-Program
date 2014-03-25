/**
 * Write a description of class GCD here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GCD
{
    //int a = 0;
    //int b = 0;
    
   public static int gcd(int a, int b) 
   {
       if (b==0)
       {
           return a;
       }
       else
       {
           return gcd(b, a % b);
       }
    } 
}
