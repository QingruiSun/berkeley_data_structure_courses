import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private final double ULLON = -122.2998046875;
    private final double ULLAT = 37.892195547244356;
    private final double LRLON = -122.2119140625;
    private final double LRLAT = 37.82280243352756;

    private final double eps = Math.pow(0.1, 7);

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        // System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        results = calculateMapRaster(params);
        System.out.println(results);
        String path = "../library-sp18/data/proj3_imgs/";
        System.out.println(path);
        return results;
    }




    private double calculateGoalResolution(Map<String, Double> params) {
        return (params.get("lrlon") - params.get("ullon")) / params.get("w");
    }

    /**
     * Calculate the depth needed with the Given params.
     * */
    private int calulateDepth(Map<String, Double> params) {
        double goalResolution = calculateGoalResolution(params);
        double originalResoultion = (LRLON - ULLON) / 256; // The longtitude resolution in the depth 0.
        double resolution = originalResoultion;
        int depth = 0;
        while ((depth <= 7) && (resolution > goalResolution)) {
            depth++;
            resolution = resolution / 2;
        }
        return depth;
    }



    private Map<String, Object> calculateMapRaster(Map<String, Double> params) {
        int depth = calulateDepth(params);
        int imgNumPerLine = (int) Math.pow(2, depth);
        double queryULLON = params.get("ullon");
        double queryULLAT = params.get("ullat");
        double queryLRLON = params.get("lrlon");
        double queryLRLAT = params.get("lrlat");
        int xLeftOrder = 0;
        while (xLeftOrder < imgNumPerLine) {
            double[] imgParams = getRange(depth, xLeftOrder, 0);
            if (Double.compare(queryULLON, imgParams[0]) < 0) {
                break;
            }
            if ((Double.compare(imgParams[0], queryULLON) <= 0) && (Double.compare(imgParams[2], queryULLON) > 0)) {
                break;
            }
            xLeftOrder++;
        }
        int xRightOrder = 0;
        while (xRightOrder < imgNumPerLine) {
            double[] imgParams = getRange(depth, xRightOrder, 0);
            if ((Double.compare(imgParams[0], queryLRLON) < 0) && (Double.compare(imgParams[2], queryLRLON) >= 0)) {
                break;
            }
            xRightOrder++;
        }
        xRightOrder = Math.min(xRightOrder, imgNumPerLine - 1);
        int yUpOrder = 0;
        while (yUpOrder < imgNumPerLine) {
            double[] imgParams = getRange(depth, 0, yUpOrder);
            if (Double.compare(queryULLAT, imgParams[1]) > 0) {
                break;
            }
            if ((Double.compare(imgParams[1], queryULLAT) >= 0) && (Double.compare(imgParams[3], queryULLAT) < 0)) {
                break;
            }
            yUpOrder++;
        }
        int yBottomOrder = 0;
        while (yBottomOrder < imgNumPerLine) {
            double[] imgParams = getRange(depth, 0, yBottomOrder);
            if ((Double.compare(imgParams[1], queryLRLAT) > 0) && (Double.compare(imgParams[3], queryLRLAT) <= 0)) {
                break;
            }
            yBottomOrder++;
        }
        yBottomOrder = Math.min(yBottomOrder, imgNumPerLine - 1);
        String[][] grids = new String[yBottomOrder - yUpOrder + 1][xRightOrder - xLeftOrder + 1];
        for (int i = yUpOrder; i <= yBottomOrder; ++i) {
            for (int j = xLeftOrder; j <= xRightOrder; ++j) {
                String oneResult = "d" + depth + "_x" + j + "_y" + i + ".png";
                grids[i - yUpOrder][j - xLeftOrder] = oneResult;
            }
        }
        Map<String, Object> mapRaster = new HashMap<>();
        mapRaster.put("render_grid", (Object) grids);
        Boolean query_success = true;
        mapRaster.put("query_success", (Object) query_success);
        mapRaster.put("depth", depth);
        double[] upLeftParams = getRange(depth, xLeftOrder, yUpOrder);
        mapRaster.put("raster_ul_lon", upLeftParams[0]);
        mapRaster.put("raster_ul_lat", upLeftParams[1]);
        double[] bottomRightParams = getRange(depth, xRightOrder, yBottomOrder);
        mapRaster.put("raster_lr_lon", (Object) bottomRightParams[2]);
        mapRaster.put("raster_lr_lat", (Object) bottomRightParams[3]);
        return mapRaster;
    }



    /**
     * Given the depth, the x order and the y order of image.
     * Return the longtitude and the latitude of the up left corner
     * and bottom right corner of the image.
     * */
    private double[] getRange(int depth, int x, int y) {
        if (depth > 7) {
            throw new IllegalArgumentException("Depth shouldn't greater than 7!");
        }
        int imgNumPerLine = (int) Math.pow(2, depth);
        if ((x >= imgNumPerLine) || (y >= imgNumPerLine)) {
            throw new IllegalArgumentException("The value of x, y is illegal!");
        }
        double longitudePerImg = (LRLON - ULLON) / imgNumPerLine;
        double latitudePerImg = (ULLAT - LRLAT) / imgNumPerLine;
        double[] imgParams = new double[4];
        imgParams[0] = ULLON + x * longitudePerImg;
        imgParams[1] = ULLAT - y * latitudePerImg;
        imgParams[2] = imgParams[0] + longitudePerImg;
        imgParams[3] = imgParams[1] - latitudePerImg;
        return imgParams;
    }

}
