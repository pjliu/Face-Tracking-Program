import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import java.util.TreeSet;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import com.sun.image.codec.jpeg.*;
import java.io.FileWriter;
import java.io.IOException;

public class Distance
{
    public static void main(String[] args) throws IOException 
    {
        FileInputStream in = null;
        Dimension imgDim;
        int gridLineThick = 0; // if set to 0, would not see any grid line
        String origImg = "G:\\Documents\\Pictures\\CamTestPics2\\15.jpg";
        String datFile = "G:\\Documents\\Pictures\\CamTestPics2\\15.jpg";
//      String outImg  = "F:\\pic1.jpg";
        
        try {
            imgDim = getImageDimension(origImg);
            System.out.println("Dimensions = "+imgDim);
           
            in = new FileInputStream(datFile);
/*       
            ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            JFrame frame = new JFrame("RGB");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel content = new JPanel();
            content.setLayout(new GridLayout(imgDim.height, imgDim.width, gridLineThick, gridLineThick));
*/                      
            int colorThreshold = 200;
            int distThreshhold = 50;
//            int xsum=0;
//            int ysum=0;
//            int count=0;
            
            ArrayList points = new ArrayList();
            for (int i=0; i<imgDim.height; i++)
            {
                for (int j=0; j<imgDim.width; j++)
                {
                    int r = in.read();
                    int g = in.read();
                    int b = in.read();
                    
                    if ((r+g+b) > colorThreshold)
                    {
//                        count++;
//                        xsum += j;
//                        ysum += i;
                        points.add(new Point(j, i));
                    }
/*
                    Color theColor = new Color(r, g, b);
                    Color theColor = new Color(255-r, 255-g, 255-b);

                    JPanel obj = new JPanel();
                    obj.setBackground(theColor);                  
                    content.add(obj);              
                    System.out.println((i+1)+","+(j+1)+":"+theColor);                  
*/                  
                }                             
            }
           
            ArrayList[] groups = new ArrayList[3];
            Point[] midpoints = new Point[3];
            for (int k=0; k<3; k++)
            {
                groups[k] = new ArrayList();
                ListIterator listIterator = points.listIterator();                
                groups[k].add((Point)listIterator.next());
                listIterator.remove();
                        
                while (listIterator.hasNext())
                {
                    Point curr = (Point)listIterator.next();
                    Point firstPointInTheGroup = (Point)groups[k].get(0);
                    if (calcDist(curr, firstPointInTheGroup) < distThreshhold)
                    {
                        groups[k].add(curr);
                        listIterator.remove();                       
                    }           
                }
            }
            
            for (int n=0; n<points.size(); n++)
            {
                Point thePoint = (Point)points.get(n);
                System.out.println("points "+n+":"+thePoint.x+","+thePoint.y);                
            }
            
            for (int k=0; k<groups.length; k++)
            {
                int xsum = 0;
                int ysum = 0;
                int count = groups[k].size();
                for (int n=0; n<count; n++)
                {
                    Point thePoint = (Point)groups[k].get(n);
                    xsum += thePoint.x;
                    ysum += thePoint.y;                    
                }
                midpoints[k] = new Point(xsum/count, ysum/count);
                System.out.println("The midpoint of for group["+k+"] is at: {"+midpoints[k].x+","+midpoints[k].y+"}");                
            }
            System.out.println("dist between midpoint0 and midpoint1="+calcDist(midpoints[0], midpoints[1]));
            System.out.println("dist between midpoint0 and midpoint2="+calcDist(midpoints[0], midpoints[2]));
            System.out.println("dist between midpoint1 and midpoint2="+calcDist(midpoints[1], midpoints[2]));            
/*            
            content.setPreferredSize(imgDim);
            frame.setContentPane(content);
            frame.pack();
            frame.setVisible(true);
            saveComponentAsJPEG(content, outImg);
*/
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }         
        }
    }
    
    private static double calcDist(Point p1, Point p2)
    {
        return  Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
    }

/*
    public static void saveComponentAsJPEG(Component myComponent, String fileName) throws Exception
    {
        FileOutputStream fos = new FileOutputStream(fileName);
        Dimension size = myComponent.getPreferredSize();
        BufferedImage myImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = myImage.createGraphics();
        myComponent.paint(g2);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
        encoder.encode(myImage);
        fos.close();
    }
*/
   
    public static Dimension getImageDimension(String fileName) throws Exception
    {
        Dimension imgDim = new Dimension();
        FileInputStream fis = new FileInputStream(fileName); 
        fis.skip(4); // after ff d8  ff eo is a length info of 2 bytes
 
        //look for the marker ffc0
        // ffd9 is the end of the image
 
        boolean myEOF = false;
 
        while (!myEOF)
        {
            // read the length info of a marker; length is 2 bytes
 
            short xx[] = new short[2];
            for(int i = 0;i<2;i++)
            { 
                xx[i] = (short)fis.read();
            }
 
            // make the proper value of the out of the byte info
            int x0 = (int)xx[0];
            int x1 = (int)xx[1];
            long markLength = ((x0 << 8) | (x1));
 
            markLength= markLength-2;
 
            fis.skip(markLength); // to get to the next marker
            short markerName[] = new short[2]; /*marker name*/
            for(int i = 0;i<2;i++)
            { 
                markerName[i] = (short)fis.read();
            }
 
            int theHighByte = (int)markerName[0];
            int theLowByte = (int)markerName[1];
 
            /*Start Of Frame (0xFFCx  where x = 0,1-3,5-7,9-11,13-15)*/
 
            if ((theHighByte == 255) && (
                (theLowByte == 192)  /*c0*/
                || (theLowByte == 193)  /*c1*/
                || (theLowByte == 194)  /*c2*/
                || (theLowByte == 195)  /*c3*/
                || (theLowByte == 197)  /*c5*/
                || (theLowByte == 198)  /*c6*/
                || (theLowByte == 199)  /*c7*/
                || (theLowByte == 201)  /*c9*/
                || (theLowByte == 202)  /*c10*/
                || (theLowByte == 203)  /*c11*/
                || (theLowByte == 205)  /*c13*/
                || (theLowByte == 206)  /*c14*/
                || (theLowByte == 207)  /*c15*/
                )
 
               ) /* ffc0 or ffc1 or ffc2..... marker contains the width and height info*/
               {
 
                   myEOF =true; /*no more scanning of the file ,we came to the desired marker*/
 
                   fis.skip(3);//now that we are at the desired marker we can
                   //skip the length info+ 1 byte shit info
 
                   /*read in the 4 bytes of height and width information*/
                   short dim[] = new short[4];
                   for(int i = 0;i<4;i++)
                   { 
                       dim[i] = (short)fis.read();
                   }
  
                   /*calculate the height from the byte information */
                   int h0 = dim[0];
                   int h1 = dim[1];
 
                   int h = (h0 << 8) | (h1);
 
                   /*calculate the width from the byte information */
                   int w0 = dim[2];
                   int w1 = dim[3];
 
                   int w = (w0 << 8) | (w1);
  
                   imgDim.height = h;
                   imgDim.width = w;
                }
                else
                {
                    myEOF =false; /*go on reading the file to search for the dimension*/
                }
 
            }
            fis.close();       
            return imgDim;
        }   
}  