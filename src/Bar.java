
public class Bar {
    public Bar(){}

    /**
     * this method calculates the length and color of the bar depending on the time past
     * @param timeSpent
     */
    public void displayBar(double timeSpent){
        StdDraw.setPenColor(225,225-(int) (225*(timeSpent/40000)),0);
        StdDraw.filledRectangle(0.0,-0.5,16.0-(16*timeSpent/Environment.totalGameDuration),0.25);
    }
}