public class Ball {
    public static final double periodOfBall = 15000;
    public static final double heightMultiplier = 1.75;
    private final static double radiusMultiplier = 2.0;
    public final static double minPossibleHeight = Player.playerHeight*1.4;
    public final static double minPossibleRadius = 9*0.0175;
    public static final double gravity = 0.000001 * 9;
    private double velocityX;
    private double velocityY;
    private double currentRadius;
    private double currentHeight;
    private double XCoordinate;
    private double YCoordinate;
    private int level;
    private boolean shot;

    public Ball(int level, double XCoordinate, double YCoordinate, double velocityX, double velocityY){
        this.level = level;
        this.XCoordinate = XCoordinate;
        this.YCoordinate = YCoordinate;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        currentRadius = minPossibleRadius * Math.pow(radiusMultiplier,level);
        currentHeight = minPossibleHeight * Math.pow(radiusMultiplier,level);

    }

    /**
     * setter for the x component of the velocity
     * @param velocityX
     */
    public void setVelocityX(double velocityX){
        this.velocityX = velocityX;
    }

    /**
     * getter for the x component of the velocity
     * @return the x component of the velocity
     */
    public double getVelocityX() {
        return velocityX;
    }

    /**
     * setter for the y component of the velocity
     * @param velocityY
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * getter for the y component of the velocity
     * @return the y component of the velocity
     */
    public double getVelocityY() {
        return velocityY;
    }

    /**
     * getter for the height
     * @return the max height the ball can reach
     */
    public double getCurrentHeight() {
        return currentHeight;
    }

    /**
     * getter for the radius of the ball
     * @return the radius of the ball
     */
    public double getCurrentRadius() {
        return currentRadius;
    }

    /**
     * setter for the y coordinate
     * @param YCoordinate
     */
    public void setYCoordinate(double YCoordinate) {
        this.YCoordinate = YCoordinate;
    }

    /**
     * getter for the y coordinate
     * @return YCoordinate
     */
    public double getYCoordinate() {
        return YCoordinate;
    }

    /**
     * setter for the x coordinate
     * @param XCoordinate
     */
    public void setXCoordinate(double XCoordinate) {
        this.XCoordinate = XCoordinate;
    }

    /**
     * getter for the x coordinate
     * @return XCoordinate
     */
    public double getXCoordinate() {
        return XCoordinate;
    }

    /**
     * getter for the level of the ball
     * @return level
     */
    public int getLevel(){return level;}

    /**
     * it returns a boolean value depending on whether the ball is shot or not
     * @return shot
     */

    public boolean isShot(){
        return shot;
    }

    /**
     * if the ball is shot this method sets the shot value true
     * @param shot
     */
    public void setShot(boolean shot) {
        this.shot = shot;
    }
}
