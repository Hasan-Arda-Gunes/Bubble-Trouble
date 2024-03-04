import java.awt.event.KeyEvent;

public class Player {
    public static final double periodOfPlayer = 6000;
    public static final double playerHeight = 1.125;
    public static final double playerWidth = playerHeight * 27.0/37.0;
    private double positionX;
    public Player(){
        this.positionX = 8.0;
    }

    /**
     * getter for the x coordinate of the player
     * @return the x coordinate of the player
     */
    public double getPositionX() {
        return positionX;
    }

    /**
     * setter for the x coordinate of the player
     * @param x
     */
    public void setPositionX(double x){
        this.positionX = x;
    }
}
