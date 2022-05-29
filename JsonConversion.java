package BZA04;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class JsonConversion 
{
   public static void createJson(BZA04[] list) 
   {
//Creating a Json Object
      JSONArray arr = new JSONArray();

      
      for(BZA04 std1:list)
      {
    	  JSONObject jObj = new JSONObject();
    	  
//Putting the key and value in the Json Object
    	  jObj.put("Student Name", std1.StudentName);
    	  jObj.put("Class Name", std1.ClassName);
    	  jObj.put("Staff Name", std1.StaffName);
    	  
    	  arr.add(jObj);
      }
      
//Handling the IOException
      try 
      {
         FileWriter file = new FileWriter("C:\\Users\\USER\\eclipse-workspace\\ConversionToJson\\src\\main\\java\\BZA04\\Output\\Output.json");
         file.write(arr.toJSONString());
         
         file.close();
      } catch (IOException e) 
      {
         e.printStackTrace();
      }
   }
}