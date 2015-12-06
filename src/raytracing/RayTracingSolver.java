/*
 * Use the raytracing algorithm from algolist.manual.ru/graphics/delinvis.php
 * Also see http://www.redblobgames.com/articles/visibility/
 */
package raytracing;

import java.util.ArrayList;

/**
 * @author ivan
 */
public class RayTracingSolver {
    RayTracingSolver theSolver;
    public void initRayTracer() {
        theSolver = new RayTracingSolver();
    }
    /**
     * all trees in the single array
     */
    public ArrayList<SingleTreeData> allTrees;
    /**
     * the wall is a subject of our task
     */
    public SingleSectionData mainwall;
    public Double wallB;
    public Double wallK;
    /**
     * the solution of the task - contains all subsections of the wall which are visible.
     * They should not be overlapping by the architecture of the solution
     */
    public ArrayList<SingleSectionData> solutionTask;
    
    public Double observerX=0.0;
    public Double observerY=0.0;
    
    //the view cone. it is defined by the user
    public Double alphaStartRadians=0.0;
    public Double alphaEndRadians=2.0*Math.PI;
    private Double alphaThreshold=5e-2;
    //current angle
    private Double alphaCurrRadians=alphaStartRadians;
    /**
     * define trees data in a hardcoded manner. Writes to allTrees array
     */
    public void defineStaticTrees() {
        allTrees = new ArrayList<>();
        allTrees.add(new SingleTreeData(2.0,45.0,13.0));
        allTrees.add(new SingleTreeData(1.5,17.5,4.25));
        allTrees.add(new SingleTreeData(2.5,26.5,15.0));
        allTrees.add(new SingleTreeData(2.0,30.0,25.0));
        allTrees.add(new SingleTreeData(2.5,35.0,30.0));
    }
    /**
     * define wall in the hardcoded manner. 
     */
    public void defineStaticWall(){
        mainwall=new SingleSectionData(20.0, 20.0, 35.0, 45.0);
        wallK=(mainwall.getEndYCoord()-mainwall.getStartYCoord())/(mainwall.getEndXCoord()-mainwall.getStartXCoord());
        wallB=mainwall.getStartYCoord()-wallK*mainwall.getStartXCoord();
    }
    /**
     * define observer's coordinates in the static manner
     */
    public void defineStaticObserver() {
        observerX = 27.5;
        observerY = 0.0;
    }
    /**
     * the main subroutine for the task. It finds visible segments of the wall.
     * returns nothing, because it writes to solutionTask. 
     * All data definitions should be done beforehand
     */
    public void performRaytracing() {
        
        //During raytracing we may find that ray intersects several objects, for example:
        //several circles-trees in front of the wall and several circles-trees at back, as the wall by itself.
        //it means that we should check intersections with all objects on scene and 
        //find the closest (to the observer) intersection point with certain object
        //(and probably save it somehow: both the closest coordinate and the object)
        while (alphaCurrRadians<alphaEndRadians) {
            alphaCurrRadians+=alphaThreshold;
            //-1 = intersection with Wall; other number = index of Tree in array
            Integer intersectionIndex=null;
            //The scene object which is located the most close to the observer
            PointDouble intersectPoint=null;
            System.out.println("==>Raytracing for the ray with current angle:"+alphaCurrRadians.toString());
            if ( (alphaCurrRadians>=0)&&(alphaCurrRadians<=Math.PI) ) {
                //find intersection with all Tree Objects above the point of observation
                Integer treeIndex=0;
                for (SingleTreeData theTree : allTrees) {
                    if (observerY<=theTree.treeYCoordinate+theTree.treeRadius) {
                        Boolean intersectRes=findIntersection(theTree);
                        System.out.println("Tree Above Observer:"+theTree.toString()+"|"+intersectRes);
                        if (intersectRes==true) {
                           PointDouble intersectPoint2 = findIntersection2(theTree);
                           if (intersectPoint!=null) {
                               //calculate distance from observer to stored point
                               Double dist1=(intersectPoint.XCoord-observerX)*(intersectPoint.XCoord-observerX)+(intersectPoint.YCoord-observerY)*(intersectPoint.YCoord-observerY);
                               Double dist2=(intersectPoint2.XCoord-observerX)*(intersectPoint2.XCoord-observerX)+(intersectPoint2.YCoord-observerY)*(intersectPoint2.YCoord-observerY);
                               if (dist2<dist1) {
                                    intersectPoint=intersectPoint2;
                                    intersectionIndex = treeIndex;
                               }
                           } else {
                               intersectPoint=intersectPoint2;
                               intersectionIndex = treeIndex;
                           }
                           
                        }
                    }
                    treeIndex+=1;
                }
                //check intersection with wall
                PointDouble wallIntersection = findWallIntersection();
                if (wallIntersection!=null) {
                    Double dist1=(intersectPoint.XCoord-observerX)*(intersectPoint.XCoord-observerX)+(intersectPoint.YCoord-observerY)*(intersectPoint.YCoord-observerY);
                    Double dist2=(wallIntersection.XCoord-observerX)*(wallIntersection.XCoord-observerX)+(wallIntersection.YCoord-observerY)*(wallIntersection.YCoord-observerY);
                    if (dist2<dist1) {
                        intersectPoint=wallIntersection;
                        intersectionIndex = -1;
                    }
                }
                //draw intersection on image (put the tracing ray to drawing queue)
                maintracing.RayTracing.theGlobalGraphics.tracingRaysReflection.add(new SingleSectionData(observerX, observerY,intersectPoint.XCoord, intersectPoint.YCoord));
                
            } else {
                if ( (alphaCurrRadians>Math.PI)&&(alphaCurrRadians<2*Math.PI) ) {
                    //in a trignometric sense 0 is the same as 2*Math.PI
                    //find intersection with all Tree Objects below the point of observation
                    for (SingleTreeData theTree : allTrees) {
                        if (observerY>=theTree.treeYCoordinate-theTree.treeRadius) {
                            System.out.println("Tree Below Observer:"+theTree.toString());
                        }
                    }
                }
            
            }
           
            
        }
    }
    /**
     * Find FACT of intersection of tracing ray with some Tree
     * @param someObject - a SingleTreeData 
     * @return 
     */
    public Boolean findIntersection(SingleTreeData someObject) {
        Boolean intersects = false;
        //square of distance from line to center of circle
        Double D_square = Math.pow((-Math.tan(this.alphaCurrRadians)*someObject.treeXCoordinate+someObject.treeYCoordinate+ (-observerY+observerX*Math.tan(alphaCurrRadians)) ),2.0)/(1+Math.tan(alphaCurrRadians)*Math.tan(alphaCurrRadians));
        if (D_square>someObject.treeRadius*someObject.treeRadius) {
            intersects=false;
        } else {intersects=true;}
        return intersects;
    }
    /**
     * find intersection of current tracing ray and Other Tree. Actually it finds the intersection of ray and circle o the plane
     * @param someObject
     * @return intersection point that is located closer to 
     */
    public PointDouble findIntersection2(SingleTreeData someObject) {
        PointDouble intersectPoint =new PointDouble();
        //ray parameters
        Double k=Math.tan(alphaCurrRadians);
        Double b=observerY-k*observerX;
        //center of circle
        Double x=someObject.treeXCoordinate;
        Double y=someObject.treeYCoordinate;
        Double r=someObject.treeRadius;
        //find discriminant of quadratic equation
        Double d=(Math.pow((2*k*b-2*x-2*y*k),2)-(4+4*k*k)*(b*b-r*r+x*x+y*y-2*y*b));        
        //if it is less 0 then there's no intersection, just return null
     if(d<0) 
     {
         intersectPoint=null;
     }
     else
     {
    //find 2 intersection points
        double x1=((-(2*k*b-2*x-2*y*k)-Math.sqrt(d))/(2+2*k*k));
        double x2=((-(2*k*b-2*x-2*y*k)+Math.sqrt(d))/(2+2*k*k));
        double y1=k*x1+b;
        double y2=k*x2+b;
    //intersection is in a single point: ray "touches" the circle
        if (x1==x2) {
             intersectPoint.XCoord=x1; intersectPoint.YCoord=y1;
        }
        else  { //two intersections points. Determine which one appears to be closer to observer
            Double dist1=(observerX-x1)*(observerX-x1)+(observerY-y1)*(observerY-y1);
            Double dist2=(observerX-x2)*(observerX-x2)+(observerY-y2)*(observerY-y2);
            if (dist1<=dist2) {
                intersectPoint.XCoord=x1; intersectPoint.YCoord=y1;
            } else {
                intersectPoint.XCoord=x2; intersectPoint.YCoord=y2;
            }
        }
     }
        
        return intersectPoint;
    }
    public PointDouble findWallIntersection() {
        PointDouble intersectPoint = new PointDouble();
        
        try {
        intersectPoint.XCoord = (wallB-(observerY-Math.tan(alphaCurrRadians)*observerX))/(Math.tan(alphaCurrRadians)-wallK);
        intersectPoint.YCoord = wallK*intersectPoint.XCoord+wallB;
        } catch (Exception e) 
            //ray and wall are parallel
            {intersectPoint=null; return intersectPoint;}
        //check belonging of point to the section
        Double X = intersectPoint.XCoord; Double Y = intersectPoint.YCoord;
        Double X1 = mainwall.getStartXCoord(); Double Y1=mainwall.getStartYCoord();
        Double X2 = mainwall.getEndXCoord(); Double Y2 = mainwall.getEndYCoord();
        Double P = (X-X2)/(X1-X2);
        if ((P<0)||(P>1)) {intersectPoint=null; return intersectPoint;}
        if (P*Y1+(1-P)*Y2!=Y) {intersectPoint=null; return intersectPoint;}
        return intersectPoint;
    }
    
    public void initAllHardcodedData() {
        this.defineStaticTrees();
        this.defineStaticWall();
        this.defineStaticObserver();
    }
}