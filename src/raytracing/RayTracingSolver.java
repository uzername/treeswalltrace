/*
 * Use the raytracing algorithm from algolist.manual.ru/graphics/delinvis.php
 */
package raytracing;

import java.util.ArrayList;

/**
 * @author ivan
 */
public class RayTracingSolver {
    /**
     * all trees in the single array
     */
    public ArrayList<SingleTreeData> allTrees;
    /**
     * the wall is a subject of our task
     */
    public SingleSectionData mainwall;
    /**
     * the solution of the task - contains all subsections of the wall which are visible.
     * They should not be overlapping by the architecture of the solution
     */
    public ArrayList<SingleSectionData> solutionTask;
    
    public Double observerX=0.0;
    public Double observerY=0.0;
    /**
     * define trees data in a hardcoded manner. Writes to allTrees array
     */
    public void defineStaticTrees() {
        allTrees = new ArrayList<>();
        allTrees.add(new SingleTreeData(2.0, 45.0, 13.0));
        allTrees.add(new SingleTreeData(1.5, 17.5, 4.25));
        allTrees.add(new SingleTreeData(2.5,26.5,15.0));
        allTrees.add(new SingleTreeData(2.0,30.0,25.0));
        allTrees.add(new SingleTreeData(2.5,35.0,30.0));
    }
    /**
     * define wall in the hardcoded manner. 
     */
    public void defineStaticWall(){
        mainwall=new SingleSectionData(20.0, 20.0, 35.0, 45.0);
    }
    /**
     * define observer's coordinates in the static manner
     */
    void defineStaticObserver() {
        observerX = 27.5;
        observerY = 0.0;
    }
    /**
     * the main subroutine for the task. It finds visible segments of the wall.
     * returns nothing, because it writes to solutionTask.
     */
    public void performRaytracing() {
        
    }
    
    
}
