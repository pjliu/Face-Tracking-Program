/**
 * Write a description of class PrimeNumbers here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;

public class PrimeNumbers
{
    static int start = 2;
    static int end = 97;//#2-97
    static ArrayList<Integer> numArray = new ArrayList<Integer>();
    static ArrayList<Integer> primeArray = new ArrayList<Integer>();    
    static boolean isPrime = false;
    static int n;
    
    public static boolean isPrime(int num)
    {
        if (num==2)// 2 is prime
        {
            return true;
        }
        else if (num % 2 == 0)//Even numbers are not prime
        {
            return false;
        }
        else
        {
            for (int i = 3; i < num; i++) 
            {
                if (num % i == 0)
                {
                    return false;
                }
            }
            return true;
        }
    }
    
    public static void main(String[] args) 
    {
        int num = start;
        while(num <= end)
        {
            numArray.add(num);
            if(isPrime(num) == true)
            {
                primeArray.add(num);            
            }
            num++;
        }
                //System.out.println(numArray);
        System.out.println(numArray);
        System.out.println(primeArray);
    }
}
