/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raghav.plot;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Dimension;
import java.io.FileOutputStream;

/**
 *
 * @author raghav
 */
public class ImageIntoPDF {

    public static void main(String[] args) throws Exception {
        Document document = new Document(PageSize.A4);

        PdfWriter writer = PdfWriter.getInstance(document,
                new FileOutputStream("/home/raghav/zzzzz.pdf"));
        document.open();
        document.newPage();

        //for 4 photo
//        for (int i=0;i<2;i++) {
//            Image image1 = Image.getInstance("/home/raghav/Desktop/Firmway-Reco.png");
//            image1.setAbsolutePosition(40,80+(350*i));
//          image1.scaleAbsolute(200, 260);
//            document.add(image1);
//        }
//          for (int i=0;i<2;i++) {
//            Image image1 = Image.getInstance("/home/raghav/Desktop/Firmway-Reco.png");
//            image1.setAbsolutePosition(300, 80+(350*i));
//          image1.scaleAbsolute(260, 200);
//            document.add(image1);
//        }
//for 6

        float margin=10;
        
        float docHeight = ((document.getPageSize().getHeight())/3) -(2*margin);
        float docwidth = ((document.getPageSize().getWidth())/2)- (2*margin);
        

        for (int i = 0; i < 3; i++) {
            Image image1 = Image.getInstance("/home/raghav/myps/i1.jpeg");

            image1.setAbsolutePosition(margin, margin*(i+1)+(docHeight * i));
            image1.scaleToFit(docwidth, docHeight);
            //image1.scaleAbsolute(190, 130);
            document.add(image1);
        }
        for (int i = 0; i < 3; i++) {
            Image image1 = Image.getInstance("/home/raghav/myps/i1.jpeg");
            image1.setAbsolutePosition(docwidth+(2*margin), margin*(i+1)+(docHeight * i));
            image1.scaleToFit(docwidth, docHeight);
            document.add(image1);
        }
        document.close();
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
