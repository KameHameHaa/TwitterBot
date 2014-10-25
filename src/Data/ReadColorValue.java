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


public class ReadColorValue {
	public static Vector<ColorValue> colorVectorGreen = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorBrown = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorBlue = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorWhite = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorOrange = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorGrey = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorGray = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorYellow = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorPurple = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorRed = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorBlack = new Vector<ColorValue>();
	public static Vector<ColorValue> colorVectorPink = new Vector<ColorValue>();
	
	
	public int searchBigram(ColorValue item,Vector<ColorValue> colorVector )
	{
		return colorVector.indexOf(item);
	}
	
	public Vector<ColorValue> readColorValue(String filename) throws Exception
	{
		if(filename.equals(null)||filename.isEmpty())
			filename = "C:/Users/ankit/workspace/TwitterApp/resources/Veale's color map.xlsx";
		
		//FileInputStream file = new FileInputStream(new File("C:/Users/ankit/workspace/TwitterApp/resources/Veale's unbracketed color bigrams.xlsx"));
		FileInputStream file = new FileInputStream(new File(filename));

		//Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook workbook = new XSSFWorkbook(file);

		//Get first/desired sheet from the workbook
		XSSFSheet sheet = workbook.getSheetAt(0);

		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
	
		Vector<ColorValue> colorVector = new Vector<ColorValue>() ;
		
		String word1 = new String(),word2=new String();

		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			if(row.getRowNum()==0)
				continue;
			if(row.getRowNum()>=300)
				break;
			
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
						ColorValue cValue = new ColorValue();
						cValue.stereotype = word1;
						cValue.name = word2;
						String s = cell.getStringCellValue();
						cValue.value =  new Color(Integer.decode (s) );
						colorVector.add(cValue);
					
					//Add to colorvector categorized as per the actual color
						if(word2.equals("green"))
							colorVectorGreen.add(cValue);
						else if(word2.equals("brown"))
							colorVectorBrown.add(cValue);
						else if(word2.equals("blue"))
							colorVectorBlue.add(cValue);
						else if(word2.equals("white"))
							colorVectorWhite.add(cValue);
						else if(word2.equals("orange"))
							colorVectorOrange.add(cValue);
						else if(word2.equals("grey"))
							colorVectorGrey.add(cValue);
						else if(word2.equals("gray"))
							colorVectorGray.add(cValue);
						else if(word2.equals("yellow"))
							colorVectorYellow.add(cValue);
						else if(word2.equals("purple"))
							colorVectorPurple.add(cValue);
						else if(word2.equals("red"))
							colorVectorRed.add(cValue);
						else if(word2.equals("black"))
							colorVectorBlack.add(cValue);
						else if(word2.equals("pink"))
							colorVectorPink.add(cValue);
					}
					
			}
		
			
		}
		
		return colorVector;
	}
		
	
}
