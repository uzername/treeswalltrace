package raytracing;

/**
 * contains data about single section.
 * @author ivan
 */
public class SingleSectionData {
    private Double startXCoord; 
    private Double startYCoord;
    private Double endXCoord; 
    private Double endYCoord;
    public SingleSectionData(Double inpStartX, Double inpStartY, Double inpEndX, Double inpEndY) {
        if (inpStartX>inpEndX) {
            this.endXCoord=inpStartX; this.endYCoord=inpStartY;
            this.startXCoord=inpEndX; this.startYCoord=inpEndY;
        } else {
            this.startXCoord=inpStartX; this.startYCoord=inpStartY;
            this.endXCoord=inpEndX; this.endYCoord=inpEndY;
        }
    }

    /**
     * @return the startXCoord
     */
    public Double getStartXCoord() {
        return startXCoord;
    }
    /**
     * @param startXCoord the startXCoord to set
     */
    public void setStartXCoord(Double startXCoord) {
        this.startXCoord = startXCoord;
    }
    /**
     * @return the startYCoord
     */
    public Double getStartYCoord() {
        return startYCoord;
    }
    /**
     * @param startYCoord the startYCoord to set
     */
    public void setStartYCoord(Double startYCoord) {
        this.startYCoord = startYCoord;
    }
    /**
     * @return the endXCoord
     */
    public Double getEndXCoord() {
        return endXCoord;
    }
    /**
     * @param endXCoord the endXCoord to set
     */
    public void setEndXCoord(Double endXCoord) {
        this.endXCoord = endXCoord;
    }
    /**
     * @return the endYCoord
     */
    public Double getEndYCoord() {
        return endYCoord;
    }
    /**
     * @param endYCoord the endYCoord to set
     */
    public void setEndYCoord(Double endYCoord) {
        this.endYCoord = endYCoord;
    }
}
