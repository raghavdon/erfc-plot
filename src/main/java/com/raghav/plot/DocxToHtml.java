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
            in = new FileInputStream(new File("/home/raghav/StandardChartered-Flat.docx.docx"));
            XWPFDocument document = new XWPFDocument(in);
            XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File("/home/raghav/zzzzAxis-Flat_docxb.html")));
            OutputStream out = new ByteArrayOutputStream();
            XHTMLConverter.getInstance().convert(document, out, options);
            String html = out.toString();
            //String pattern = "_tag_\\[(.*?)\\]";
            String pattern = "_tag_";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(html);
            String htmlCopy = html;
            int index = 0;
            //List<TagModel> tagModels = new ArrayList<>();
//            while (m.find()) {
//
//                System.out.println(m.group());
//                String replaceText = html.substring(m.start(), findTagEndIndex(html, m.end()));
//                // tagModels.add(new TagModel(replaceText, ""));
//
////                htmlCopy = htmlCopy.replace(replaceText, "<span style='border-bottom: 3px solid grey' ng-model='tagModel[" + replaceText + "]' contenteditable='true'  id='" + replaceText + "' ng-mouseover='showCommentContent(\"" + replaceText + "\")' ng-dblclick='showCommentBox(\"" + replaceText + "\")'>" + replaceText + "_</span>");
////                index++;
//
// htmlCopy = htmlCopy.replace(replaceText, "<span contenteditable  strip-br='true' style='border-bottom: 3px solid grey' ng-model='tagModel["+ replaceText +"]'   id='" + replaceText + "' ng-mouseover='showCommentContent(\"" + replaceText + "\")' ng-dblclick='showCommentBox(\"" + replaceText + "\")'>" + replaceText + "_</span>");
////               
//            }

//htmlCopy=htmlCopy.replaceAll("[\bstyle=\"width: pt\b]", "style=\"width: 97%");
           // htmlCopy = htmlCopy.replaceAll("<style>.*?</style>", "");

            htmlCopy = htmlCopy.replaceAll("style=\\\"width:.*?pt", "style=\\\"width:100%");
            
           
            
            
            htmlCopy = htmlCopy.replaceAll("style=\"", "style=\"word-wrap: break-word; ");
            htmlCopy=htmlCopy.replaceAll("", "<td class=\"TableNormal Table3\" style=\"word-wrap: break-word; background-color:#ece8f1;border-top:0.5px solid #000000;border-bottom:0.5px solid #000000;border-left:0.5px solid #000000;border-right:0.5px solid #000000;\">");
//            htmlCopy = htmlCopy.replaceAll("<td class=\"TableNormal Table2\" style.*?\"", "<td ");
//            htmlCopy = htmlCopy.replaceAll("<td class=\"TableNormal Table3\" style.*?\"", "<td ");
           htmlCopy = htmlCopy.replaceAll("margin.*?;", "");
           // htmlCopy = htmlCopy.replaceAll("<td ", "<td style=\"word-break: break-all;\"");
//          htmlCopy = htmlCopy.replaceAll("<td style=\"border:1px solid black;word-break: break-all;\"class=\"TableNormal Table1\">", "<td style=\"word-break: break-all;\"class=\"TableNormal Table1\">");
            htmlCopy = htmlCopy.replaceAll("<body>", "<body><div style=\"margin-left:20px;margin-right:20px\">");
            htmlCopy = htmlCopy.replaceAll("</body>", "</div></body>");
//            
//            htmlCopy = htmlCopy.replaceAll("border-collapse:collapse", "border:1px solid black");
            result = htmlCopy.replaceAll(">", ">\n");

            int ind = 1;

            while (result.contains("<p")) {

                String id = "version_" + documentVersionId + "_" + ind;

                String customText = "<_p id='" + id + "' ng-dblclick='showCommentBox(\"" + id + "\")'";

                ind++;

                result = result.replaceFirst("<p", customText);

            }

            result = result.replaceAll("<_p", "<p");

//            htmlCopy = result;
//            String pattern2 = "<p";
//            Pattern r2 = Pattern.compile(pattern2);
//            Matcher m2 = r2.matcher(htmlCopy);
//            int index = 1;
//            Map<Integer, String> output = new HashMap<>();
//            Map<Integer, Integer> outputIndex = new HashMap<>();
//            List<Integer> matchedIndexes = new ArrayList<>();
//
//            while (m2.find()) {
//
////                System.out.println(m.group());
//                // String replaceText = result.substring(m2.start());
//                String textUptoElement = htmlCopy.substring(0, m2.start());
//                String textAfterElement = htmlCopy.substring(m2.start() + 2, result.length() - 1);
//
//                String customText = "<p contenteditable='true' id='version_" + documentVersionId + "_" + index + "'";
//
//                if (matchedIndexes.stream().noneMatch(mi -> mi == m2.start())) {
//                    matchedIndexes.add(m2.start());
//
////                    if (index == 1) {
////                        output.put(m2.start(), customText);
////                        outputIndex.put(index, m2.start());
////                    } else {
////                        int customTextAddedLength = output.get(outputIndex.get(index - 1)).length();
////                        output.put(m2.start() + customTextAddedLength, customText);
////                    }
//
//                    
//
//                    index++;
//              }
            // htmlCopy = textUptoElement + customText + textAfterElement;
            //           }
            // result = htmlCopy;
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

        Path outputPath = Paths.get("/home/raghav/xyzzst.html");

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
