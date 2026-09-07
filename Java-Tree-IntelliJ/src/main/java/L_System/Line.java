package L_System;
/**
 * Line that contains coordinates of the start and the end.
 */
public class Line {
    /**
     * Double array of start coordinates.
     */
    private double[] start = new double[2];
    /**
     * Double array of end coordinates.
     */
    private double[] end = new double[2];

    /**
     * Constructor,
     * Sets start and end coordinates of Line.
     */
    public Line(double[] start, double[] end){
        setStart(start);
        setEnd(end);
    }
    /**
     * Sets start coordinates of Line.
     */
    public void setStart(double[] start) {
        this.start = new double[] {start[0], start[1]};
    }
    /**
     * Sets end coordinates of Line.
     */
    public void setEnd(double []  end){
        this.end = new double[] {end[0], end[1]};
    }
    /**
     * Basic return getEnd.
     * @return Retruns end coordinates of Line.
     */
    public double[] getEnd() {
        return end;
    }
    /**
     * Basic return getStart.
     * @return Returns start coordinates of Line.
     */
    public double[] getStart() {
        return start;
    }
    /**
     * Basic toString function.
     * @return Returns a String of the start and the end coordinates.
     */
    @Override
    public String toString(){
        return start[0] + " " + start[1] + " " + end[0] + " " + end[1];
    }
}
