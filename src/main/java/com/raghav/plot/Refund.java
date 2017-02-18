/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raghav.plot;

/**
 *
 * @author raghav
 */
//package org.json;
import com.paytm.merchant.CheckSumServiceHelper;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;
import org.json.JSONException;
import org.json.JSONObject;

public class Refund {

//private static boolean REFID;
    public static void main(String[] args) throws JSONException {
        excutePost("https://pguat.paytm.com/oltp/HANDLER_FF/withdrawScw");
    }

    public static String excutePost(String targetURL) throws JSONException {
        HttpURLConnection connection = null;
        JSONObject jsonRequestObj1 = new JSONObject();
        jsonRequestObj1 = getJsonRequestObject();
        try {
//Create connection
            targetURL = targetURL.concat("?JsonData=");

            String targetURL1 = targetURL.concat(jsonRequestObj1.toString());

            System.out.println(
                    "tearget url==" + targetURL1
            );

            URL url = new URL(targetURL1);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            String urlParameters = jsonRequestObj1.toString();


            connection.setUseCaches(false);
           
            connection.setDoOutput(true);

//Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

//Get Response
            StringBuilder sb = new StringBuilder();
            int HttpResult = connection.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

            System.out.println(
                    "response is =" + sb.toString());

//System.out.println(response.toString());
            return sb.toString();
        } catch (Exception e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "";
    }
    
    
//        public static JSONObject getJsonRequestObject() throws JSONException {
//
//        String CHECKSUM = "";
//
//        TreeMap<String, String> parameters = new TreeMap<String, String>();
//
//        parameters.put("MID", "DDRHea99265480299027");
//        parameters.put("REQUEST_TYPE", "ADD_MONEY");
//        parameters.put("ORDER_ID", "2POIRTY");
//        parameters.put("CUST_ID", "9234584845");
//        parameters.put("TXN_AMOUNT", "10");
//        parameters.put("CHANNEL_ID", "WAP");
//        parameters.put("INDUSTRY_TYPE_ID", "Retail");
//        parameters.put("WEBSITE", "MIMO");
//        parameters.put("SSO_TOKEN", "663bdb78-f024-426f-afab-5620d33cf3a6");
//        parameters.put("MOBILE_NO", "9407202364");
//        parameters.put("EMAIL", "");
//        
//       
//
//        try {
//            CHECKSUM = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum("FUdQQoR3rWcwWdWZ", parameters);
//            System.out.println(CHECKSUM);
//            boolean isChecksumValid = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum("FUdQQoR3rWcwWdWZ", parameters,CHECKSUM);
//         System.out.println(isChecksumValid);
//        }
//        catch (Exception e) {
//            System.out.println("sdsd" + e);
//        }
//
//        JSONObject jsonRequestObj = new JSONObject();
//        jsonRequestObj.put("MID", "DDRHea99265480299027");
//        jsonRequestObj.put("REQUEST_TYPE", "ADD_MONEY");
//        jsonRequestObj.put("ORDER_ID", "2POIRTY");
//        jsonRequestObj.put("CUST_ID", "9234584845");
//        jsonRequestObj.put("TXN_AMOUNT", "10");
//        jsonRequestObj.put("CHANNEL_ID", "WAP");
//        jsonRequestObj.put("INDUSTRY_TYPE_ID", "Retail");
//        jsonRequestObj.put("WEBSITE", "MIMO");
//        jsonRequestObj.put("SSO_TOKEN", "663bdb78-f024-426f-afab-5620d33cf3a6");
//       jsonRequestObj.put("MOBILE_NO", "9407202364");
//        jsonRequestObj.put("EMAIL", "");
//         jsonRequestObj.put("CHECKSUMHASH", CHECKSUM);
//        
//
//        return jsonRequestObj;
//    }
//
    
    public static JSONObject getJsonRequestObject() throws JSONException {

        String CHECKSUM = "";

        TreeMap<String, String> parameters = new TreeMap<String, String>();

        parameters.put("MID", "Yeldi245038328327504");
        parameters.put("ReqType", "WITHDRAW");
        parameters.put("TxnAmount", "1");
        parameters.put("AppIP", "219.91.159.227");
        parameters.put("OrderId", "39A1223");
        parameters.put("Currency", "INR");
        parameters.put("DeviceId", "9407202364");
        parameters.put("SSOToken", "663bdb78-f024-426f-afab-5620d33cf3a6");
        parameters.put("PaymentMode", "PPI");
        parameters.put("CustId", "9234584845");
        parameters.put("IndustryType", "Retail");
        parameters.put("Channel", "WAP");
        parameters.put("AuthMode", "USRPWD");
       
        try {
            CHECKSUM = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum("kbzk1DSbJiV_O3p5", parameters);
            System.out.println(CHECKSUM);
            System.out.println(CHECKSUM.replaceAll("\\+", "%2b"));
            
            boolean isChecksumValid = CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum("kbzk1DSbJiV_O3p5", parameters,CHECKSUM);
         System.out.println(isChecksumValid);
        }
        catch (Exception e) {
            System.out.println("sdsd" + e);
        }

        JSONObject jsonRequestObj = new JSONObject();
        jsonRequestObj.put("MID", "Yeldi245038328327504");
        jsonRequestObj.put("ReqType", "WITHDRAW");
        jsonRequestObj.put("TxnAmount", "1");
        jsonRequestObj.put("AppIP", "219.91.159.227");
        jsonRequestObj.put("OrderId", "39A1223");
        jsonRequestObj.put("Currency", "INR");
        jsonRequestObj.put("DeviceId", "9407202364");
        jsonRequestObj.put("SSOToken", "663bdb78-f024-426f-afab-5620d33cf3a6");
        jsonRequestObj.put("PaymentMode", "PPI");
        jsonRequestObj.put("CustId", "9234584845");
        jsonRequestObj.put("IndustryType", "Retail");
        jsonRequestObj.put("Channel", "WAP");
        jsonRequestObj.put("AuthMode", "USRPWD");
        jsonRequestObj.put("CheckSum", CHECKSUM.replaceAll("\\+", "%2b"));

        return jsonRequestObj;
    }
    
    
}
