package GridMap;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class AStar {

    Grid grid;
    private int aantal_onbekenden;

    Node anode;
    private ArrayList<Point> path;
    private Node[][] neighbours;
    private static double pytho = Math.sqrt(Math.pow(Grid.lengte_node, 2) + Math.pow(Grid.lengte_node, 2));
    private double hWaarde;

    Robot robot;

    public AStar(int a_o, int a_n) {
        aantal_onbekenden = a_o;
        path = new ArrayList<Point>();
        neighbours = new Node[aantal_onbekenden][aantal_onbekenden];
    }

    //BerekenH Waarde methode
    public double BerekenH(double e_x, double e_y, double o_x, double o_y) {
        double afstands = Math.sqrt(Math.pow(e_x - o_x, 2) + Math.pow(e_y - o_y, 2));
        return afstands;
    }

    public void print(ArrayList<Point> onb) {
        for (int i = 0; i < aantal_onbekenden; i++) {
            System.out.println("Node " + i + ": " + onb.get(i).getX() + "," + onb.get(i).getY());
        }
        System.out.println("");
    }

    public void test(ArrayList<Point> ob, ArrayList<Point> be, ArrayList<Point> onb) {
        System.out.println("\n");

        double min_h_waarde = Double.MAX_VALUE;

        double b_x = be.get(0).getX();
        double b_y = be.get(0).getY();
        double e_x = be.get(1).getX();
        double e_y = be.get(1).getY();

        int wall_x = (int) e_x;
        int wall_y = (int) e_y;

        Point temp = new Point(0, 0);
        Point begin = new Point((int) b_x, (int) b_y);
        Point eind = new Point((int) e_x, (int) e_y);
        Stack s = new Stack();

        boolean bereikt = false;

        int y = 0, j = 0;

        s.push(temp);
        path.add(begin);

        if (ob.contains(eind)) {
            ob.remove(eind);
        }

        System.err.println("");

        while (!bereikt) {
            min_h_waarde = 1000000;
            for (int i = 0; i < onb.size(); i++) {

                double vers_x = b_x - onb.get(i).getX();
                double vers_y = b_y - onb.get(i).getY();

                if (!(vers_x == 0 && vers_y == 0) && (vers_x >= -1 && vers_x <= 1) && (vers_y >= -1 && vers_y <= 1)) {

                    Point buur_p = new Point((int) onb.get(i).getX(), (int) onb.get(i).getY());

                    if (ob.size() != 0) {
                        for (int k = 0; k < ob.size(); k++) {
                            if (buur_p.getX() == ob.get(k).getX() && buur_p.getY() == ob.get(k).getY()) {
                                Grid.map.replace(ob.get(k), Obstakel.M);
                                hWaarde = Double.MAX_VALUE;
                                break;
                            } else {
                                Grid.map.replace(buur_p, Obstakel.N);
                                hWaarde = BerekenH(e_x, e_y, onb.get(i).getX(), onb.get(i).getY());
                                if (onb.get(i).getX() == temp.getX() && onb.get(i).getY() == temp.getY()) {
                                    hWaarde += 2;
                                }
                            }
                            neighbours[j][y] = new Node("Node " + y, hWaarde, buur_p, Obstakel.N);
                        }
                    } else {
                        Grid.map.replace(buur_p, Obstakel.N);
                        hWaarde = BerekenH(e_x, e_y, onb.get(i).getX(), onb.get(i).getY());
                        neighbours[j][y] = new Node("Node " + y, hWaarde, buur_p, Obstakel.N);
                    }

                    if (hWaarde < min_h_waarde) {
                        min_h_waarde = hWaarde;
                        begin.move((int) buur_p.getX(), (int) buur_p.getY());
                    }
                    j++;
                }
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException exc) {

            }
            grid.map.replace(new Point((int) b_x, (int) b_y), Obstakel.R);

            path.add(begin.getLocation());
            s.push(begin.getLocation());
            System.out.println(begin.getLocation());

            if (b_x == wall_x) {
                Grid.voeg_xRij(b_x);
                wall_x = Grid.getGrid_width();
            }
            if (b_y == wall_y) {
                Grid.voeg_yRij(b_y);
                wall_y = Grid.getGrid_height();
            }
            System.out.println(Grid.printGrid(Grid.map));

            if (b_x == eind.getX() && b_y == eind.getY()) {
                System.out.println("Bestemming bereikt. U heeft 2 seconden te tijd om een verfrissing te pakken! SHIET OP");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException exc) {
                }
                bereikt = true;
            }

            b_x = begin.getX();
            b_y = begin.getY();

            y++;

        }
        if (bereikt) {
            Iterator<Point> i = s.iterator();
            while (i.hasNext()) {
                Point t = (Point) s.pop();

                grid.map.replace(t, Obstakel.R);
                System.out.println(Grid.printGrid(Grid.map));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException exc) {
                }

                grid.map.replace(t, Obstakel.N);
            }
        }
    }

    public void printPath() {
        Iterator<Point> it = path.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getLocation());
        }
    }
}


