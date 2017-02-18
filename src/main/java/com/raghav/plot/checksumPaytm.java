/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raghav.plot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paytm.merchant.CheckSumServiceHelper;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raghav
 */
public class checksumPaytm {
    public static void main(String[] args){
            TreeMap<String,String> parameters = new TreeMap<String,String>();

   parameters.put("MID", "Yeldi245038328327504");
            parameters.put("ReqType", "WITHDRAW");
            parameters.put("TxnAmount", "1");
            parameters.put("AppIP", "54.173.65.38");
            parameters.put("OrderId", "2POIR1");
            parameters.put("Currency", "INR");
            parameters.put("DeviceId", "9407202364");
            parameters.put("SSOToken", "663bdb78-f024-426f-afab-5620d33cf3a6");
            parameters.put("PaymentMode", "PPI");
            parameters.put("CustId", "9234584845");
            parameters.put("IndustryType", "Retail");
            parameters.put("Channel", "WAP");
            parameters.put("AuthMode", "USRPWD");
            
            
         

            
            
        CheckSumServiceHelper helper=CheckSumServiceHelper.getCheckSumServiceHelper();
        
        String checkSum="";
        try {
            checkSum = helper.genrateCheckSum("kbzk1DSbJiV_O3p5", parameters);
             parameters.put("CheckSum", checkSum.replaceAll("+", "%2b"));
        } catch (Exception ex) {
            Logger.getLogger(checksumPaytm.class.getName()).log(Level.SEVERE, null, ex);
        }
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	
        System.out.println(gson.toJson(parameters));
    }
}
