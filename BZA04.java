package BZA04;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import BZA04.JsonConversion;

public class BZA04 
{  
	String StudentName;
	String ClassName;
	String StaffName;	
    
	public BZA04(String studentName, String className, String staffName) 
	    {
		StudentName = studentName;
		ClassName = className;
		StaffName = staffName;
		}
	public String toString()
	    {
		return "StudentName "+this.StudentName+" ClassName "+this.ClassName+" StaffName "+this.StaffName;
	    }
	

	public static void main(String[] args) throws FileNotFoundException //Handling the FileNotFoundException
	{
		String SDFilePath="C:\\Users\\USER\\eclipse-workspace\\ConversionToJson\\src\\main\\java\\BZA04\\CSV_Files\\Student_Data.csv";
		String CNFilePath="C:\\Users\\USER\\eclipse-workspace\\ConversionToJson\\src\\main\\java\\BZA04\\CSV_Files\\ClassName_Table.csv";
		String SNFilePath="C:\\Users\\USER\\eclipse-workspace\\ConversionToJson\\src\\main\\java\\BZA04\\CSV_Files\\StaffName_Table.csv";

	    String[][] SDTable=CSVreader(SDFilePath);
	    String[][] CNTable=CSVreader(CNFilePath);
	    String[][] SNTable=CSVreader(SNFilePath);

//Remove undefined character(Example: "?Student Id" --> "Student Id")
	    SDTable[0][0]=SDTable[0][0].substring(NoOfUndefinedChar(SDTable[0][0]));
	    CNTable[0][0]=CNTable[0][0].substring(NoOfUndefinedChar(CNTable[0][0]));
	    SNTable[0][0]=SNTable[0][0].substring(NoOfUndefinedChar(SNTable[0][0]));
	    	    
	    for(int i=1; i<SDTable.length; i++)
	    {
	    	for(int j=0; j<SDTable[0].length;j++)
	    	{
	    		if(SDTable[0][j].equals("Class Id"))
	    		{
	    			SDTable[i][j]=Get(CNTable, SDTable[i][j], "Class Id", "Class Name");
	    		}
	    		if(SDTable[0][j].equals("Staff Id"))
	    		{	
	    			SDTable[i][j]=Get(SNTable, SDTable[i][j], "Staff Id", "Staff Name");
	    		}
	    	}
	    } 
        SDTable[0][2]="Class Name";
        SDTable[0][3]="Staff Name";
        
	    BZA04[] Obj= new BZA04[SDTable.length];
	    for(int i=0; i<SDTable.length; i++)
	    {
    	Obj[i]= new BZA04(SDTable[i][1], SDTable[i][2], SDTable[i][3]);
	    
    	System.out.println(Obj[i]);
	    }
	    
//Calling the JsonConversion.createJson() to create Json File
	    JsonConversion.createJson(Obj);
	}
	
//To Find the No. of Rows and Columns	
		static int[] RowColumn_count(Scanner sc1)
		{
			int row=0;
			int column=0;
			int[] RowColumn_count= {0,0};
			
		while(sc1.hasNext())
		  {
			String st=sc1.nextLine();
			String[] arr=st.split(",");
		    
			column=arr.length;
	        row++;
		  }
		RowColumn_count[0]=row;
		RowColumn_count[1]=column;	
		
		return RowColumn_count;
		}
		
//Method to read and assign the values from CSV file to Array
		static String[][] CSVreader(String FilePath) throws FileNotFoundException //Handling the FileNotFoundException
		{
			Scanner sc1 = new Scanner(new File(FilePath));
			
			int row_count =0;
			int column_count=0;		
			int[] row_column=RowColumn_count(sc1);
			
			row_count=row_column[0];
			column_count=row_column[1];
			
			sc1 = new Scanner(new File(FilePath));
			String[][] arrs= new String[row_count][column_count];
			
			for(int i=0; i<row_count; i++)
			   {
				String St=sc1.nextLine();
				String[] Line=St.split(",");
				
				for(int j=0; j<column_count;j++)
				{
					arrs[i][j]=Line[j];
				}
			   }
			return arrs;	
		}
		
//Method to retrieve data from another table
	     static String Get(String[][] Table, String value1, String column1, String column2)
	     {
	    	 String value2="";
	    	 
	    	 int column1Index= indexOf(Table, column1);
	    	 int column2Index= indexOf(Table, column2);
	    	 
	    	 for(int i=0; i<Table.length; i++)
	    	 {
	    				 if(value1.equals(Table[i][column1Index]))
	 			     	 {
	    					 value2=Table[i][column2Index];
	 			  	     }	
	    	 }    			 
	    	 return value2;			
	     }

//Method to find the index of the column
	     static int indexOf(String[][] Table,String columnName)
	     
	     {   int index=-1;
	    	 for(int j=0; j<Table[0].length; j++)
	    	 {
	    		 if(Table[0][j].equals(columnName))
	    		 {
	    			index=j; 
	    		 }
	    	 }
	    	 return index;
	     }

//Method to find the no. of undefined Characters
	     static int NoOfUndefinedChar(String s)
	 	{
	 	     char[] ch = s.toCharArray();
	 	     boolean undefinedChar = true;	
	 	     int count = 0; 

	 	     while(undefinedChar)
	 	     {
	 	    	 for(int i = 0; i < ch.length; i++)
	 	    	 {
	 	    		 for(int j = 0; j < 26; j++)
	 		    	 {
	 	    	
	 		    		 if(ch[i] == (char)('A' + j) || ch[i] == (char)('a' + j))
	 		    		 {		
	 		    			 undefinedChar = false;
	 		    			 break;
	 		    		 }
	 		    	 }
	 	    		 if(!undefinedChar)
	 	    		 {
	 	    			 break;
	 	    		 }
	 		    	 count++;
	 	    	 }
	 	     }
	 	     return count;  
	 	}
}
