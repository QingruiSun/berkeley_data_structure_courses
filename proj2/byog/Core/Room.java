package byog.Core;

import java.util.Comparator;
import java.util.Random;

public class Room {

    private int bottomRow; //Represents the bottom row of the room.
    private int leftCol; //Represents the left col of the room.

    // Represent the width and height of the room.
    private int width;
    private int height;

    public int getBottomRow() {
        return bottomRow;
    }

    public int getLeftCol() {
        return leftCol;
    }

    public int getRightCol() {
        return leftCol + width + 1;
    }

    public int getUpRow() {
        return bottomRow + height + 1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Room(int bottomRow, int leftCol, int width, int height) {
        if ((width < 2) || (height < 2)) {
            throw new IllegalArgumentException("The width and height of room must be greater than 2!");
        }
        this.bottomRow = bottomRow;
        this.leftCol = leftCol;
        this.width = width;
        this.height = height;
    }

    public boolean isOverlap(Room room) {
        int thisRightCol = leftCol + width + 1;
        int thisUpRow = bottomRow + height + 1;
        int roomLeftCol = room.getLeftCol();
        int roomRightCol = roomLeftCol + room.getWidth() + 1;
        int roomBottomRow = room.getBottomRow();
        int roomUpRow = roomBottomRow + room.getHeight() + 1;
        if ((thisRightCol <= roomLeftCol) || (roomRightCol <= this.leftCol)) {
            return false;
        }
        if ((this.bottomRow >= roomUpRow) || (thisUpRow <= roomBottomRow)) {
            return false;
        }
        return true;
    }


    public static class RoomComparator implements Comparator<Room> {
        @Override
        public int compare(Room room1, Room room2) {
            int room1DistanceFromOrigin = room1.getLeftCol() + room1.getBottomRow();
            int room2DistanceFromOrigin = room2.getLeftCol() + room2.getBottomRow();
            if (room1DistanceFromOrigin < room2DistanceFromOrigin) {
                return -1;
            } else if (room1DistanceFromOrigin == room2DistanceFromOrigin) {
                return 0;
            } else {
                return 1;
            }
        }
    }

}
