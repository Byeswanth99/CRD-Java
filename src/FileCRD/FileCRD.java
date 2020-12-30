package FileCRD;

import java.io.*;
import java.time.LocalTime;
import jsonlib.*;

public class FileCRD {
	private String filePath;              //address path of file to access
	public FileCRD(String path){          //Parameterized constructor for customized path          
		filePath=path;
		JSONObject obj=new JSONObject();
		try{
			obj.put("","");
			FileWriter file=new FileWriter(filePath,true);
			file.write(obj.toString());
			file.close();
		}
		catch(Exception e){
			System.out.println("**ERROR OCCURED**");
		}
	}
	public FileCRD(){                     //Default constructor for default path
		filePath="C:\\Users\\Lenovo\\Desktop\\FileCRD.JSON";
		JSONObject obj=new JSONObject();
		try{
			FileWriter file=new FileWriter(filePath,true);
			file.write(obj.toString());
			file.close();
		}
		catch(Exception e){
			System.out.println("**ERROR OCCURED**");
		}
	}
	public void create(String key,JSONObject value){         //create method if no ttl is mentioned
		if(key.length()>32) {
			System.out.println("** Error : Key size exceeds maximum **");
			return ;
		}
		else if(value.toString().length()*8>16*1024) {
			System.out.println("** Error : Value size exceeds maximum **");
			return ;
		}
		try {
			FileReader fr=new FileReader(filePath);
			JSONTokener ftoken=new JSONTokener(fr);
			JSONObject jfile=new JSONObject(ftoken);
			if(jfile.has(key)) {
				System.out.println("**Error : KEY exists, Duplicate keys not allowed**");
				return ;
			}
			JSONArray jarr=new JSONArray();
			jarr.put(value);
			jarr.put(Integer.MAX_VALUE);
			LocalTime time=LocalTime.now();
			int timeStamp=time.toSecondOfDay();
			jarr.put(timeStamp);
			jfile.put(key, jarr);
			FileWriter fw=new FileWriter(filePath);
			fw.write(jfile.toString());
			fw.close();
		}
		catch(Exception e) {
			System.out.println("**Error Occurred");
		}
	}
	
	public void create(String key,JSONObject value,int ttl){     //overloading create method for mentioning ttl parameter
		if(key.length()>32) {
			System.out.println("** Error : Key size exceeds maximum **");
			return ;
		}
		else if(value.toString().length()*8>16*1024) {
			System.out.println("** Error : Value size exceeds maximum **");
			return ;
		}
		try {
			FileReader fr=new FileReader(filePath);
			JSONTokener ftoken=new JSONTokener(fr);
			JSONObject jfile=new JSONObject(ftoken);
			if(jfile.has(key)) {
				System.out.println("**Error : KEY exists, Duplicate keys not allowed**");
				return ;
			}
			JSONArray jarr=new JSONArray();
			jarr.put(value);
			jarr.put(ttl);
			LocalTime time=LocalTime.now();
			int timeStamp=time.toSecondOfDay();
			jarr.put(timeStamp);
			jfile.put(key, jarr);
			FileWriter fw=new FileWriter(filePath);
			fw.write(jfile.toString());
			fw.close();
		}
		catch(Exception e){
			System.out.println("** Error Occured**");
		}
	}
	public JSONObject read(String key){             //read method for returning value of given key
		try {
			FileReader fr=new FileReader(filePath);
			JSONTokener ftoken=new JSONTokener(fr);
			JSONObject jfile=new JSONObject(ftoken);
			if(jfile.has(key)) {
				JSONArray jarr=jfile.getJSONArray(key);
				LocalTime time=LocalTime.now();
				int curTime=time.toSecondOfDay();
				if(curTime-jarr.getInt(2)<jarr.getInt(1))
					return jarr.getJSONObject(0);
				else
					System.out.println("**Error : Key's Time To Live Expired**");
			}
			else {
				System.out.println("**Error : Invalid Key**");
			}
		}
		catch (Exception e) {
            System.out.println("**Error : File Not Found**");
        }
		return null;
	}
	public void delete(String key){                //delete method to delete an entry of specific key if exists
		try {
			FileReader fr=new FileReader(filePath);
			JSONTokener ftoken=new JSONTokener(fr);
			JSONObject jfile=new JSONObject(ftoken);
			if(jfile.has(key)) {
				JSONArray jarr=jfile.getJSONArray(key);
				LocalTime time=LocalTime.now();
				int curTime=time.toSecondOfDay();
				if(curTime-jarr.getInt(2)<jarr.getInt(1))
					jfile.remove(key);
				else {
					System.out.println("**Error : Key's Time To Live Expired**");
					return ;
				}
			}
			else {
				System.out.println("**Error : Invalid Key**");
				return ;
			}
			FileWriter fw=new FileWriter(filePath);
			fw.write(jfile.toString());
			fw.close();
		}
		catch(Exception E) {
			System.out.println("** Error Occured**");
		}
	}
}
