package raytracing;

/**
 * Single tree abstraction. Making everything double for a while
 * @author ivan
 */
public class SingleTreeData {
    public Double treeRadius=0.0;
    public Double treeXCoordinate=0.0;
    public Double treeYCoordinate=0.0;
    public SingleTreeData(Double inpTreeRadius, Double inpTreeXCoordinate, Double inpTreeYCoordinate) {
        this.treeRadius=inpTreeRadius;
        this.treeXCoordinate=inpTreeXCoordinate;
        this.treeYCoordinate = inpTreeYCoordinate;
    }
}
