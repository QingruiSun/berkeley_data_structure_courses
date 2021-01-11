package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class TestSomeJavaSyntax {
    public static void main(String[] args) {
        int width = 5;
        int height = 5;
        TERenderer teRenderer = new TERenderer();
        teRenderer.initialize(width, height);
        TETile[][] tiles = new TETile[width][height];
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < height / 2; ++j) {
                tiles[i][j] = Tileset.NOTHING;
            }
        }
        for (int i = 0; i < width; ++i) {
            for (int j = height / 2; j < height; ++j) {
                tiles[i][j] = Tileset.FLOOR;
            }
        }
        teRenderer.renderFrame(tiles);
    }
}
