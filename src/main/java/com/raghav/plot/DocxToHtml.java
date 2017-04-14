/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raghav.plot;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author raghav
 */
public class DocxToHtml {

    public HashMap<String, String> documentTagsWithValues;
    public long documentVersionId;
    public String htmlContent;

    public String convertDocToHtml(long documentVersionId) {

        // DocumentVersion documentVersion = documentVersionService.getDocumentVersion(documentVersionId);
        InputStream in = null;
        String result = "";
        try {
            in = new FileInputStream(new File("/home/raghav/Desktop/Axis-Flat.docxr.docx"));
            XWPFDocument document = new XWPFDocument(in);
            XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File("/home/raghav/yzzzzAxis-Flat_docxb.html")));
            OutputStream out = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, out, options);
            String html = out.toString();
            //String pattern = "_tag_\\[(.*?)\\]";
            String pattern = "_tag_";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(html);
            String htmlCopy = html;
            int index = 0;

            htmlCopy = htmlCopy.replaceAll("</table>", "</table><br>");

            htmlCopy = htmlCopy.replaceAll("<style>.*?</style>", "");
            htmlCopy = htmlCopy.replaceAll("style=\\\"width:.*?pt", "style=\\\"width:100%");

            htmlCopy = htmlCopy.replaceAll("style=\"", "style=\"word-wrap: break-word; ");

           // String[] bd = {"Table3", "Table5", "Table8"};
            String[] bd = {};

            for (int o = 0; o < bd.length; o++) {
                htmlCopy = htmlCopy.replaceAll("<td class=\"TableNormal " + bd[o] + "\" style=\".*?\"", "<td class=\"TableNormal " + bd[o] + "\"");
                htmlCopy = htmlCopy.replaceAll("<td class=\"TableNormal " + bd[o] + "\"", "<tid align=\"left\" class=\"TableNormal " + bd[o] + "\" style=\"padding:10px 10px 10px 10px;border:1px solid black;word-break: break-all;\"");

            }
           // htmlCopy = htmlCopy.replaceAll("<td ", "<td align=\"left\" style= \"padding:10px 10px 10px 0px;\" ");

            htmlCopy = htmlCopy.replaceAll("<tid ", "<td ");
            htmlCopy = htmlCopy.replaceAll("margin.*?;", "");
            htmlCopy = htmlCopy.replaceAll("<body>", "<body><div style=\"margin-left:20px;margin-right:20px\">");
            htmlCopy = htmlCopy.replaceAll("</body>", "</div></body>");

            result = htmlCopy.replaceAll(">", ">\n");

            int ind = 1;

            while (result.contains("<p")) {

                String id = "version_" + documentVersionId + "_" + ind;

                String customText = "<_p id='" + id + "' ng-dblclick='showCommentBox(\"" + id + "\")'";

                ind++;

                result = result.replaceFirst("<p", customText);

            }

            result = result.replaceAll("<_p", "<p");


        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        Path outputPath = Paths.get("/home/raghav/yxAxis.html");

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            writer.append(result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;

    }

    private int findTagEndIndex(String html, int tagIndex) {
        return html.indexOf("</span", tagIndex);
    }

    public static void main(String[] args) {
        DocxToHtml obj = new DocxToHtml();
        obj.convertDocToHtml(0);
    }
}

class TagModel {

    public String name;
    public String value;

    public TagModel(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public TagModel() {
    }

}


// public static void main(String[] args) {
//        String s="<span class=SpellE>sft</span>ooooo<span class=SpellE>sft</span>ppp<span class=SpellE>sft</span>";
//        String t = s.replaceAll("<span[^</span>]*>", "*");
//        System.out.println(t);
//        
////        BufferedReader br = new BufferedReader(new FileReader("/home/raghav/Desktop/Axis-Flat-new.html"));
////        StringBuilder sb = new StringBuilder();
////        try {
////            
////            String line = br.readLine();
////
////            while (line != null) {
////                sb.append(line);
////                sb.append("\n");
////                line = br.readLine();
////            }
////            
////        } finally {
////            br.close();
////        }
////        
////    String s=sb.toString().replace("\n", "");
////    
////        String t = s.replaceAll("_<span[^</span>]*</span>", "");
////        //System.out.println(t);
////        t=t.replaceAll(">", ">\n");
////        t=t.replaceAll(";", ";\n");
////
////        Path outputPath = Paths.get("/home/raghav/xxxxxyxAxis.html");
////
////        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
////            writer.append(t);
////        } catch (Exception ex) {
////            ex.printStackTrace();
////        }
//
//       
//    }