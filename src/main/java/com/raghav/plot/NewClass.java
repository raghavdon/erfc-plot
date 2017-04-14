/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raghav.plot;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Manas
 */
public class NewClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        fixWordDocumentForHtmlConversion();
    }

    private static void fixWordDocumentForHtmlConversion() {
        try {
            InputStream in = null;
            in = new FileInputStream(new File("/home/raghav/EnvisioDevs/Axis-Flat_final.html"));
            BufferedInputStream bis = new BufferedInputStream(in);
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            int result = bis.read();
            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
            String html = buf.toString();

            //String starReplacedHtml = html.replace("*", "-");
            //String divWidthFixedHtml = starReplacedHtml.replace("width:595.0pt;", "width:100%;");
            //String hrFixedHtml = divWidthFixedHtml.replace("<hr>", "");
            String brFixedHtml = html.replaceAll("<br>", "<br/>");
            brFixedHtml = brFixedHtml.replaceAll("\n", " ");
            brFixedHtml = brFixedHtml.replaceAll("\r", " ");
            String lineEndFixedHtml = brFixedHtml.replaceAll("\n", "");
            lineEndFixedHtml=lineEndFixedHtml.replaceAll("  ", " ");
            lineEndFixedHtml = lineEndFixedHtml.replaceAll("span   class", "span class");
             lineEndFixedHtml = lineEndFixedHtml.replaceAll("span   style", "span style");
            lineEndFixedHtml = lineEndFixedHtml.replaceAll("<span style='mso-spacerun:yes'></span>", "");
           
            String slash96Fixed = lineEndFixedHtml.replaceAll("/96", "");
            String slash91Fixed = slash96Fixed.replaceAll("/91", "");
            String slash92Fixed = slash91Fixed.replaceAll("/92", "");
            String slashE0Fixed = slash92Fixed.replaceAll("/E0", "");
            String slashB0Fixed = slashE0Fixed.replaceAll("/B0", "");
            String slashA0Fixed = slashB0Fixed.replaceAll("/A0", "");

            //Search for <span class=SpellE>
            String pattern = "(?i)<span class=SpellE[^>]*>";

            // Create a Pattern object
            Pattern r = Pattern.compile(pattern);

            
            
            String stringResult = findNext(slashA0Fixed, r);
            
            
            String pattern2 = "(?i)<span class=GramE[^>]*>";

            // Create a Pattern object
            Pattern r2 = Pattern.compile(pattern2);

            stringResult = findNext(stringResult, r2);
            
            
            
            
            
            
            

            int ind = 1;

            while (stringResult.contains("<p")) {

                String id = "version_" + 0 + "_" + ind;

                String customText = "<_p id='" + id + "' ng-dblclick='showCommentBox(\"" + id + "\")'";

                ind++;

                stringResult = stringResult.replaceFirst("<p", customText);

            }

            stringResult = stringResult.replaceAll("<_p", "<p");
            stringResult = stringResult.replaceAll("<img.*?>", "");
            stringResult = stringResult.replaceAll("</img>", "");
             stringResult = stringResult.replaceAll("�", "");
             stringResult = stringResult.replaceAll("_<", "<");
            stringResult = stringResult.replaceAll(">tag", ">_tag");
            stringResult = stringResult.replaceAll("__tag", "_tag");

            stringResult = stringResult.replaceAll(">", ">\n");
            

            // System.out.println(stringResult);
            Path outputPath = Paths.get("/home/raghav/xxxxxyxAxis.html");

            try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
                writer.append(stringResult);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // Now create matcher object.
//            Matcher m = r.matcher(slashA0Fixed);
//            if (m.find()) {
//                System.out.println("Found value: " + m.group(0));
//                
//                
//                //System.out.println("Found value: " + m.group(1));
//                //System.out.println("Found value: " + m.group(2));
//            }
            //String pattern2 = "(?i)<span class=SpellE[^>]*>";"
//            if (m.find()) {
//                System.out.println("Found value: " + m.group(0));
//                System.out.println("Found value: " + m.group(1));
//                System.out.println("Found value: " + m.group(2));
//            } else {
//                System.out.println("NO MATCH");
//            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static String findNext(String newHtml, Pattern pattern) {
        Matcher m = pattern.matcher(newHtml);
        String modifiedHtml = "";
        if (m.find()) {
            String htmlTillMatch = newHtml.substring(0, m.start());
            String textBetweenTag = newHtml.substring(m.start() + m.group().length(), findTagEndIndex(newHtml, m.end()));
            String htmlAfteTag = newHtml.substring(m.end() + textBetweenTag.length() + "</span>".length());
            modifiedHtml = htmlTillMatch + textBetweenTag + htmlAfteTag;

            modifiedHtml = findNext(modifiedHtml, pattern);
        }

        return modifiedHtml.equals("") ? newHtml : modifiedHtml;
    }

    private static int findTagEndIndex(String html, int tagIndex) {
        return html.indexOf("</span", tagIndex);
    }

}
