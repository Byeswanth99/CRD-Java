import FileCRD.*;
import jsonlib.*;
public class test {
	public static void main(String[] args) throws JSONException {
		JSONObject obj=new JSONObject();
		//default path
		FileCRD file=new FileCRD();
		obj.put("name", "yeswanth");
		file.create("ID1",obj);
		System.out.println(file.read("ID1"));
		file.delete("ID1");
		
		//customized path
		file=new FileCRD("D:\\file.JSON");
		obj.put("name", "yeswanth");
		file.create("ID1",obj);
		System.out.println(file.read("ID1"));
		file.delete("ID1");
		
		//default path with time to live value
		file=new FileCRD();
		obj.put("name", "yeswanth");
		file.create("ID1",obj,500);
		System.out.println(file.read("ID1"));
		file.delete("ID1");
		
		//customized path with time to live value
		file=new FileCRD("D:\\file.JSON");
		obj.put("name", "yeswanth");
		file.create("ID1",obj,500);
		System.out.println(file.read("ID1"));
		file.delete("ID1");
	}
}
