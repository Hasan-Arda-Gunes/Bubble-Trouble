public class Arrow {
    private boolean active;
    public static final double periodOfArrow = 1500;
    private double positionX;
    private double positionY;
    public Arrow(){}

    /**
     * getter for the x coordinate
     * @return the x coordinate
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * getter for the y coordinate
     * @return the y coordinate
     */
    public double getPositionY() {
        return positionY;
    }

    /**
     * setter for the y coordinate
     * @param positionY
     */
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    /**
     * setter for the x coordinate
     * @param positionX
     */
    public void setPositionX(double positionX){this.positionX = positionX;}

    /**
     * getter for the activity
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * if the arrow is shot this method sets the boolean active value true
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}
