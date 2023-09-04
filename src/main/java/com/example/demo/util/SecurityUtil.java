package com.example.demo.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {
   
   public static String encryptSHA256(String str) {
	   
      String sha = "";
      try {
         MessageDigest sh = MessageDigest.getInstance("SHA-256");
         sh.update(str.getBytes());
         byte byteData[] = sh.digest();
         StringBuffer sb = new StringBuffer();
         for(int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i]&0xff) + 256, 16).substring(1));
         }
         sha = sb.toString();
      } catch (NoSuchAlgorithmException e) {
         sha = null;
      }
      
      
      return sha;
   }
   
   
   public static void main(String[] args){
       try {
       	
          //System.out.println("encryptSHA256 : "+ encryptSHA256("1234"));
           
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
}