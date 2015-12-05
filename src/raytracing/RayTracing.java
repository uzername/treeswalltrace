/*
 * В лесу стоит стена ограниченной длины. Координаты центров и радиусов деревьев известны.
 * Выяснить, какие части стены не будут видны наблюдателю, находящемуся в заданной точке
 */
package raytracing;

/**
 * @author ivan
 */
public class RayTracing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RayTracingSolver theSolver = new RayTracingSolver();
        theSolver.defineStaticTrees();
        theSolver.defineStaticWall();
        theSolver.defineStaticObserver();
        theSolver.performRaytracing();
    }
    
}
