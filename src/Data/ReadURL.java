package Data;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadURL {
	public Vector<ColorCodeURL> readColorCodeURL(String filename) throws Exception
	{
		if(filename.equals(null)||filename.isEmpty())
			filename = "C:/Users/ankit/workspace/TwitterApp/resources/everycolorbot tweets.xlsx";
		
		//FileInputStream file = new FileInputStream(new File("C:/Users/ankit/workspace/TwitterApp/resources/Veale's unbracketed color bigrams.xlsx"));
		FileInputStream file = new FileInputStream(new File(filename));

		//Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		//Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
	
		Vector<ColorCodeURL> ubf = new Vector<ColorCodeURL>() ;
		
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
						ColorCodeURL url = new ColorCodeURL();
						url.URL=cell.getStringCellValue();
						url.value = new Color(Integer.decode (word1) );
						
						ubf.add(url);
					}
					
			}
		
			
		}
		
		return ubf;
	}
	
}
