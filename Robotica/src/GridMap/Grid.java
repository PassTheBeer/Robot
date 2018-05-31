package GridMap;

import java.awt.Point;
import java.util.*;

public class Grid  implements Runnable{

    private AlgoritmeKiezer algokiezer;

    //Declareer de hashmap voor het plaatsen van alle coordinaten
    public static HashMap<Point, Obstakel> map;

    private int aantal_obstakels;
    public static int aantal_onbekenden;
    public static double lengte_node = 5;

    private static int grid_width;
    private static int grid_height;

    private static ArrayList<Point> obstakel_coordinaten;
    private static ArrayList<Point> beginEindCoords;
    private static ArrayList<Point> onbekend_coordinaten;

    private Point beginpunt;
    private static Point eindpunt;

    Grid(Point r_pos) {
        map = new HashMap<Point, Obstakel>();
        obstakel_coordinaten = new ArrayList<Point>();
        beginEindCoords = new ArrayList<Point>();
        onbekend_coordinaten = new ArrayList<Point>();

        beginpunt = new Point(r_pos);
        eindpunt = new Point(-6,5);

        setGrid_width(Math.abs((int)eindpunt.getX()));
        setGrid_height(Math.abs((int)eindpunt.getY()));

        genereerCoords();

        algokiezer = new AlgoritmeKiezer(getObstakel_coordinaten(), beginEindCoords, getOnbekend_coordinaten());
    }

    @Override
    public void run() {
        algokiezer.RunAStar();
    }

    public void genereerCoords() {

        int o_x = 0, o_y = 0;
        int aantal_obstakels = (getGrid_width() * getGrid_height() - 10);
        int range_x = (getGrid_width() - -getGrid_width()) + 1;
        int range_y = (getGrid_height() - -getGrid_height()) + 1;

        for (int x = -getGrid_height(); x < getGrid_height(); x++) {
            for (int y = -getGrid_width(); y < getGrid_width(); y++) {
                plaatsOnbekend(new Point(y,x));
            }
        }
        for(int i = 0; i<aantal_obstakels; i++) {
            o_x = (int)((Math.random() * range_x) + -getGrid_width());
            o_y = (int)((Math.random() * range_y)+ -getGrid_height());
            plaatsObstakel(new Point(o_x, o_y));
        }

        plaatsBeginEind(beginpunt, eindpunt);
        sortNodes();
    }

    //X begin links van negatief naar positief, ------ Y begin boven van negatief naar positief.
    public static String printGrid(HashMap<Point, Obstakel> map) {

        String grid = "";

        for (int row =-getGrid_height(); row < getGrid_height(); row++) {
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

    public void plaatsOnbekend(Point coords){
        map.put(coords, Obstakel.O);
        getOnbekend_coordinaten().add(coords);
        aantal_onbekenden++;
    }

    public void plaatsObstakel(Point coords) {
        if(coords.getX() == eindpunt.getX() && coords.getY() == eindpunt.getY()) {
            map.remove(coords);
            map.put(coords, Obstakel.E);
        }
        else {
            map.put(coords, Obstakel.M);
        }
        getObstakel_coordinaten().add(coords);
        aantal_obstakels++;
    }

    public static void voeg_xRij(double bx) {
        setGrid_width(getGrid_width() + 1);
        for(int i = -getGrid_height(); i<getGrid_height(); i++) {
            if(eindpunt.getX() > 0 ) {
                map.put(new Point((int) bx + 1, i),   Obstakel.O);
                onbekend_coordinaten.add(new Point((int) bx + 1, i));
            }
            else {
                map.put(new Point((int) bx - 1, i),   Obstakel.O);
                onbekend_coordinaten.add(new Point((int) bx - 1, i));
            }
        }
    }
    public static void voeg_yRij(double by) {
        System.out.println(by);
        setGrid_height(getGrid_height() + 1);
        for(int i = -getGrid_width(); i<getGrid_width(); i++) {
            if(eindpunt.getY() > 0) {
                map.put(new Point(i, (int)by + 1), Obstakel.O);
                onbekend_coordinaten.add(new Point(i, (int)by + 1));
            }
            else {
                map.put(new Point(i, (int) by - 1), Obstakel.O);
                onbekend_coordinaten.add(new Point(i, (int) by - 1));
            }
        }
    }

    public void plaatsBeginEind(Point coords_b, Point coords_e) {
        Point eind_neg = new Point((int)coords_e.getX() - 1, (int)coords_e.getY() - 1);
        Point eind_neg_y = new Point((int)coords_e.getX() - 1, (int)coords_e.getY());
        Point eind_neg_x = new Point((int)coords_e.getX(), (int)coords_e.getY() - 1);

        getBeginEindCoords().add(coords_b);
        map.replace(coords_b, Obstakel.S);

        if(coords_e.getX() > 0 && coords_e.getY() > 0) {
            getBeginEindCoords().add(eind_neg);
            map.replace(eind_neg, Obstakel.E);
        }
        else if(coords_e.getX() < 0 && coords_e.getY() > 0) {
            getBeginEindCoords().add(eind_neg_x);
            map.replace(eind_neg_x, Obstakel.E);
        }
        else if(coords_e.getX() > 0 && coords_e.getY() < 0) {
            getBeginEindCoords().add(eind_neg_y);
            map.replace(eind_neg_y, Obstakel.E);
        }
        else {
            getBeginEindCoords().add(coords_e);
            map.replace(coords_e, Obstakel.E);
        }
    }
    public int getAantal_obstakels() {
        return aantal_obstakels;
    }

    public void setAantal_obstakels(int aantal_obstakels) {
        this.aantal_obstakels = aantal_obstakels;
    }

    public Point getBeginpunt() {
        return beginpunt;
    }

    public static void sortNodes() {
        Collections.sort(getOnbekend_coordinaten(), Node.comp);
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

    public void iterateArrayList() {
        Iterator<Point> crunchifyIterator = getOnbekend_coordinaten().iterator();
        while (crunchifyIterator.hasNext()) {
            System.out.println(crunchifyIterator.next());
        }
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




