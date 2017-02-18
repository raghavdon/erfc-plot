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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class ConvertWord {

    private static final String docName = "ald_bank.docx";
    private static final String outputlFolderPath = "/home/raghav/";

    String htmlNamePath = "ald_bank6.html";
    String zipName = "_tmp.zip";
    File docFile = new File(outputlFolderPath + docName);
    File zipFile = new File(zipName);

    public void ConvertWordToHtml() {

        try {

            // 1) Load DOCX into XWPFDocument
            InputStream doc = new FileInputStream(new File(outputlFolderPath + docName));
            System.out.println("InputStream" + doc);
            XWPFDocument document = new XWPFDocument(doc);

            // 2) Prepare XHTML options (here we set the IURIResolver to load images from a "word/media" folder)
            XHTMLOptions options = XHTMLOptions.create(); //.URIResolver(new FileURIResolver(new File("word/media")));;

            // Extract image
            String root = "target";
            File imageFolder = new File(root + "/images/" + doc);
            options.setExtractor(new FileImageExtractor(imageFolder));
            // URI resolver
            options.URIResolver(new FileURIResolver(imageFolder));

            OutputStream out = new FileOutputStream(new File(htmlPath()));
            XHTMLConverter.getInstance().convert(document, out, options);

            System.out.println("OutputStream " + out.toString());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConvertWord cwoWord = new ConvertWord();
        cwoWord.ConvertWordToHtml();
        System.out.println("done");
    }

    public String htmlPath() {
        // d:/docHtml.html
        return outputlFolderPath + htmlNamePath;
    }

    public String zipPath() {
        // d:/_tmp.zip
        return outputlFolderPath + zipName;
    }

}
