package byog.Core;

public class HallWay {

    private int bottomRow;
    private int leftCol;

    private boolean isHorizontal;
    private boolean isVertical;

    private int length;


    private HallWay turnHallWay;

    public HallWay(int bottomRow, int leftCol, int length, boolean isVertical, HallWay turnHallWay) {
        this.bottomRow = bottomRow;
        this.leftCol = leftCol;
        this.length = length;
        this.isVertical = isVertical;
        this.isHorizontal = !isVertical;
        this.turnHallWay = turnHallWay;
    }

    public int getBottomRow() {
        return bottomRow;
    }

    public int getLeftCol() {
        return leftCol;
    }

    public int getLength() {
        return length;
    }

    public HallWay getTurnHallWay() {
        return turnHallWay;
    }


    public boolean isHallWayVertical() {
        return isVertical;
    }

    public boolean isHallWayHorizontal() {
        return isHorizontal;
    }

    public boolean isLTypeHallWay() {
        return turnHallWay != null;
    }

    public void setTurnHallWay(HallWay turnHallWay) {
        this.turnHallWay = turnHallWay;
    }


}
