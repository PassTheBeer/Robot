package GridMap;

import java.awt.Point;
import java.util.*;

public class Grid {

    public HashMap<Point, Obstakel> map = new HashMap<Point, Obstakel>();

    public static int aantal_nodes;
    public static int aantal_obstakels;
    public static double lengte_node = 5;
    public static int grid_width = 5;
    public static int grid_height = 5;

    private static ArrayList<Point> node_coordinaten;
    private static ArrayList<Point> beginEindCoords;
    private static ArrayList<Point> ob_coordinaten;

    private Point beginpunt;
    private Point eindpunt;

    Grid() {
        node_coordinaten = new ArrayList<Point>();
        beginEindCoords = new ArrayList<Point>();
        ob_coordinaten = new ArrayList<Point>();
        beginpunt = new Point(0, 0);
        eindpunt = new Point((grid_width - 1), (grid_height - 1));
        genereerCoords();
    }

    public void genereerCoords() {

        int ob_x;
        int ob_y;
        int aantal_obstakels = (int) (Math.random() * (grid_height * grid_width) - 20);

        // x = kolumn (begint bij 0) y = rij (begint bij 0)
        for (int x = -grid_height; x < grid_height; x++) {
            for (int y = -grid_width; y < grid_width; y++) {
                plaatsOnbekend(new Point(y,x));
                //plaatsNode(new Point(y, x));
            }
        }


        plaatsBeginEind(beginpunt, eindpunt);
        sortNodes();
    }

    public static String printGrid(HashMap<Point, Obstakel> map) {

        String grid = "";

        for (int row = -grid_height; row < grid_height; row++) {
            for (int column = -grid_width; column < grid_width; column++) {
                if (map.containsKey(new Point(column, row))) {
                    grid += map.get(new Point(column, row));
                } else {
                    grid += "#";
                }
            }
            grid += "\n"; // next row
        }

        return grid;
    }

    public static String printPad(HashMap<Point, String> map) {

        String grid = "";

        for (int row = -grid_height; row < grid_height; row++) {
            for (int column = -grid_width; column < grid_width; column++) {
                if (map.containsKey(new Point(column, row))) {
                    grid += map.get(new Point(column, row));
                } else {
                    grid += " ";
                }
            }
            grid += "\n"; // next row
        }

        return grid;
    }

    public void plaatsOnbekend(Point coords){
        map.put(coords, Obstakel.O);
        getOb_coordinaten().add(coords);
        aantal_obstakels++;

    }

    public void plaatsObject(Point coords) {
        map.put(coords, Obstakel.T);
        getOb_coordinaten().add(coords);
        aantal_obstakels++;
    }

    public void plaatsNode(Point coords) {
        getNode_coordinaten().add(coords);
        map.put(coords, Obstakel.N);
        aantal_nodes++;
    }

    public void plaatsBeginEind(Point coords_b, Point coords_e) {
        getBeginEindCoords().add(coords_b);
        map.put(coords_b, Obstakel.R);
        getBeginEindCoords().add(coords_e);
        map.put(coords_e, Obstakel.P);
    }

    public void beweegRobot(Point point, Point nieuw){
        map.replace(point, Obstakel.l);
        map.replace(nieuw, Obstakel.P);
    }



    public void padZien(HashMap<Point, Obstakel> map, Point coords) {
        map.put(coords, Obstakel.l);
    }

    public void sortNodes() {
        Collections.sort(getOb_coordinaten(), Node.comp);
    }


    public ArrayList<Point> getNode_coordinaten() {
        return node_coordinaten;
    }

    public void setNode_coordinaten(ArrayList<Point> node_coordinaten) {
        this.node_coordinaten = node_coordinaten;
    }

    public static ArrayList<Point> getOb_coordinaten() {
        return ob_coordinaten;
    }

    public static void setOb_coordinaten(ArrayList<Point> ob_coordinaten) {
        Grid.ob_coordinaten = ob_coordinaten;
    }

    public static ArrayList<Point> getBeginEindCoords() {
        return beginEindCoords;
    }

    public static void setBeginEindCoords(ArrayList<Point> beginEindCoords) {
        Grid.beginEindCoords = beginEindCoords;
    }

    public static <Point, Obstakel> Set<Point> getKeysByValue(Map<Point,Obstakel> map, Obstakel value) {

        Set<Point> keys = new HashSet<Point>();

        for (Map.Entry<Point, Obstakel> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

}



