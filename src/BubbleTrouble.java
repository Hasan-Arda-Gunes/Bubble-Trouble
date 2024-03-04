/**
 * This code makes an animation with a while loop by calculating the locations and adding the images to their locations
 * @author Hasan Arda Gunes  Student ID: 2021400003
 * @since Date: 07.04.2023
 */
public class BubbleTrouble {
    public static void main(String[] args) {

        StdDraw.setCanvasSize(Environment.canvasWidth,Environment.canvasHeight);
        StdDraw.setXscale(0.0,16.0);
        StdDraw.setYscale(-1.0,9.0);
        Environment environment = new Environment();
        environment.playGame();
    }
}