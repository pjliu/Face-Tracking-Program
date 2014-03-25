/**
 * Write a description of class Occurences here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import java.io.*;

public class Occurences
{
    static public String getContents(File aFile) 
    {
        StringBuilder contents = new StringBuilder();
    
        try {
            BufferedReader input =  new BufferedReader(new FileReader(aFile));
            try {
                String line = null;

                while (( line = input.readLine()) != null)
                {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            }
            finally 
            {
                input.close();
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    
        return contents.toString();
    }

    public static void calcOccurences()
    {        
        //Initialize
        Map map = new HashMap();
        for (int i=0; i<26; i++)
        {
            map.put(new Character((char)('A'+i)), new Integer(0));
        }
        map.put(new Character(' '), new Integer(0));
        
        //Count
        String test = getContents(new File("G:\\Face Tracking Program\\AUTOEXEC.BAT"));//Changes depending on where the file is located
        for (int i=0; i<test.length(); i++)
        {
            Character theChar = new Character(test.charAt(i));
            theChar = new Character(theChar.toUpperCase(theChar.charValue()));
            Integer count = (Integer)map.get(theChar);
            if (count != null)
            {
                map.put(theChar, new Integer(count.intValue()+1));
            }
        }
        
        //Print
        for (int i=0; i<26; i++)
        {
            Character theChar = new Character((char)('A'+i));
            System.out.println(theChar + ": " + map.get(theChar));
        }
        System.out.println("Spaces: " + map.get(new Character(' ')));
    }
}
