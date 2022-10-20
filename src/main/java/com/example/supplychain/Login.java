package com.example.supplychain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
/*
 *  project by Harshal Tambat
 * */

public class Login {
    //                      SHA-256 Hashing algorythem for encryption of password
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // massageDigest class contains many hashing methods in it
        MessageDigest md = MessageDigest.getInstance("SHA-256");  // convert simple password to encrypted hashing value

        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
     //
    static String getEncryptedPassword(String passwordText) throws NoSuchAlgorithmException {
        try{
            BigInteger number = new BigInteger(1, getSHA(passwordText));

            StringBuilder hexString = new StringBuilder(number.toString(16));
//        while (hexString.length())
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static boolean customerLogin(String email, String password)  {
        try{
            DatabaseConnection dbCon = new DatabaseConnection();
            String encryptedPassword = getEncryptedPassword(password);
            String query = String.format("SELECT * FROM customer WHERE email = '%s' AND password = '%s' ", email,encryptedPassword);
                                                                      // %s is place holder ,
            ResultSet rs = dbCon.getQueryTable(query);
            if(rs.next()){
                return  true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
      //  String pass = "123";
       // System.out.println(Login.getEncryptedPassword(pass));
        System.out.println(Login.customerLogin("harshal@gmail.com", "123"));
    }


}
