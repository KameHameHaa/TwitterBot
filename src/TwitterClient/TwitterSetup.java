package TwitterClient;


import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;

import nameGame.ColorName;
import nameGame.ReadyMade;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import Data.PluralBigramsFrequency;
public class TwitterSetup {
	
		 
	    private  static String CONSUMER_KEY;
	    private  static String CONSUMER_KEY_SECRET ;
	    private  static String ACCESS_TOKEN ;
	    private  static String ACCESS_TOKEN_SECRET;
	 
	  //Mode of operation :
	  //1. Fan of Stan Lee with 6/10 chances
	  //2. The Boss personality with 1/10 chance, select a color with low score and publishes it on tweeter.
	  //3. 3/10 chances are that bot will only focus on the color and tweet only if the color match is very high


	  	    public static void main(String[] args) throws Exception {
	  	    
	  	    	String filename = "C:/Users/ankit/workspace/Keys/keys.xlsx";
	  	    	readKeys(filename); 	
	  	    	boolean flagOnce = true;
	  	    	Status lastStatusRepliedTo = everycolorbotListerner();
	  	    	ColorName nameOfColor = new ColorName();
	  	    	
	  	    	while(true)
	  	    	{
	  	    		
	  	    		Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.twitter.com");
	  	    		int returnVal = p1.waitFor();
	  	    		boolean unreachable = (returnVal==0);
	  	    		
	  	    		if(unreachable)
	  	    			continue;
	  	    		Status lastStatusEveryColorBot = everycolorbotListerner();
	  	    		
	  	    		
	  	    		String clrStr = lastStatusEveryColorBot.getText().substring(0,8).trim();
	  	    		Color clr = new Color (Integer.decode (clrStr ));
	  	    		int personality = randomPersonality();
	  	    		
	  	    		System.out.println("Personality is : "+personality);
	  	    		
	  	    		String tweet =null;
	  	    		 ReadyMade colorReadyMade = nameOfColor.findSuitableReadyMade(clr,personality);
	  	    		
	  	    		//String tweet  = "A new Color for you : "+colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype+" "+lastStatusEveryColorBot.getText().substring(8).trim()+" RT .@everycolorbot: " + clrStr;
	  	    		
	  	    		 
	  	    		 if(colorReadyMade == null)
	  		    			continue;
	  		    		
	  	    		tweet  = createTweet(personality,colorReadyMade);
	  	    		
	  	    		if(tweet == null || tweet.isEmpty())
	  	    			continue;
	  	    		
	  	    			tweet+=" "+lastStatusEveryColorBot.getText().substring(8).trim()+" RT .@everycolorbot: " + clrStr;
	  	    		System.out.println(tweet);
	  	    		if(!lastStatusRepliedTo.equals(lastStatusEveryColorBot)||flagOnce)
	  		    	{
	  		    		flagOnce = false;
	  		    		
	  		    		Status lastStatusColorTheWorld = ColorTheWorldListerner();
	  		    		if(lastStatusColorTheWorld != null && tweet != null && !lastStatusColorTheWorld.getText().isEmpty() )
	  		    		{	
	  			    		 if(!tweet.equals(lastStatusColorTheWorld.getText().trim()) && !colorAlreadyUsed(colorReadyMade))
	  			    		 {
	  			    			 StatusUpdate stat= new StatusUpdate(tweet);
	  	
	  			    			    stat.setInReplyToStatusId(lastStatusEveryColorBot.getId());	    			    
	  			    			 
	  			    				if(tweetOnTwitter(stat,colorReadyMade))
	  			    				System.out.println("Happily tweeted");	
	  			    				
	  			    		 }
	  			    			else 
	  			    				System.out.println("This color has already been tweeted: "+ lastStatusColorTheWorld.getText());
	  		    		}
	  		    		else //tweet directly without any hesisting 
	  		    			{
	  		    				StatusUpdate stat= new StatusUpdate(tweet);
	  		    				stat.setInReplyToStatusId(lastStatusEveryColorBot.getId());	
	  		    				if(tweetOnTwitter(stat,colorReadyMade))
	  		    				System.out.println("Happily tweeted");	
	  		    			}
	  		    		lastStatusRepliedTo = lastStatusEveryColorBot;
	  		    	}
	  	    		else
	  	    		{
	  	    			System.out.println("Waiting for a new status");
	  	    			
	  	    		}
	  		    	try {
	  		    	    // to sleep 10 minutes
	  		    	    Thread.sleep(1000*60*10);
	  		    	    
	  		    	} catch (InterruptedException e) {
	  		    	    Thread.currentThread().interrupt();
	  		    	   return;
	  		    	}
	  	    
	  	    	}//End of infinite loop
	  	    } //End of Main

	    public static void readKeys(String filename)	   
	   {
		   if(filename.equals(null)||filename.isEmpty())
				filename = "C:/Users/ankit/workspace/Keys/keys.xlsx";
			
		   try {
			//FileInputStream file = new FileInputStream(new File("C:/Users/ankit/workspace/TwitterApp/resources/Veale's unbracketed color bigrams.xlsx"));
			FileInputStream file = new FileInputStream(new File(filename));

			//Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook;
			
				workbook = new XSSFWorkbook(file);
			
			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
		
			Row row = rowIterator.next();
				
				//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
				
				
				while (cellIterator.hasNext()) 
				{
					
					Cell cell = cellIterator.next();
						if(cell.getColumnIndex()==0)
						{	
						
							CONSUMER_KEY = cell.getStringCellValue().trim();
							//System.out.println(CONSUMER_KEY+" ");
						}	
						else if (cell.getColumnIndex()==1)
						{
							CONSUMER_KEY_SECRET = cell.getStringCellValue().trim();						
							//System.out.println(CONSUMER_KEY_SECRET+" ");
						}
						
						else if (cell.getColumnIndex()==2)
						{
							ACCESS_TOKEN_SECRET = cell.getStringCellValue().trim();						
							//System.out.println(ACCESS_TOKEN_SECRET+" ");
						}
						else if (cell.getColumnIndex()==3)
						{
							ACCESS_TOKEN = cell.getStringCellValue().trim();						
							//System.out.println(ACCESS_TOKEN+" ");
						}		
				}	
		   } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.print("Couldn't retrieve keys");
			}
}	
		
		   
		   
	

public static boolean tweetOnTwitter(StatusUpdate tweet , ReadyMade rmd) throws TwitterException, IOException
{
	Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		
		 AccessToken oathAccessToken = new AccessToken(ACCESS_TOKEN,ACCESS_TOKEN_SECRET);

		 twitter.setOAuthAccessToken(oathAccessToken);
		 
		 if(!tweet.equals(null))		
		 {
			 System.out.println("Tweeting :"+tweet);
			 twitter.updateStatus(tweet);
		 
		 
			CSVWriter writer = new CSVWriter(new FileWriter("C:/Users/ankit/workspace/NameGame/resources/TweetedColors.csv",true), '\t');
	        // feed in your array (or convert your data to an array)
	        String[] entries = new String[2];
	        
	        entries[0] = rmd.color1.stereotype;
	        entries[1] = rmd.color2.stereotype;
	        
	        writer.writeNext(entries);
	    	
	        writer.close();
		 
	        return true;
		 }
	return false;	 
}

public static   ResponseList<Status> tweeterTimeline() throws TwitterException, IOException
{
	Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		
		 AccessToken oathAccessToken = new AccessToken(ACCESS_TOKEN,ACCESS_TOKEN_SECRET);

		 twitter.setOAuthAccessToken(oathAccessToken);
		  ResponseList<Status> list = twitter.getHomeTimeline();
		 return list;
}
public static Vector<Status> usefulListerner()
{
	Color lastColor ;
	 Status lastStatusECB = null;
	 Status lastStatusCTW = null;
	 String lastURL;
	 boolean fECB=true,fCTW=true;
	 Vector<Status> lastStatus=new Vector<Status>();
	try
	{
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		
		 AccessToken oathAccessToken = new AccessToken(ACCESS_TOKEN,ACCESS_TOKEN_SECRET);

		 twitter.setOAuthAccessToken(oathAccessToken);
		  ResponseList<Status> list = twitter.getHomeTimeline();

	 
	 for (Status each : list) {
		 if(each.getUser().getScreenName().equals("everycolorbot")&&fECB)
		 {// System.out.println("Sent by: @" + each.getUser().getScreenName() + " - " + each.getUser().getName() + "\n" + each.getText() + "\n");
		 	lastColor = new Color (Integer.decode (each.getText().substring(0,8).trim() )); 
		 	lastURL = each.getText().substring(8).trim();
		 	lastStatusECB = each;
		 	//System.out.println("Color:"+lastColor+" URL:"+lastURL);
		 	fECB = false;
		 }
		 if(each.getUser().getScreenName().equals("worldiscolored")&&fCTW)
		 {// System.out.println("Sent by: @" + each.getUser().getScreenName() + " - " + each.getUser().getName() + "\n" + each.getText() + "\n");
		 	lastColor = new Color (Integer.decode (each.getText().substring(0,8).trim() )); 
		 	lastURL = each.getText().substring(8).trim();
		 	lastStatusCTW = each;
		 	//System.out.println("Color:"+lastColor+" URL:"+lastURL);
		 	fCTW = false;
		 }
		 if(!fCTW && !fECB)
			 break;
	 }
	}catch (TwitterException te) {
		te.printStackTrace();
		System.out.println("Failed to get timeline: " + te.getMessage());
		System.exit(-1);
		}
	lastStatus.add(lastStatusCTW);
	lastStatus.add(lastStatusECB);
	return lastStatus;

}
public static Status everycolorbotListerner()
{
	
	Color lastColor ;
	 Status lastStatus = null;
	 String lastURL;
	try
	{
	 ResponseList<Status> list = tweeterTimeline();
	 
	 
	 for (Status each : list) {
		 if(each.getUser().getScreenName().equals("everycolorbot"))
		 { //System.out.println("Sent by: @" + each.getUser().getScreenName() + " - " + each.getUser().getName() + "\n" + each.getText() + "\n");
		 	lastColor = new Color (Integer.decode (each.getText().substring(0,8).trim() )); 
		 	lastURL = each.getText().substring(8).trim();
		 	lastStatus = each;
		 	//System.out.println("Color:"+lastColor+" URL:"+lastURL);
		 	break;
		 }
	 }
	}catch (TwitterException te) {
		te.printStackTrace();
		System.out.println("Failed to get timeline: " + te.getMessage());
		System.exit(-1);
		} catch (IOException e) {
			System.out.println("IOEcxeption thrown : " );
			e.printStackTrace();
	}
	return lastStatus;
	


}






		private static boolean colorAlreadyUsed(ReadyMade colorReadyMade) throws IOException {
			boolean isUsed = false;
			
			CSVReader reader = new CSVReader(new FileReader("C:/Users/ankit/workspace/NameGame/resources/TweetedColors.csv"), '\t');
		    
			   String [] nextLine;
			    while ((nextLine = reader.readNext()) != null) {
			        // nextLine[] is an array of values from the line
			      //  System.out.println(nextLine[0] + nextLine[1] + "etc...");
			    	if(nextLine[0].equals(colorReadyMade.color1.stereotype)&&nextLine[1].equals(colorReadyMade.color2.stereotype))
			    	isUsed = true;
			    }
			    
			    if(isUsed && (int)(Math.random()*10)>7) //Toggle with a 30% probabilty
			    	isUsed=false;
			return isUsed;
		}





		private static String createTweet(int personality, ReadyMade colorReadyMade ) {
			String tweet = null;
			switch(personality)
			{
			case 0:  tweet = tweetStanLee(colorReadyMade); 
			break;
			case 1: tweet =   tweetNormal(colorReadyMade);
			break;
			case 2: tweet = tweetUgly(colorReadyMade);
			break;
			default : tweet = tweetStanLee(colorReadyMade);  
			}
			return tweet;
		}





		private static String tweetUgly(ReadyMade colorReadyMade) {
			String tweet = null;
			int rand = (int)(Math.random()*3);
			if(rand == 0){
			 tweet = " I know it doesn't make any sense, but thats life. So "+colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype+" it is ";// +lastStatusEveryColorBot.getText().substring(8).trim()+" RT .@everycolorbot: " + clrStr;
			}
			else if(rand == 1){
				 tweet = "Some colors are too ugly to be named correctly! "+colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype;
				}
			else tweet = "I will call this color " +colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype + "becoz I can !! Now stop pestering me"; 
			return tweet;
		}





		private static String tweetNormal(ReadyMade colorReadyMade) {
			String tweet = null;
			int rand = (int)(Math.random()*3);
			if(rand == 0){
			 tweet = " This colors looks good to me, also the name suits it well. "+colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype;// +lastStatusEveryColorBot.getText().substring(8).trim()+" RT .@everycolorbot: " + clrStr;
			}
			else if(rand == 1){
				 tweet = "You agree with me on the name of this color right! "+colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype;
				}
			else tweet = "Its so good that I can't call the color anything else but " +colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype ;
			return tweet;
		}





		private static String tweetStanLee(ReadyMade colorReadyMade) {
			String tweet = null;
			int rand = (int)(Math.random()*4);
			if(rand == 0){
			 tweet = "New mission! For camouflage I need to color my car as "+colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype+"! ";// +lastStatusEveryColorBot.getText().substring(8).trim()+" RT .@everycolorbot: " + clrStr;
			}
			else if(rand == 1){
				 tweet = "Now since my suit had turned "+colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype+". No one, nothing can stop me! ";
				}
			else if(rand == 2) tweet = "This is what the world needs! " +colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype;
			else tweet = "May be coloring my costume as " +colorReadyMade.color1.stereotype+" "+colorReadyMade.color2.stereotype+ " was not a very good idea!" ;
			
			return tweet;
		}





		private static int randomPersonality() {

			int num = (int)(Math.random()*10);
			if(num < 4)
				return 0; // StanLee personality
			else if(num < 8)
				return 1; // Normal bot personality
				else return 2; // Stupid bot personality
		}





		public static Status ColorTheWorldListerner()
{
	
	
	 Status lastStatus = null;
	 
	try
	{
	 ResponseList<Status> list = tweeterTimeline();
	 
	 
	 for (Status each : list) {
		 if(each.getUser().getScreenName().equals("worldiscolored"))
		 { 
			 //System.out.println("Sent by: @" + each.getUser().getScreenName() + " - " + each.getUser().getName() + "\n" + each.getText() + "\n");
		 	lastStatus = each;
		 	break;
		 }
	 }
	}catch (TwitterException te) {
		te.printStackTrace();
		System.out.println("Failed to get timeline: " + te.getMessage());
		System.exit(-1);
		} catch (IOException e) {
			System.out.println("IOEcxeption thrown : " );
			e.printStackTrace();
	}
		
	return lastStatus;
	


}

	    
	    
	    
	}
