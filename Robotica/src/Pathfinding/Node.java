package Pathfinding;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Node {

    public final String value;
    public double h_scores;

    public static ArrayList<Point> neighbours = new ArrayList<Point>();
    Obstakel obstakel;

    public Node(String val, double hVal, Point n, Obstakel obstakel){
        value = val;
        h_scores = h_scores + hVal;
        neighbours.add(n);
        this.obstakel = obstakel;
    }
    public String toString(){
        return value;
    }
    public static Comparator<Point> comp = new Comparator<Point>() {

        public int compare(Point c1, Point c2) {
            int cy_1 = (int) c1.getY();
            int cy_2 = (int) c2.getY();
            return cy_1 - cy_2;
        }
    };
}
