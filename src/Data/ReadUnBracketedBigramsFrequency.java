package Data;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadUnBracketedBigramsFrequency {

	public int searchBigram(UnBracketedBigramsFreq item,Vector<UnBracketedBigramsFreq> ubVector )
	{
		return ubVector.indexOf(item);
	}
	
	public Vector<UnBracketedBigramsFreq> readUnBracketedBigramsFreq(String filename) throws Exception
	{
		if(filename.equals(null)||filename.isEmpty())
			filename = "C:/Users/ankit/workspace/TwitterApp/resources/Veale's unbracketed color bigrams.xlsx";
		
		//FileInputStream file = new FileInputStream(new File("C:/Users/ankit/workspace/TwitterApp/resources/Veale's unbracketed color bigrams.xlsx"));
		FileInputStream file = new FileInputStream(new File(filename));

		//Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		//Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
	
		Vector<UnBracketedBigramsFreq> ubf = new Vector<UnBracketedBigramsFreq>() ;
		
		String word1 = new String(),word2=new String();

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
						UnBracketedBigramsFreq bigram = new UnBracketedBigramsFreq();
						bigram.word1 = word1;
						bigram.word2 = word2;
						bigram.frequency = (int)cell.getNumericCellValue();
						//System.out.println(freq+" ");
						ubf.add(bigram);
					}
					
			}
		
			
		}
		
		return ubf;
	}
		
	
}
