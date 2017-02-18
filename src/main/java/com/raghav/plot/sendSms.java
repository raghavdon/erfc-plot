/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raghav.plot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author raghav
 */
public class sendSms {
    public static void main(String[] args){
       
        try {
       String url = "https://maker.ifttt.com/trigger/transaction_status/with/key/dBqEIVQ8vax-ILpOI2Ou_O";
            URL object = new URL(url);
            
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            
            JSONObject cred = new JSONObject();

            cred.put("value1", "abc");
            cred.put("value2", "def");
            cred.put("value3", "sdjdj");
            
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(cred.toString());
            wr.flush();
            
             BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
              StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            System.out.println( "response is =" + sb.toString());

        } catch (Exception e) {
           
        } 
        
        
        
    }
    
}
