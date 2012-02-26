package com.somesky.decodexml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;


public class Decode {
	
	public static void main(String[] args) {
		decode(args);
	}
	
	public static void decode(String[] args) {
		String inputDir;
		String outputDir;
		if(args.length<1){
			System.out.println("Arguments is not legal!");
			return;
		}else if(args.length<2){
			inputDir=args[0];
			outputDir=inputDir+"_decode";
		}else{
			inputDir=args[0];
			outputDir=args[1];
		}
		_decode(inputDir,outputDir);
	}
	
	public static void _decode(String oldPath, String newPath) { 
	       try { 
	           (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
	           File a=new File(oldPath); 
	           String[] file=a.list(); 
	           File temp=null; 
	           for (int i = 0; i < file.length; i++) { 
	               if(oldPath.endsWith(File.separator)){ 
	                   temp=new File(oldPath+file[i]); 
	               } 
	               else{ 
	                   temp=new File(oldPath+File.separator+file[i]); 
	               } 
	               if(temp.isFile()){  
	            	   String fileName=temp.getAbsolutePath();
	            	   if(fileName.endsWith(".xml")){
	            		   String indent=DecodeXml.DecodeAndroidXml(temp.getAbsolutePath());
		                   BufferedWriter buff = new BufferedWriter(new FileWriter(newPath + "/" + 
		                           (temp.getName()).toString()));
		                   for (int j = 0; j < indent.length(); j++) {
		                	      buff.write(indent.charAt(j));
		                	}
		                   buff.close();
	            	   }else{
	            		   FileInputStream input = new FileInputStream(temp); 
	                       FileOutputStream output = new FileOutputStream(newPath + "/" + 
	                               (temp.getName()).toString()); 
	                       byte[] b = new byte[1024 * 5]; 
	                       int len; 
	                       while ( (len = input.read(b)) != -1) { 
	                           output.write(b, 0, len); 
	                           output.flush(); 
	                       } 
	                       output.close(); 
	                       input.close(); 
	            	   }
	                   
	               } 
	               if(temp.isDirectory()){//如果是子文件夹 
	            	   _decode(oldPath+"/"+file[i],newPath+"/"+file[i]); 
	               } 
	           } 
	       } 
	       catch (Exception e) { 
	           System.out.println("复制整个文件夹内容操作出错"); 
	           e.printStackTrace(); 
	       } 
	}
}
