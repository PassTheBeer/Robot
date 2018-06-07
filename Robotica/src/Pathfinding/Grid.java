package Pathfinding;

import java.awt.*;
import java.util.*;
@SuppressWarnings("Duplicates")

public class Grid implements Runnable {

    private Algorithm greedy;
    KinderLocker kinder;
    public static HashMap<Point, Obstakel> map;

    private static boolean killThread = false;

    public static double lengte_node;
    private static int grid_width;
    private static int grid_height;

    private static ArrayList<Point> obstakel_coordinaten;
    private static ArrayList<Point> beginEindCoords;
    private static ArrayList<Point> onbekend_coordinaten;

    private static Point positie;
    private static Point eindpunt;

    private static Richting richting;
    private static boolean richtingVeranderd;

    Grid(Point r_pos, Point e_p, double zicht, KinderLocker kl) {
        kinder = kl;
        map = new HashMap<Point, Obstakel>();

        obstakel_coordinaten = new ArrayList<Point>();
        beginEindCoords = new ArrayList<Point>();
        onbekend_coordinaten = new ArrayList<Point>();

        positie = new Point(r_pos);
        eindpunt = new Point(e_p);
        lengte_node = zicht;

        grid_width = Math.abs((int) eindpunt.getX());
        grid_height = Math.abs((int) eindpunt.getY());

        genereerCoords();

        greedy = new Algorithm(getObstakel_coordinaten(), beginEindCoords, getOnbekend_coordinaten(), kinder);
    }

    @Override
    public void run() {
        greedy.runAlgo();
    }


    public void genereerCoords() {

//        int aantal_obstakels = 50;
//        int o_x = 0, o_y = 0;
//        int aantal_obstakels = (getGrid_width() * getGrid_height() - 10);
//        int range_x = (2 * getGrid_width()) + 1;
//        int range_y = (2 * getGrid_height()) + 1;

        for (int x = -getGrid_height(); x < getGrid_height(); x++) {
            for (int y = -getGrid_width(); y < getGrid_width(); y++) {
                plaatsOnbekend(new Point(y, x));
            }
        }
//        for (int i = 0; i < aantal_obstakels; i++) {
//            o_x = (int) ((Math.random() * range_x) + -getGrid_width());
//            o_y = (int) ((Math.random() * range_y) + -getGrid_height());
//            obstakel_coordinaten.add(new Point(o_x, o_y));
//        }

        plaatsBeginEind(positie, eindpunt);
        sortNodes();

//        Out comment als je alle nodes wilt zien
//        iterateArrayList();
    }

//    X begin links van negatief naar positief, ------ Y begin boven van negatief naar positief.
    public static String printGrid(HashMap<Point, Obstakel> map) {

        String grid = "";

        for (int row = -getGrid_height(); row < getGrid_height(); row++) {
            for (int column = -getGrid_width(); column < getGrid_width(); column++) {
                if (map.containsKey(new Point(column, row)))
                    grid += map.get(new Point(column, row));
                else
                    grid += "#";
            }
            grid += "\n";
        }

        return grid;
    }

    public void plaatsOnbekend(Point coords) {
        map.put(coords, Obstakel.O);
        getOnbekend_coordinaten().add(coords);
    }

    public void plaatsObstakel(Point coords) {
        map.put(coords, Obstakel.M);
        if(!obstakel_coordinaten.contains(coords)) {
            getObstakel_coordinaten().add(coords);
            System.out.println(coords.getLocation());
        }
    }

    public void plaatsBeginEind(Point coords_b, Point coords_e) {
        Point eind_neg = new Point((int) coords_e.getX() - 1, (int) coords_e.getY() - 1);
        Point eind_neg_y = new Point((int) coords_e.getX() - 1, (int) coords_e.getY());
        Point eind_neg_x = new Point((int) coords_e.getX(), (int) coords_e.getY() - 1);

        getBeginEindCoords().add(coords_b);
        map.replace(coords_b, Obstakel.S);

        if (coords_e.getX() > 0 && coords_e.getY() > 0) {
            getBeginEindCoords().add(eind_neg);
            map.replace(eind_neg, Obstakel.E);
        } else if (coords_e.getX() < 0 && coords_e.getY() > 0) {
            getBeginEindCoords().add(eind_neg_x);
            map.replace(eind_neg_x, Obstakel.E);
        } else if (coords_e.getX() > 0 && coords_e.getY() < 0) {
            getBeginEindCoords().add(eind_neg_y);
            map.replace(eind_neg_y, Obstakel.E);
        } else {
            getBeginEindCoords().add(coords_e);
            map.replace(coords_e, Obstakel.E);
        }
    }

    public static void voeg_xRij(double bx) {
        setGrid_width(getGrid_width() + 1);
        for (int i = -getGrid_height(); i < getGrid_height(); i++) {
            if (eindpunt.getX() > 0) {
                map.put(new Point((int) bx + 1, i), Obstakel.O);
                getOnbekend_coordinaten().add(new Point((int) bx + 1, i));
            } else {
                map.put(new Point((int) bx - 1, i), Obstakel.O);
                getOnbekend_coordinaten().add(new Point((int) bx - 1, i));
            }
        }
    }

    public static void voeg_yRij(double by) {
        setGrid_height(getGrid_height() + 1);
        for (int i = -getGrid_width(); i < getGrid_width(); i++) {
            if (eindpunt.getY() > 0) {
                map.put(new Point(i, (int) by + 1), Obstakel.O);
                getOnbekend_coordinaten().add(new Point(i, (int) by + 1));
            } else {
                map.put(new Point(i, (int) by - 1), Obstakel.O);
                getOnbekend_coordinaten().add(new Point(i, (int) by - 1));
            }
        }
    }
    public void iterateArrayList() {
        Iterator<Point> crunchifyIterator = getOnbekend_coordinaten().iterator();
        while (crunchifyIterator.hasNext()) {
            System.out.println(crunchifyIterator.next());
        }
    }
    public static <Point, Obstakel> Set<Point> getKeysByValue(Map<Point, Obstakel> map, Obstakel value) {
        Set<Point> keys = new HashSet<Point>();

        for (Map.Entry<Point, Obstakel> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }
    public static void sortNodes() {
        Collections.sort(getOnbekend_coordinaten(), Node.comp);
    }


    public static void kill(){
        killThread = true;
    }
    public static boolean isKillThread(){
        return killThread;
    }

    public static boolean isRichtingVeranderd(){
        return richtingVeranderd;
    }
    public static void setRichting(Richting richting, boolean rv) {
        Grid.richting = richting;
        richtingVeranderd = rv;
    }
    public static Richting getRichting() {
        return richting;
    }


    public static ArrayList<Point> getBeginEindCoords() {
        return beginEindCoords;
    }
    public static void setBeginEindCoords(ArrayList<Point> beginEindCoords) {
        Grid.beginEindCoords = beginEindCoords;
    }

    public static Point getPositie() {
        return positie;
    }
    public static void setPositie(Point positie) {
        Grid.positie = positie;
//        System.out.println(positie.getLocation());
    }

    public static Point getEindpunt() {
        return eindpunt;
    }
    public static void setEindpunt(Point eindpunt) {
        Grid.eindpunt = eindpunt;
    }

    public static int getGrid_height() {
        return grid_height;
    }
    public static void setGrid_height(int grid_height) {
        Grid.grid_height = grid_height;
    }

    public static int getGrid_width() {
        return grid_width;
    }
    public static void setGrid_width(int grid_width) {
        Grid.grid_width = grid_width;
    }

    public static ArrayList<Point> getOnbekend_coordinaten() {
        return onbekend_coordinaten;
    }
    public static void setOnbekend_coordinaten(ArrayList<Point> onbekend_coordinaten) {
        Grid.onbekend_coordinaten = onbekend_coordinaten;
    }

    public static ArrayList<Point> getObstakel_coordinaten() {
        return obstakel_coordinaten;
    }
    public static void setObstakel_coordinaten(ArrayList<Point> obstakel_coordinaten) {
        Grid.obstakel_coordinaten = obstakel_coordinaten;
    }

}




