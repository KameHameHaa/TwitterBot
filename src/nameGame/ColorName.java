package nameGame;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;














import nameGame.*;
import Data.ColorCodeURL;
import Data.ColorValue;
import Data.ReadColorValue;
import Data.ReadData;
import Data.ReadUnBracketedBigramsFrequency;
import Data.UnBracketedBigrams;
import Data.UnBracketedBigramsFreq;
import TwitterClient.*;

public class ColorName {
	

	static ReadData data;

	static Vector<ReadyMade> vReadyMade;
	

	public ColorName() throws Exception
	{
	
		String s =new String();
		data = new ReadData(s);
		vReadyMade = new Vector<ReadyMade>();
		createAllReadyMade(data.colorVector);
		
	}

	
	public static int mergeColor(int c1, int c2)
	{
		 double w1 = 0.6;
		 double w2 = 1 - w1 ;
		 int[] col1 = new int[3];
		 int[] col2 = new int[3];
		 int[] col3 = new int[3];
		 
		  col1[0] = (c1 >> 16) & 0x000000FF;
		  col1[1] = (c1 >>8 ) & 0x000000FF;
		  col1[2] = (c1) & 0x000000FF;
		
		  col2[0] = (c2 >> 16) & 0x000000FF;
		  col2[1] = (c2 >>8 ) & 0x000000FF;
		  col2[2] = (c2) & 0x000000FF;
		
				  
		  col3[0] = (int) ( (double) (w1*col1[0]) + (double) (w2*col2[0]));
		  col3[1] = (int) ((double) w1*col1[1] + (double) w2*col2[1]);
		  col3[2] = (int) ((double) w1*col1[2] + (double) w2*col2[2]);
		 		  
		  	for(int i = 0; i<3;i++)
		  	{
		  		if(col3[i]>=255)
		  			col3[i]=255;
		  	}
		  	
		  	int finalColor =  (col3[2]) + (col3[1] <<8 ) + (col3[0] <<16 );
		  	return(finalColor);
	}
	
	public static int mergeColor(int c1, int c2, double w1, double w2)
	{
		 int[] col1 = new int[3];
		 int[] col2 = new int[3];
		 int[] col3 = new int[3];
		 
		  col1[0] = (c1 >> 16) & 0x000000FF;
		  col1[1] = (c1 >>8 ) & 0x000000FF;
		  col1[2] = (c1) & 0x000000FF;
		
		  col2[0] = (c2 >> 16) & 0x000000FF;
		  col2[1] = (c2 >>8 ) & 0x000000FF;
		  col2[2] = (c2) & 0x000000FF;
		
				  
		  col3[0] = (int) ( (double) (w1*col1[0]) + (double) (w2*col2[0]));
		  col3[1] = (int) ((double) w1*col1[1] + (double) w2*col2[1]);
		  col3[2] = (int) ((double) w1*col1[2] + (double) w2*col2[2]);
		 		  
		  	for(int i = 0; i<3;i++)
		  	{
		  		if(col3[i]>=255)
		  			col3[i]=255;
		  	}
		  	
		  	int finalColor =  (col3[2]) + (col3[1] <<8 ) + (col3[0] <<16 );
		  	return(finalColor);
	}
	public static Color mergeColor(Color c1, Color c2, double w1, double w2)
	{	
		Color c3 = new Color((int)(c1.getRed()*w1+c2.getRed()*w2),(int)(c1.getGreen()*w1+c2.getGreen()*w2),(int)(c1.getBlue()*w1+c2.getBlue()*w2)) ; 
		return(c3);
	}
	
	public static Color mergeColor(Color c1, Color c2)
	{	//Color2 two has a weight of 0.8 and color1 has smaller weight of 0.2
		 double w1 = 0.2;
		 double w2 = 1 - w1 ;
		 Color c3 = new Color((int)(c1.getRed()*w1+c2.getRed()*w2),(int)(c1.getGreen()*w1+c2.getGreen()*w2),(int)(c1.getBlue()*w1+c2.getBlue()*w2)) ; 
		 return(c3);
	}
	public static double distanceColor(int c1, int c2)
	{
		 
		
		 int[] col1 = new int[3];
		 int[] col2 = new int[3];
		 int[] col3 = new int[3];
		 
		  col1[0] = (c1 >> 16) & 0x000000FF;
		  col1[1] = (c1 >>8 ) & 0x000000FF;
		  col1[2] = (c1) & 0x000000FF;
		
		  col2[0] = (c2 >> 16) & 0x000000FF;
		  col2[1] = (c2 >>8 ) & 0x000000FF;
		  col2[2] = (c2) & 0x000000FF;
		
		  int rm =(col1[0] + col2[0] )/ 2;
		  
		  col3[0] = (col1[0] - col2[0]);
		  col3[1] = (col1[1] - col2[1]);
		  col3[2] = (col1[2] - col2[2]);
		 		  
		  	for(int i = 0; i<3;i++)
		  	{
		  		if(col3[i]>=255)
		  			col3[i]=255;
		  	}
		  	
		  	double distance = Math.sqrt( (2+rm/256)*col3[0]*col3[0] + 4*col3[1]*col3[1] + 2+(255-rm)/256*col3[2]*col3[2]);
		  	return(distance);
	}

	public static double distanceColor(Color c1, Color c2)
	{
		  double rm =(c1.getRed()+c2.getRed())/ 2;
		  
		  int w1 = 1;
		  int w2 = -1;
 
		  Color c3 = new Color(Math.abs((int)(c1.getRed()*w1+c2.getRed()*w2)),Math.abs((int)(c1.getGreen()*w1+c2.getGreen()*w2)),Math.abs((int)(c1.getBlue()*w1+c2.getBlue()*w2))) ; 
					  	
		  	double distance = Math.sqrt( (2+rm/256)*c3.getRed()*c3.getRed() + 4*c3.getGreen()*c3.getGreen() + 2+(255-rm)/256*c3.getBlue()*c3.getBlue());
		  	return(distance);
	
		  
	
	}
	
	public static double distanceColor2(Color c1, Color c2)
	{	   	
		  	double distance = Math.sqrt( Math.abs(c1.getRed()*c1.getRed()-c2.getRed()*c2.getRed()) +  Math.abs(c1.getBlue()*c1.getBlue()-c2.getBlue()*c2.getBlue()) +  Math.abs(c1.getGreen()*c1.getGreen()-c2.getGreen()*c2.getGreen())  );
		  	return(distance);
	}
	
	
	public static int createAllReadyMade(Vector<ColorValue> colorVector)
	{
		ColorValue color1 = new ColorValue();
		ColorValue color2 = new ColorValue();
		Color cl;
		for(int i = 0; i< colorVector.size();i++)
		{
			color1= colorVector.elementAt(i);
			for(int j = i+1; j< colorVector.size();j++)
			{
				color2= colorVector.elementAt(j);
				cl = mergeColor(color1.value,color2.value);
				ReadyMade rmd = new ReadyMade();
				rmd.color1= color1;
				rmd.color2 = color2;
				rmd.value = cl;
				rmd.color1.stereotype.toUpperCase();
				rmd.color2.stereotype.toUpperCase();
				rmd.linguisticScore= calculateLinguisticScore(color1.stereotype,color2.stereotype);
				
				if(rmd!=null)
				vReadyMade.add(rmd);
			}
		}
				
				
	return 0;
		
	}
	public static double calculateLinguisticScore(String stereotype,
			String stereotype2) {
		//Linguistic Score Criteria : 0 if both the strings are the same
		//0.4 if they have the same first character
		//+0.1 for every subsequent match 
		//0.1 for same end
		double score=(double) 0.2;
		
		if(stereotype.equals(stereotype2))
		return 0;
		if(stereotype.startsWith(stereotype2.substring(0,1)))
				score+=0.4;
		if(stereotype.endsWith(stereotype2.substring(stereotype2.length()-1)))
				score+=0.1;
		for(int i = 0; i<stereotype.length()-2 &&i<stereotype2.length()-2;i++)
		{
			if(stereotype.regionMatches(true, 0, stereotype2, 0, i+2))
				score+=0.1;
			else 
				break;
		}
		
		return score;
		
	}


	public ReadyMade findSuitableReadyMade(Color clr, int personality)
	{
		
		ColorValue stereotypeColor = findColorStereotypeNameWithMinimumDistance(clr);
		//System.out.println(stereotypeColor.stereotype+" "+stereotypeColor.name);
		ReadyMade rmd = new ReadyMade();
		switch(personality)
		{
		case 0 : rmd = findStanLeeColor(vReadyMade,clr,stereotypeColor.stereotype);
		break;
		case 1 : rmd= findNormalColor(vReadyMade,clr,stereotypeColor.stereotype);
		break;
		case 2 : rmd= findUselessColor(vReadyMade,clr,stereotypeColor.stereotype);
		break;
		default: rmd = findStanLeeColor(vReadyMade,clr,stereotypeColor.stereotype);
		}
		return rmd;	
	}

/*	public static void main(String[] args) throws Exception
	{
		
		 String s =new String();
			ReadData data = new ReadData(s);
		
		 
		 createAllReadyMade(data.colorVector);
		 
		 int randIndex = (int)(Math.random()*(data.colorURL.size()-1));
		 
		 ColorCodeURL URL = data.colorURL.elementAt(randIndex);
		 ReadyMade rmd = findColorNameWithMinimumDistance(vReadyMade,URL.value, "black");
		 Vector<String> unq = uniqueColor(data.colorVector);
		 System.out.println("Unique Color Name :" + unq); 
		 System.out.println("Resultant URL :" + URL); 
		System.out.println("Resultant Color :" + rmd);
		
	//	TwitterSetup twitter = new TwitterSetup();
		 
		 String tweetString ;//= "Hi, I want to congratulate the world for the successful setup of my App";
		
		 tweetString = "A new Color for you : " + rmd.color1.stereotype +":" + rmd.color1.name +"  "+ rmd.color2.stereotype+":" + rmd.color2.name + "  "+rmd.value + " " + URL.value;
		 System.out.println(tweetString); 
		 //twitter.tweetOnTwitter(tweetString);
		
	} */

	
	private ReadyMade findUselessColor(Vector<ReadyMade> vReadyMade2,
			Color inputColor, String stereotype) {
		double maxScore=-10000;
		double distance;
		double norDis;
		
		ReadyMade color = null;
		ReadyMade minColor = null ;
		for(int i = 0; i< vReadyMade.size();i++)
		{
			color= vReadyMade.elementAt(i);
			
			distance= distanceColor(inputColor, color.value );
			norDis =1 - (double) (2*(double)distance/(double)(inputColor.getRed()+color.value.getRed()+inputColor.getBlue()+color.value.getBlue()+inputColor.getGreen()+color.value.getGreen() ));
			double score = norDis ; 
			
			if(score>maxScore && score < 0.8)
			{
				minColor =  color;
				maxScore = score;
			}
		}		
		
		//System.out.println(maxScore);
		
		if(maxScore < 0.5)
		return minColor;
		else return null;
		
	}


	private ReadyMade findNormalColor(Vector<ReadyMade> vReadyMade,
			Color inputColor, String stereotype) {
		
		double maxScore=-10000;
		double distance;
		double norDis;
		
		ReadyMade color = null;
		ReadyMade minColor = null ;
		for(int i = 0; i< vReadyMade.size();i++)
		{
			color= vReadyMade.elementAt(i);
			
			if(!color.color2.stereotype.equals(stereotype))
			continue;
			
			distance= distanceColor(inputColor, color.value );
			norDis =1 - (double) (2*(double)distance/(double)(inputColor.getRed()+color.value.getRed()+inputColor.getBlue()+color.value.getBlue()+inputColor.getGreen()+color.value.getGreen() ));
			double score = norDis ; 
			
			if(score>maxScore)
			{
				minColor =  color;
				maxScore = score;
			}
		}		
		
		//System.out.println(maxScore);
		if(maxScore > 0.95)
		return minColor;
		else return null;
	}


	public static ColorValue findColorStereotypeNameWithMinimumDistance(Color inputColor) {
		
		
		
		double minDistance=10000;
		double distance;
		
		ColorValue color = new ColorValue();
		ColorValue minColor = new ColorValue();
		for(int i = 0; i< data.colorVector.size();i++)
		{
			color= data.colorVector.elementAt(i);
			distance= distanceColor(inputColor, color.value );
			
			if(distance<minDistance)
			{
				minDistance = distance;
				minColor =  color;
			
			}
		}		
		
			// 
		
				return minColor;
	}
	public static ReadyMade findStanLeeColor(Vector<ReadyMade> vReadyMade,
			Color inputColor, String stereotype) {
		
		
		
		double maxScore=-10000;
		double distance;
		double norDis;
		
		ReadyMade color = null;
		ReadyMade minColor = null ;
		for(int i = 0; i< vReadyMade.size();i++)
		{
			color= vReadyMade.elementAt(i);
			
			if(!color.color2.stereotype.equals(stereotype))
			continue;
			
			distance= distanceColor(inputColor, color.value );
			norDis =1 - (double) (2*(double)distance/(double)(inputColor.getRed()+color.value.getRed()+inputColor.getBlue()+color.value.getBlue()+inputColor.getGreen()+color.value.getGreen() ));
			double score = norDis + 1.25*color.linguisticScore; // Score gives higher weightage to linguistic feature between two colors than their normalized Distance
			
			if(score>maxScore)
			{
				minColor =  color;
				maxScore = score;
			}
		}		
		
		//System.out.println(maxScore);
		return minColor;
	}
	
	static Vector<String> uniqueColor(Vector<ColorValue> vColor)
	{
		Vector<String> unq= new Vector<String>(); 
		
		
		for(int i = 0; i< vColor.size();i++)
		{
			if(!unq.contains(vColor.elementAt(i).name))
				unq.add(vColor.elementAt(i).name)	;
		}

		return unq;
		
		
		
	}
	
}
