package rishabhdoorstepservice.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import rishabhdoorstepservice.database.DBLoader;


@RestController
public class Mycontroller {

	String ans =null;
	
	@PostMapping("/adminlogin")
	public String login(@RequestParam String username, @RequestParam String password)
	{
		try {
			
			ResultSet rs = DBLoader.executestatment("select*from Admin where user='"+username+"' and password='"+password+"'");
		 
			if(rs.next())
				
			{
				ans =  "success";
			
			}
		
			else
			{
				ans = "fail";
				
			}	
		}
	catch (Exception e) {
		e.printStackTrace();
	}
		return ans;
	}
	
	@PostMapping("/addcategory")
	public String addcategory(@RequestParam String category, @RequestParam String description, @RequestParam MultipartFile f1)
	{
		File f = new File("src/main/resources/static/uploads/"+f1.getOriginalFilename());
		
		String abspath = f.getAbsolutePath();
				
		
		try {
			byte b[] = f1.getBytes();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(b);
			fos.close();
			ResultSet rs = DBLoader.executestatment("select*from category where categoryname='"+category+"'");
		 
			if(rs.next())
				
			{
				ans =  "exist";
			
			}
		
			else
			{
				rs.moveToInsertRow();
				rs.updateString("categoryname", category);
				rs.updateString("description", description);
				rs.updateString("photo", "/uploads/"+f1.getOriginalFilename());
				rs.insertRow();
				
				ans="Success";
				
			}	
		}
	catch (Exception e) {
		e.printStackTrace();
	}
		return ans;
	}
	
}
