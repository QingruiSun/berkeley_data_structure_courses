package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import sun.awt.image.ImageWatched;

import java.util.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    // Initial random seed value.
    private int initialRandomDigital = 342355;

    List<Room> rooms;

    List<HallWay> hallWays;

    TETile[][] world = new TETile[WIDTH][HEIGHT];

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {

    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        TETile[][] finalWorldFrame = null;
        return finalWorldFrame;
    }

    public void initWorldWithRandomSeed() {
        generateRoomWithRandomSeed();
        generateHallWayWithRandomSeed();
        displayAll();
    }

    private void generateRoomWithRandomSeed() {
        rooms = new LinkedList<>();
        int randomNumOfRoom = getRandomNumOfRoom();
        // Use it to random generate room position and room size.
        int newRoomRandomDigit = initialRandomDigital;
        Room newRoom = getRandomRoom(newRoomRandomDigit);
        rooms.add(newRoom);
        // Actually, the number of room we generate.
        int generateNumOfRoom = 1;
        while ((generateNumOfRoom < randomNumOfRoom) || (newRoom != null)) {
            newRoomRandomDigit++;
            newRoom = getRandomRoom(newRoomRandomDigit);
            if (newRoom != null) {
                generateNumOfRoom++;
                rooms.add(newRoom);
            }
        }
    }

    private int getRandomNumOfRoom() {
        Random random = new Random(initialRandomDigital);
        // Random create room number.
        int roomNums = 4 + (Math.abs(random.nextInt()) % 8);
        return roomNums;
    }

    private Room getRandomRoom(int randomSeed) {
        Random random1 = new Random(randomSeed + 1);
        Random random2 = new Random(randomSeed + 2);
        Random random3 = new Random(randomSeed + 3);
        Random random4 = new Random(randomSeed + 4);
        int threshHold = 0;
        // If generate overlapped room more than 30 times, indicate the world is
        // too full, stop generate room.
        while (threshHold < 30) {
            int bottomRow = (Math.abs(random1.nextInt()) % HEIGHT);
            int leftCol = (Math.abs(random2.nextInt()) % WIDTH);
            int roomWidth = 2 + (Math.abs(random3.nextInt()) % 12);
            int roomHeight = 2 + (Math.abs(random4.nextInt()) % 6);
            Room randomRoom = new Room(bottomRow, leftCol, roomWidth, roomHeight);
            if ((!isRoomOverlaped(randomRoom)) && (isRoomInWorld(randomRoom))) {
                return randomRoom;
            }
            threshHold++;
        }
        return null;
    }

    private boolean isRoomInWorld(Room room) {
        int upRow = room.getBottomRow() + room.getHeight() + 1;
        int rightCol = room.getLeftCol() + room.getWidth() + 1;
        if (upRow >= HEIGHT) {
            return false;
        }
        if (rightCol >= WIDTH) {
            return false;
        }
        return true;
    }

    /** Judege the new generate room if overlaped with the existed room. */
    private boolean isRoomOverlaped(Room oneRoom) {
        for (Room room : rooms) {
            if (room.isOverlap(oneRoom)) {
                return true;
            }
        }
        return false;
    }

    private void displayOneRoom(Room room) {
        int leftCol = room.getLeftCol();
        int bottomRow = room.getBottomRow();
        int roomWidth = room.getWidth();
        int roomHeight = room.getHeight();
        for (int i = leftCol; i <= leftCol + roomWidth + 1; i += 1) {
            world[i][bottomRow] = Tileset.WALL;
            world[i][bottomRow + roomHeight + 1] = Tileset.WALL;
        }
        for (int i = bottomRow; i <= bottomRow + roomHeight + 1; i += 1) {
            world[leftCol][i] = Tileset.WALL;
            world[leftCol + roomWidth + 1][i] = Tileset.WALL;
        }
        for (int i = leftCol + 1; i <= leftCol + roomWidth; ++i) {
            for (int j = bottomRow + 1; j <= bottomRow + roomHeight; ++j) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }




    private void generateHallWayWithRandomSeed() {
        Room.RoomComparator roomComparator = new Room.RoomComparator();
        Collections.sort(rooms, roomComparator);
        hallWays = new LinkedList<>();
        for (int i = 0; i < rooms.size() - 1; ++i) {
            HallWay hallWay = connectTwoRooms(rooms.get(i), rooms.get(i + 1));
            hallWays.add(hallWay);
        }
    }

    private void displayOneHallWay(HallWay hallWay) {
        if ((!hallWay.isLTypeHallWay()) && (hallWay.isHallWayVertical())) {
            displayOneVerticalHallWay(hallWay);
        }
        if ((!hallWay.isLTypeHallWay()) && (hallWay.isHallWayHorizontal())) {
            dispalyOneHorizontalHallWay(hallWay);
        }
        if (hallWay.isLTypeHallWay()) {
            displayOneLTypeHallWay(hallWay);
        }
    }



    private void displayOneVerticalHallWay(HallWay hallWay) {
        if (!hallWay.isHallWayVertical()) {
            throw new IllegalArgumentException("In this function, the hallway must be vertical!");
        }
        for (int i = hallWay.getBottomRow(); i < hallWay.getBottomRow() + hallWay.getLength(); ++i) {
            world[hallWay.getLeftCol() - 1][i] = Tileset.WALL;
            world[hallWay.getLeftCol()][i] = Tileset.FLOOR;
            world[hallWay.getLeftCol() + 1][i] = Tileset.WALL;
        }
    }

    private void dispalyOneHorizontalHallWay(HallWay hallWay) {
        if (!hallWay.isHallWayHorizontal()) {
            throw new IllegalArgumentException("In this function, the hallway must be horizontal!");
        }
        for (int i = hallWay.getLeftCol(); i < hallWay.getLeftCol() + hallWay.getLength(); ++i) {
            world[i][hallWay.getBottomRow() - 1] = Tileset.WALL;
            world[i][hallWay.getBottomRow()] = Tileset.FLOOR;
            world[i][hallWay.getBottomRow() + 1] = Tileset.WALL;
        }
    }

    private void displayOneLTypeHallWay(HallWay hallWay) {
        if (!hallWay.isLTypeHallWay()) {
            throw new IllegalArgumentException("The hallway in this function must be L type!");
        }
        HallWay firstHallWay = hallWay;
        HallWay secondHallWay = hallWay.getTurnHallWay();
        for (int i = 0; i < firstHallWay.getLength(); ++i) {
            world[i][firstHallWay.getBottomRow() + 1] = Tileset.WALL;
            world[i][firstHallWay.getBottomRow()] = Tileset.FLOOR;
            world[i][firstHallWay.getBottomRow() - 1] = Tileset.WALL;
        }
        for (int i = 0; i < secondHallWay.getLength(); ++i) {
            world[secondHallWay.getLeftCol() - 1][i] = Tileset.WALL;
            world[secondHallWay.getLeftCol()][i] = Tileset.FLOOR;
            world[secondHallWay.getLeftCol() + 1][i] = Tileset.WALL;
        }
        if (secondHallWay.getBottomRow() < firstHallWay.getBottomRow()) {
            world[secondHallWay.getLeftCol() - 1][secondHallWay.getBottomRow() + secondHallWay.getLength() - 1] = Tileset.FLOOR;
            world[secondHallWay.getLeftCol()][secondHallWay.getBottomRow() + secondHallWay.getLength()] = Tileset.WALL;
        } else {
            world[secondHallWay.getLeftCol() - 1][secondHallWay.getBottomRow()] = Tileset.FLOOR;
            world[secondHallWay.getLeftCol()][secondHallWay.getBottomRow() - 1] = Tileset.WALL;
        }
    }

    private HallWay connectTwoRooms(Room roomA, Room roomB) {
        int[] overlapedRows = getOverlapedRowOfRoom(roomA, roomB);
        int[] overlapedCols = getOverlapedColOfRoom(roomA, roomB);
        Random random = new Random(initialRandomDigital + 43);
        if (overlapedRows != null) {
            int overlapedRowNum = overlapedRows[1] - overlapedRows[0];
            int selectedRow = overlapedRows[0] + (Math.abs(random.nextInt()) % overlapedRowNum);
            int hallWayLeftCol = Math.min(roomA.getRightCol(), roomB.getRightCol());
            int hallWayLength = Math.max(roomA.getLeftCol(), roomB.getLeftCol()) - Math.min(roomA.getRightCol(), roomB.getRightCol()) + 1;
            HallWay hallWay = new HallWay(selectedRow, hallWayLeftCol, hallWayLength, false, null);
            return hallWay;
        } else if (overlapedCols != null) {
            int overlapedColNum = overlapedCols[1] - overlapedCols[0];
            int selectedCol = overlapedCols[0] + (Math.abs(random.nextInt()) % overlapedColNum);
            int hallWayBottomRow = Math.min(roomA.getUpRow(), roomB.getUpRow());
            int hallWayLength = Math.max(roomA.getBottomRow(), roomB.getBottomRow()) - Math.max(roomA.getUpRow(), roomB.getUpRow());
            HallWay hallWay = new HallWay(hallWayBottomRow, selectedCol, hallWayLength, true, null);
            return hallWay;
        } else {
            HallWay LTypeHallWay = buildLTypeHallWay(roomA, roomB);
            return LTypeHallWay;
        }
    }

    private HallWay buildLTypeHallWay(Room roomA, Room roomB) {
        if ((getOverlapedColOfRoom(roomA, roomB) != null) || (getOverlapedRowOfRoom(roomA, roomB) != null)) {
            throw new IllegalArgumentException("The two room should not be overlapped!");
        }
        Room leftRoom = roomA.getLeftCol() < roomB.getLeftCol() ? roomA : roomB;
        Room rightRoom = roomA.getLeftCol() > roomB.getLeftCol() ? roomA : roomB;
        HallWay firstHallWay = new HallWay(leftRoom.getBottomRow() + 1, leftRoom.getRightCol(),
                rightRoom.getLeftCol() - leftRoom.getRightCol() + 2, false, null);
        HallWay secondHallWay = null;
        if (rightRoom.getBottomRow() > leftRoom.getBottomRow()) {
            secondHallWay = new HallWay(leftRoom.getBottomRow() + 1, rightRoom.getLeftCol() + 1,
                    rightRoom.getBottomRow() - leftRoom.getBottomRow(), true, null);
        } else {
            secondHallWay = new HallWay(rightRoom.getUpRow(), rightRoom.getLeftCol() + 1,
                    leftRoom.getBottomRow() - rightRoom.getUpRow() + 2, true, null);
        }
        firstHallWay.setTurnHallWay(secondHallWay);
        return firstHallWay;
    }

    private int[] getOverlapedRowOfRoom(Room roomA, Room roomB) {
        int roomABottomFloorRow = roomA.getBottomRow() + 1;
        int roomAUpFloorRow = roomABottomFloorRow + roomA.getHeight() - 1;
        int roomBBottomFloorRow = roomB.getBottomRow() + 1;
        int roomBUpFloorRow = roomBBottomFloorRow + roomB.getHeight() - 1;
        int[] overlapedInterval = null;
        if ((roomABottomFloorRow >= roomBUpFloorRow) && (roomABottomFloorRow <= roomBUpFloorRow)) {
            overlapedInterval = new int[2];
            overlapedInterval[0] = roomABottomFloorRow;
            overlapedInterval[1] = roomBUpFloorRow;
        }
        if ((roomBBottomFloorRow >= roomABottomFloorRow) && (roomBBottomFloorRow <= roomAUpFloorRow)) {
            overlapedInterval = new int[2];
            overlapedInterval[0] = roomBBottomFloorRow;
            overlapedInterval[1] = roomBUpFloorRow;
        }
        return overlapedInterval;
    }

    private int[] getOverlapedColOfRoom(Room roomA, Room roomB) {
        int roomALeftFloorCol = roomA.getLeftCol() + 1;
        int roomARightFloorCol = roomALeftFloorCol + roomA.getWidth() - 1;
        int roomBLeftFloorCol = roomB.getLeftCol() + 1;
        int roomBRightFloorCol = roomBLeftFloorCol + roomB.getWidth() - 1;
        int[] overlapedInterval = null;
        if ((roomARightFloorCol >= roomBLeftFloorCol) && (roomARightFloorCol <= roomBRightFloorCol)) {
            overlapedInterval = new int[2];
            overlapedInterval[0] = roomBLeftFloorCol;
            overlapedInterval[1] = roomARightFloorCol;
        }
        if ((roomALeftFloorCol >= roomBLeftFloorCol) && (roomALeftFloorCol <= roomBRightFloorCol)) {
            overlapedInterval = new int[2];
            overlapedInterval[0] = roomALeftFloorCol;
            overlapedInterval[1] = roomBRightFloorCol;
        }
        return overlapedInterval;
    }


    /** Display all rooms and all hallways. */
    private void displayAll() {
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        /*
        for (Room room : rooms) {
            displayOneRoom(room);
        }

         */
        for (HallWay hallWay : hallWays) {
            displayOneHallWay(hallWay);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initWorldWithRandomSeed();
        game.ter.initialize(WIDTH, HEIGHT);
        game.ter.renderFrame(game.world);
    }
}
