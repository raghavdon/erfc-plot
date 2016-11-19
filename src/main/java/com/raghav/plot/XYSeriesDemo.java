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
import org.apache.commons.math3.special.Erf;
import org.jfree.chart.ChartFactory;
 import org.jfree.chart.ChartPanel;
 import org.jfree.chart.JFreeChart;
 import org.jfree.chart.plot.PlotOrientation;
 import org.jfree.data.xy.XYSeries;
 import org.jfree.data.xy.XYSeriesCollection;
 import org.jfree.ui.ApplicationFrame;
 import org.jfree.ui.RefineryUtilities;


public class XYSeriesDemo extends ApplicationFrame {

/**
 * A demonstration application showing an XY series containing a null value.
 *
 * @param title  the frame title.
 */
public XYSeriesDemo(final String title) {

    super(title);
    final XYSeries series = new XYSeries("Random Data");
//    float x=1;
//    float y=(float)((Math.sqrt(2))*x);
//    
   

    for(int i=1;i<200000;i++){
        double x=(((double)(i))/1000);
        System.out.print("x = "+x);
        double xdb= 10*(Math.log10(x));
        System.out.print("\t 10logx="+xdb);
      
double y = Erf.erfc(Math.sqrt(x));
System.out.print("\t y="+y);
        System.out.println("----------------------");
    series.add(xdb, y);
    }

    final XYSeriesCollection data = new XYSeriesCollection(series);
    final JFreeChart chart = ChartFactory.createXYLineChart(
        "XY Series Demo",
        "X", 
        "Y", 
        data,
        PlotOrientation.VERTICAL,
        true,
        true,
        false
    );

    final ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
    setContentPane(chartPanel);

}

// ****************************************************************************
// * JFREECHART DEVELOPER GUIDE                                               *
// * The JFreeChart Developer Guide, written by David Gilbert, is available   *
// * to purchase from Object Refinery Limited:                                *
// *                                                                          *
// * http://www.object-refinery.com/jfreechart/guide.html                     *
// *                                                                          *
// * Sales are used to provide funding for the JFreeChart project - please    * 
// * support us so that we can continue developing free software.             *
// ****************************************************************************

/**
 * Starting point for the demonstration application.
 *
 * @param args  ignored.
 */
public static void main(final String[] args) {

    final XYSeriesDemo demo = new XYSeriesDemo("XY Series Demo");
    demo.pack();
    RefineryUtilities.centerFrameOnScreen(demo);
    demo.setVisible(true);

}

}