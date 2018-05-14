package GridMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Node {

    public final String value;
    public double g_scores;
    public final double h_scores;
    public double f_scores = 0;
    public Edge[] adjacencies;
    public Node parent;

    ArrayList<Point> pathnodes;
    public Node[] nodelist;
    Obstakel obstakel;


    public Node(String val, double hVal,ArrayList<Point> n, Node[] nodes, Obstakel obstakel){
        value = val;
        h_scores = hVal;
        pathnodes = n;
        nodelist = nodes;
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
