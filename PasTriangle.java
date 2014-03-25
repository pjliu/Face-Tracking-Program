/**
 * Write a description of class PascalTriangle2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

public class PasTriangle
{
    public int [] triangle1(int height){
		if (height == 0){
			int [] last = new int[1];
			last[0] = 1;
			return last;
		}
		
		int [] next = new int[height+1];
		int [] above = triangle1(height-1);
		System.out.println("");
		for (int i = 0; i < height; i++){
			if (i == 0){
				next[i] = 1;
            }
			else{
				next[i] = above[i-1] + above[i];
			}
			System.out.print(next[i] + " ");
		}
		return next;
	}
  
}
