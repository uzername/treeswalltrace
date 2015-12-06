/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintracing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author ivan
 */
class MyPanel extends JPanel {

    public MyPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        //System.out.println("MyPanel initialized");
    }

    public Dimension getPreferredSize() {
        return new Dimension(500,700);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        System.out.println("REPAINTING");
        //draw wall
        if (RayTracing.dataInitiated) {
        g.drawLine(RayTracing.theGlobalGraphics.mainwallReflection.getStartXCoord().intValue(), 
                   RayTracing.theGlobalGraphics.mainwallReflection.getStartYCoord().intValue(), 
                   RayTracing.theGlobalGraphics.mainwallReflection.getEndXCoord().intValue(), 
                   RayTracing.theGlobalGraphics.mainwallReflection.getEndYCoord().intValue());
        //draw all circles
        for (raytracing.SingleTreeData theImageTree : maintracing.RayTracing.theGlobalGraphics.theTreesReflection) {
            int radius = theImageTree.treeRadius.intValue();
            int x = theImageTree.treeXCoordinate.intValue();
            int y = theImageTree.treeYCoordinate.intValue();
            int diameter = radius * 2;
            g.drawOval(x-radius, y-radius, diameter, diameter);
        }
        g.drawOval(RayTracing.theGlobalGraphics.observerReflection.XCoord.intValue()-3, 
                RayTracing.theGlobalGraphics.observerReflection.YCoord.intValue()-3, 
                3, 
                3);
        }
        
    }  
}
