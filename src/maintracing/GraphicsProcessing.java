/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintracing;

import java.util.ArrayList;
import raytracing.PointDouble;
import raytracing.SingleSectionData;
import raytracing.SingleTreeData;

/**
 *
 * @author ivan
 */
public class GraphicsProcessing {
    
    public Double maxHorizontalCoord=null;
    public Double maxVerticalCoord=null;
    public Double maxSquareDim=null;
    //size of drawing pane (interface) in pixels
    public Integer maximalPlaneInt=500;
    //reflection data. Calculate once and redraw many times 
    public ArrayList<SingleTreeData> theTreesReflection;
    public SingleSectionData mainwallReflection;
    public raytracing.PointDouble observerReflection; 
    public ArrayList<SingleSectionData> tracingRaysReflection=new ArrayList<>();
    /**
     * find maximal point from data points
     */
    public void findMaximalPoints() {
        maxHorizontalCoord = RayTracing.theGlobalSolver.observerX;
        maxVerticalCoord = RayTracing.theGlobalSolver.observerY;
        for (raytracing.SingleTreeData singleRawTree : maintracing.RayTracing.theGlobalSolver.allTrees) {
            //the maximal coord is located to the right
            if (maxHorizontalCoord==null) {
                maxHorizontalCoord = singleRawTree.treeXCoordinate+singleRawTree.treeRadius;} else {
                if (maxHorizontalCoord<singleRawTree.treeXCoordinate+singleRawTree.treeRadius) {
                    maxHorizontalCoord=singleRawTree.treeXCoordinate+singleRawTree.treeRadius;
                } 
            }
            if (maxVerticalCoord==null) {
                maxVerticalCoord = singleRawTree.treeYCoordinate+singleRawTree.treeRadius;} else {
                if (maxVerticalCoord<singleRawTree.treeYCoordinate+singleRawTree.treeRadius) {
                    maxVerticalCoord=singleRawTree.treeYCoordinate+singleRawTree.treeRadius;
                } 
            }
        }
        if (maxHorizontalCoord<RayTracing.theGlobalSolver.mainwall.getStartXCoord()) {
            maxHorizontalCoord=RayTracing.theGlobalSolver.mainwall.getStartXCoord();
        } 
        if (maxHorizontalCoord<RayTracing.theGlobalSolver.mainwall.getEndXCoord()) {
            maxHorizontalCoord=RayTracing.theGlobalSolver.mainwall.getEndXCoord();
        }
        maxSquareDim = (maxHorizontalCoord > maxVerticalCoord) ? maxHorizontalCoord : maxVerticalCoord;
        /*
        maxHorizontalCoord+=5;
        maxVerticalCoord+=5;
                */
    }
    public raytracing.PointDouble generatePointReflection(Double realPointXCoord, Double realPointYCoord) {
        raytracing.PointDouble drawPoint = new raytracing.PointDouble();
        drawPoint.XCoord=new Double( Math.round(maximalPlaneInt.doubleValue()*realPointXCoord/maxSquareDim) );
        drawPoint.YCoord=new Double( Math.round(maximalPlaneInt.doubleValue()*realPointYCoord/maxSquareDim) );
        return drawPoint;
    }
    public void generateWallReflection() {
        raytracing.PointDouble startPoint = generatePointReflection(RayTracing.theGlobalSolver.mainwall.getStartXCoord(), 
                RayTracing.theGlobalSolver.mainwall.getStartYCoord());
        raytracing.PointDouble endPoint = generatePointReflection(RayTracing.theGlobalSolver.mainwall.getEndXCoord(), 
                RayTracing.theGlobalSolver.mainwall.getEndYCoord());
        mainwallReflection = new SingleSectionData(startPoint.XCoord, startPoint.YCoord, endPoint.XCoord, endPoint.YCoord);
    }
    //calculate wall points on the graph
    public void generateAllReflection() {
        generateWallReflection();
        generateCirclesReflection();
        generateObserverReflection();
    }
    public void generateCirclesReflection() {
        theTreesReflection = new ArrayList<>();
        for (SingleTreeData singleRealTree : RayTracing.theGlobalSolver.allTrees) {
            Double treeReflectedRadius = generatePointReflection(singleRealTree.treeRadius, 0.0).XCoord;
            raytracing.PointDouble treeReflectedPoint = generatePointReflection(singleRealTree.treeXCoordinate, singleRealTree.treeYCoordinate);
            theTreesReflection.add(new SingleTreeData(treeReflectedRadius, treeReflectedPoint.XCoord, treeReflectedPoint.YCoord));
        }
    }
    public void generateObserverReflection() {
        observerReflection = new PointDouble();
        observerReflection = generatePointReflection(RayTracing.theGlobalSolver.observerX, RayTracing.theGlobalSolver.observerY);
    }
    
}

