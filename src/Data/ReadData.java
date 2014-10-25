package Data;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadData {
	

	public static Vector<UnBracketedBigramsFreq> biGramFrequency = new Vector<UnBracketedBigramsFreq>() ;
	public static Vector<ColorValue> colorVector = new Vector<ColorValue>();
	public static Vector<PluralBigramsFrequency> pluralBigramsFrequency = new Vector<PluralBigramsFrequency>();
	public static Vector<ColorCodeURL> colorURL = new Vector<ColorCodeURL>();

	
	
	public static ReadColorValue readColorVector = new ReadColorValue();
	public static ReadUnBracketedBigramsFrequency readBigram = new ReadUnBracketedBigramsFrequency();
	
	public   ReadData(String filename) throws Exception
	{
		ReadPluralBigramsFrequency(filename);
		ReadURL tempURLReader = new ReadURL();
		
		
		colorURL=	tempURLReader.readColorCodeURL(filename);
		biGramFrequency = readBigram.readUnBracketedBigramsFreq(filename);
		colorVector = readColorVector.readColorValue(filename);
				
	}
	public static void ReadPluralBigramsFrequency(String filename) throws Exception
	{
			if(filename.equals(null)||filename.isEmpty())
			filename = "C:/Users/ankit/workspace/TwitterApp/resources/Veale's plural color bigrams.xlsx";
		
		//FileInputStream file = new FileInputStream(new File("C:/Users/ankit/workspace/TwitterApp/resources/Veale's unbracketed color bigrams.xlsx"));
		FileInputStream file = new FileInputStream(new File(filename));

		//Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		//Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
	
		String word1 = new String(),word2=new String(),word3 = new String();
		int Freq = 0;
		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			if(row.getRowNum()==0)
				continue;
			//For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			
			
			while (cellIterator.hasNext()) 
			{
				
				Cell cell = cellIterator.next();
					if(cell.getColumnIndex()==0)
					{	
					
						word1 = cell.getStringCellValue();
						//System.out.print(word1+" ");
					}	
					else if (cell.getColumnIndex()==1)
					{
						word2 = cell.getStringCellValue();						
						//System.out.println(word2+" ");
					}
					else if (cell.getColumnIndex()==2)
					{	
						Freq = (int)cell.getNumericCellValue();
					}
					else if (cell.getColumnIndex()==3)
					{
						word3 = cell.getStringCellValue();
						
						PluralBigramsFrequency PluralBiGram = new PluralBigramsFrequency();
						PluralBiGram.word1 = word1;
						PluralBiGram.word2 = word3;
						PluralBiGram.word2Plural = word2;
						PluralBiGram.frequency = Freq;
						
						pluralBigramsFrequency.add(PluralBiGram);
					}
					
			}	
		}	
	
	}
	public static void colorAndBigrams(String filename) throws Exception
	{
		String filenameUnBracketedBigrams = new String();
		


	
		
	}

}
