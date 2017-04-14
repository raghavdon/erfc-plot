/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raghav.plot;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 *
 * @author raghav
 */
public class ReadDOCX {

    public HashMap<String, String> documentTagsWithValues;
    public long documentVersionId;
    public String htmlContent;

   

    public static void main(String[] args) {
       InputStream in = null;
        String result = "";
        try {
            in = new FileInputStream(new File("/home/raghav/Desktop/Axis-LB.docx"));
            XWPFDocument doc = new XWPFDocument(in);
           
        doc.getParagraphs().stream().map((p) -> p.getRuns()).filter((runs) -> (runs != null)).forEach((runs) -> {
                runs.stream().forEach((r) -> {
                    String text = r.getText(0);
                    System.out.println(text);
                });
            });

            doc.getTables().stream().forEach((tbl) -> {
                tbl.getRows().stream().forEach((row) -> {
                    row.getTableCells().stream().forEach((cell) -> {
                        cell.getParagraphs().stream().forEach((p) -> {
                            p.getRuns().stream().filter((r) -> (r != null)).forEach((r) -> {
                                String text = r.getText(0);
                                if (text != null ) {
                                    System.out.println(text);

                                }
                            });
                        });
                    });
                });
            });

       
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
