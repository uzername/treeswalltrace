/*
 * В лесу стоит стена ограниченной длины. Координаты центров и радиусов деревьев известны.
 * Выяснить, какие части стены не будут видны наблюдателю, находящемуся в заданной точке
 */
package maintracing;
import raytracing.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
/**
 * @author ivan
 */
public class RayTracing {

    
    public static RayTracingSolver theGlobalSolver;
    public static GraphicsProcessing theGlobalGraphics;
    public static Boolean dataInitiated=false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //iface is run in a separate thread. Swing style. Should define data processing in a separate thread too
        theGlobalSolver = new RayTracingSolver();
        theGlobalGraphics = new GraphicsProcessing();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });

        /*
        theSolver.performRaytracing();
        */
    }
    
}
