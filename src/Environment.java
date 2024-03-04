import javax.sound.midi.MidiFileFormat;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Period;
import java.util.ArrayList;

public class Environment {
    public static final int canvasWidth = 800;
    public static final int canvasHeight = 500;
    public static final double scaleX = 16.0;
    public static final double scaleY = 9.0;
    public static final double totalGameDuration = 40000;
    public static final int pauseDuration = 10;
    private boolean collusion;

    public Environment(){}

    /**
     *
     * @param time time spent in the while loop
     * @param player
     * @param balls an array list containing the balls
     * @param arrow
     */
    public void drawBackground(double time, Player player, ArrayList<Ball> balls, Arrow arrow ){
        StdDraw.picture(8.0,4.0,"background.png",16.0,10.0,0);
        StdDraw.picture(8.0,-0.5,"bar.png",16.0,1.0);
        // if the player shots the arrow show the arrow image
        if (arrow.isActive())
            StdDraw.picture(arrow.getPositionX(),arrow.getPositionY()/2,"arrow.png", 0.1, arrow.getPositionY());
        StdDraw.picture(player.getPositionX(),Player.playerHeight/2, "player_back.png", Player.playerWidth, Player.playerHeight);

        // check if the player is at left boundary
        if (player.getPositionX() >= 0.0 + Player.playerHeight/2 + scaleX/Player.periodOfPlayer * time *5/3){
            // move the player
            if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT))
                player.setPositionX(player.getPositionX() - scaleX/ Player.periodOfPlayer * time *5/3);
        }
        // check if the player is at right boundary
        if (player.getPositionX() <= 16.0-Player.playerWidth/2 - scaleX/Player.periodOfPlayer * time*5/3){
            // move the player
            if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT))
                player.setPositionX(player.getPositionX() + scaleX/Player.periodOfPlayer * time*5/3);
        }

        collusion = false;
        for (Ball ball: balls) {
            double VY = ball.getVelocityY();
            double VX = ball.getVelocityX();
            // if the ball hits the right boundary make an elastic collusion
            if (ball.getXCoordinate() + VX * time*5/3.0 + ball.getCurrentRadius() > scaleX)
                ball.setVelocityX(-1 * ball.getVelocityX());
            // if the ball hits the left boundary make an elastic collusion
            else if (ball.getXCoordinate() + VX * time*5/3.0 - ball.getCurrentRadius() < 0.0)
                ball.setVelocityX(-1 * ball.getVelocityX());
            //if the ball doesn't hit the boundaries move it horizontally with VX velocity
            ball.setXCoordinate(ball.getXCoordinate() + VX * time*5/3.0);
            // so that balls can't get stuck
            if (ball.getXCoordinate() + ball.getCurrentRadius() > scaleX)
                ball.setXCoordinate(ball.getXCoordinate() - 0.05);
            else if (ball.getXCoordinate() - ball.getCurrentRadius() < 0.0)
                ball.setXCoordinate(ball.getXCoordinate() + 0.05);
            // if the ball hits the ground make an elastic collusion
            if (ball.getYCoordinate() + VY * time - 0.5 * Ball.gravity * Math.pow(time, 2) - ball.getCurrentRadius() < 0) {
                ball.setVelocityY(Math.sqrt(2 * Ball.gravity * (ball.getCurrentHeight() - ball.getCurrentRadius())));
                ball.setYCoordinate(ball.getCurrentRadius());
            }
            else {
                // if the ball doesn't hit the ground move the ball with y = y0 + Vy*t - 0.5 * g * t^2
                ball.setYCoordinate(ball.getYCoordinate() + VY * time - 0.5 * Ball.gravity * Math.pow(time, 2));
                ball.setVelocityY(ball.getVelocityY() - Ball.gravity * time);
            }
            double x = ball.getXCoordinate();
            double y = ball.getYCoordinate();
            double r = ball.getCurrentRadius();
            double xP0 = player.getPositionX() - Player.playerWidth / 2;
            double xP1 = player.getPositionX() + Player.playerWidth / 2;
            double xPA = player.getPositionX();
            double yP = Player.playerHeight;

            StdDraw.picture(x, y, "ball.png", 2 * r, 2 * r);

            //check whether the ball hit the player or not
            // use the geometrical equation for a disc with center(a,b) and radius r  (x-a)^2 + (y-b)^2 <= r^2
            if (Math.pow(r, 2) > Math.pow(xP0 - x, 2)) {
                if (y - Math.sqrt(Math.pow(r, 2) - Math.pow(xP0 - x, 2)) <= yP)
                    collusion = true;
            } else if (Math.pow(r, 2) > Math.pow(xP1 - x, 2)) {
                if (y - Math.sqrt(Math.pow(r, 2) - Math.pow(xP1 - x, 2)) <= yP)
                    collusion = true;
            }
            else if (Math.pow(r, 2) > Math.pow(xP0 + Player.playerWidth / 4, 2)){
                if (y - Math.sqrt(Math.pow(r, 2) - Math.pow(xP0 + Player.playerWidth / 4, 2)) <= yP)
                    collusion = true;
            }
            else if (Math.pow(r, 2) > Math.pow(xPA + Player.playerWidth / 4, 2)){
                if (y - Math.sqrt(Math.pow(r, 2) - Math.pow(xPA + Player.playerWidth / 4, 2)) <= yP)
                    collusion = true;
            }
            else if (Math.pow(r, 2) > Math.pow(xPA - x, 2)) {
                if (y - Math.sqrt(Math.pow(r, 2) - Math.pow(xPA - x, 2)) <= yP)
                    collusion = true;
            }
            // use the same equation to determine whether the arrow shot the ball or not
            if (Math.pow(r, 2) > Math.pow(arrow.getPositionX() - x, 2)) {
                if (y - Math.sqrt(Math.pow(r, 2) - Math.pow(arrow.getPositionX() - x, 2)) <= arrow.getPositionY()){
                    arrow.setActive(false);
                    arrow.setPositionY(0);
                    ball.setShot(true);
                }
            }
            else if (Math.pow(r, 2) > Math.pow(arrow.getPositionX() - x - 0.05, 2)) {
                if (y - Math.sqrt(Math.pow(r, 2) - Math.pow(arrow.getPositionX() - x -0.05, 2)) <= arrow.getPositionY()){
                    arrow.setActive(false);
                    arrow.setPositionY(0);
                    ball.setShot(true);
                }
            }
            else if (Math.pow(r, 2) > Math.pow(arrow.getPositionX() - x + 0.05, 2)) {
                if (y - Math.sqrt(Math.pow(r, 2) - Math.pow(arrow.getPositionX() - x + 0.05, 2)) <= arrow.getPositionY()){
                    arrow.setActive(false);
                    arrow.setPositionY(0);
                    ball.setShot(true);
                }
            }
        }
        int size = balls.size();
        for (int i = 0; i<size;i++){
            if (balls.get(i).isShot()){
                // remove the ball if it is shot and if its level is not zero add 2 lower level balls
                double x = balls.get(i).getXCoordinate();
                double y = balls.get(i).getYCoordinate();
                double h = balls.get(i).getCurrentHeight();
                int level = balls.get(i).getLevel();
                balls.remove(balls.get(i));
                if (level>0){
                    balls.add(new Ball(level-1,x,y,scaleX/Ball.periodOfBall,Math.sqrt(2*Ball.gravity*h/Ball.heightMultiplier)));
                    balls.add(new Ball(level-1,x,y,-1*scaleX/Ball.periodOfBall,Math.sqrt(2*Ball.gravity*h/Ball.heightMultiplier)));
                }
                break;
            }
        }
        // shooting an arrow
        // if the arrow is still active this won't work
        if (!arrow.isActive()){
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                arrow.setActive(true);
                arrow.setPositionX(player.getPositionX());
            }
        }

        if (arrow.isActive()){
            // if the arrow reached the ceiling make it disappear
            if (arrow.getPositionY()+scaleY/Arrow.periodOfArrow * time >= scaleY){
                arrow.setActive(false);
                arrow.setPositionY(0);
            }
            // move the arrow vertically
            else
                arrow.setPositionY(arrow.getPositionY()+scaleY/Arrow.periodOfArrow * time*5/3);
        }
    }
    public void playGame(){
        Bar bar = new Bar();
        Environment environment = new Environment();
        StdDraw.enableDoubleBuffering();
        Player player = new Player();
        Arrow arrow = new Arrow();
        ArrayList<Ball> balls = new ArrayList<>();
        double start = System.currentTimeMillis();
        Ball ball1 = new Ball(1,4.0,0.5,-1* 16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight * 1.75 - Ball.minPossibleRadius*2)));
        Ball ball0 = new Ball(0,4.0,0.5,16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight - Ball.minPossibleRadius)));
        Ball ball2 = new Ball(2,4.0,0.5, 16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight * 1.75 * 1.75 - Ball.minPossibleRadius*2*2)));
        balls.add(ball0);
        balls.add(ball1);
        balls.add(ball2);
        StdDraw.enableDoubleBuffering();
        double current2 = System.currentTimeMillis() - start;
        while (true){
            StdDraw.clear();
            double current1 = System.currentTimeMillis() - start;
            //if the time run out game over
            if (current1>=Environment.totalGameDuration){
                StdDraw.clear();
                //clear the array list balls
                balls.clear();
                environment.drawBackground(current2,player,balls,arrow);
                StdDraw.picture(8.0,9/2.18,"game_screen.png",16/3.8,9.0/4);
                StdDraw.setPenColor(Color.black);
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
                StdDraw.text(8.0,4.5,"GAME OVER!");
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 15));
                StdDraw.text(8.0,9/2.3, "To Replay Click \"Y\"");
                StdDraw.text(8.0,9/2.6, "To Quit Click \"N\"");
                StdDraw.show();
                while (true){
                    // if Y is pressed add the initial balls, set the player's position to its initial value and reset the time
                    if (StdDraw.isKeyPressed(KeyEvent.VK_Y)){
                        balls.add(new Ball(1,4.0,0.5,-1* 16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight * 1.75 - Ball.minPossibleRadius*2))));
                        balls.add(new Ball(0,4.0,0.5,16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight - Ball.minPossibleRadius))));
                        balls.add(new Ball(2,4.0,0.5, 16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight * 1.75 * 1.75 - Ball.minPossibleRadius*2*2))));
                        start = System.currentTimeMillis();
                        current2 = System.currentTimeMillis() - start;
                        player.setPositionX(Environment.scaleX/2);
                        break;
                    }
                    // if N is pressed exit
                    else if (StdDraw.isKeyPressed(KeyEvent.VK_N)){
                        System.exit(0);
                    }
                }
                // make the arrow disappear
                arrow.setActive(false);
                arrow.setPositionY(0);
                continue;
            }
            // display everything
            environment.drawBackground(current2,player,balls,arrow);
            bar.displayBar(current1);
            StdDraw.show();
            // if any ball hit the player game over(same as time run out)
            if (environment.isCollusion()){
                StdDraw.clear();
                balls.clear();
                environment.drawBackground(current2,player,balls,arrow);
                bar.displayBar(current1);
                StdDraw.picture(8.0,9/2.18,"game_screen.png",16/3.8,9.0/4);
                StdDraw.setPenColor(Color.black);
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
                StdDraw.text(8.0,4.5,"GAME OVER!");
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 15));
                StdDraw.text(8.0,9/2.3, "To Replay Click \"Y\"");
                StdDraw.text(8.0,9/2.6, "To Quit Click \"N\"");
                StdDraw.show();
                while (true){
                    if (StdDraw.isKeyPressed(KeyEvent.VK_Y)){
                        balls.add(new Ball(1,4.0,0.5,-1* 16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight * 1.75 - Ball.minPossibleRadius*2))));
                        balls.add(new Ball(0,4.0,0.5,16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight - Ball.minPossibleRadius))));
                        balls.add(new Ball(2,4.0,0.5, 16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight * 1.75 * 1.75 - Ball.minPossibleRadius*2*2))));
                        start = System.currentTimeMillis();
                        current2 = System.currentTimeMillis() - start;
                        player.setPositionX(Environment.scaleX/2);
                        break;
                    }
                    else if (StdDraw.isKeyPressed(KeyEvent.VK_N)){
                        System.exit(0);
                    }
                }
                arrow.setActive(false);
                arrow.setPositionY(0);
                continue;
            }
            // if the player shoots every ball
            // inside the if part is the same as previous ones
            if (balls.isEmpty()){
                StdDraw.clear();
                environment.drawBackground(current2,player,balls,arrow);
                bar.displayBar(current1);
                StdDraw.picture(8.0,9/2.18,"game_screen.png",16/3.8,9.0/4);
                StdDraw.setPenColor(Color.black);
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
                StdDraw.text(8.0,4.5,"YOU WON!");
                StdDraw.setFont(new Font("Helvetica", Font.BOLD, 15));
                StdDraw.text(8.0,9/2.3, "To Replay Click \"Y\"");
                StdDraw.text(8.0,9/2.6, "To Quit Click \"N\"");
                StdDraw.show();
                while (true){
                    if (StdDraw.isKeyPressed(KeyEvent.VK_Y)){
                        balls.clear();
                        balls.add(new Ball(1,4.0,0.5,-1* 16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight * 1.75 - Ball.minPossibleRadius*2))));
                        balls.add(new Ball(0,4.0,0.5,16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight - Ball.minPossibleRadius))));
                        balls.add(new Ball(2,4.0,0.5, 16.0/15000,Math.sqrt(2*Ball.gravity*(Ball.minPossibleHeight * 1.75 * 1.75 - Ball.minPossibleRadius*2*2))));
                        start = System.currentTimeMillis();
                        current2 = System.currentTimeMillis() - start;
                        player.setPositionX(Environment.scaleX/2);
                        break;
                    }
                    else if (StdDraw.isKeyPressed(KeyEvent.VK_N)){
                        System.exit(0);
                    }
                }
                arrow.setActive(false);
                arrow.setPositionY(0);
                continue;
            }

            current2 = System.currentTimeMillis() - start;
            current2 -= current1;
            StdDraw.pause(Environment.pauseDuration);
        }
    }

    /**
     * if any ball touches the player this method returns true
     * @return collusion
     */
    public boolean isCollusion() {
        return collusion;
    }
}
