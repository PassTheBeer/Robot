package GridMap;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar {

    Robot robot = new Robot();
    Node anode;

    Node[] nodes = new Node[Grid.aantal_nodes];
    private static double pytho = Math.sqrt(Math.pow(Grid.lengte_node, 2) + Math.pow(Grid.lengte_node, 2));
    private double hWaarde;

    //BerekenH Waarde methode
    public double BerekenH(double x, double y, ArrayList<Point> b) {
        double afstands = Math.sqrt(Math.pow(x - b.get(1).getX(), 2)
                + Math.pow(y - b.get(1).getY(), 2));
        return afstands;

    }

    public void print(ArrayList<Point> nc, ArrayList<Point> ob) {
        for (int i = 0; i < Grid.aantal_nodes; i = i + 3) {
            System.out.println("Node " + i + ": " + nc.get(i).getX() + "," + nc.get(i).getY()
                    + "- Node " + (i + 1) + ": " + nc.get(i + 1).getX() + "," + nc.get(i + 1).getY()
                    + "- Node " + (i + 2) + ": " + nc.get(i + 2).getX() + "," + nc.get(i + 2).getY());
        }
        System.out.println("");
        for (int i = 0; i < Grid.aantal_obstakels; i++) {
            System.out.println("Obstakel " + i + ": " + ob.get(i).getX() + "," + ob.get(i).getY());
        }
    }

    public void BerekenPad(ArrayList<Point> nc, ArrayList<Point> ob, ArrayList<Point> be) {
        //Alle nodes aanmaken & checken of de coordinaten van de node gelijk zijn aan de coordinaten
        //van een obstakel, zo ja, zet de h-waarde (afstand van die node naar eind) op oneindig
        //zodat hij daar nooit naar toe gaat, want zo werkt A*
        System.out.println("\n");
        for (int i = 0; i < Grid.aantal_nodes; i++) {
            for (int y = 0; y < Grid.aantal_obstakels; y++) {
                if (nc.get(i).getX() == ob.get(y).getX() && nc.get(i).getY() == ob.get(y).getY()) {
                    hWaarde = 1000000.0;
                    break;
                } else {
                    hWaarde = BerekenH(nc.get(i).getX(), nc.get(i).getY(), be);
                }
            }
            //System.out.println("H Node " + i + ": " + hWaarde);
            nodes[i] = new Node("Node " + i, hWaarde, nc, nodes, Obstakel.l);
            //nodes[i] = new Node("Node " + i, hWaarde, nc, i, nodes);
        }

        //Alle edges aanmaken bij de bijbehorende nodes.
        //De edges naar de obstakels hebben gewoon de normale waarde, want ze gaan hier toch niet heen
        //Omdat de h-waarde te groot is.
        for (int i = 0; i < Grid.aantal_nodes; i++) {
            //Node Links bovenin
            if (nc.get(i).getX() == 0 && nc.get(i).getY() == 0) {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i + 1], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width + 1], pytho)
                };
            }
            //Node links onder in
            else if (nc.get(i).getX() == 0 && nc.get(i).getY() == Grid.grid_height - 1) {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i + 1], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width + 1], pytho)
                };
            }
            //Node rechts bovenin
            else if (nc.get(i).getX() == Grid.grid_width - 1 && nc.get(i).getY() == 0) {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i - 1], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width - 1], pytho)
                };
            }
            //Node rechts onderin
            else if (nc.get(i).getX() == Grid.grid_width - 1 && nc.get(i).getY() == Grid.grid_height - 1) {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i - 1], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width - 1], pytho)
                };
            }
            //Alle nodes links tegen de edge
            else if (nc.get(i).getX() == 0) {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i + 1], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width + 1], pytho),
                        new Edge(nodes[i - Grid.grid_width + 1], pytho)
                };
            }
            //Alle nodes rechts tegen de edge
            else if (nc.get(i).getX() == Grid.grid_width - 1) {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i - 1], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width - 1], pytho),
                        new Edge(nodes[i - Grid.grid_width - 1], pytho)
                };
            }
            //Alle nodes bovenin tegen de edge
            else if (nc.get(i).getY() == 0) {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i + 1], Grid.lengte_node),
                        new Edge(nodes[i - 1], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width - 1], pytho),
                        new Edge(nodes[i + Grid.grid_width + 1], pytho)
                };
            }
            //Alle nodes onderin tegen de edge
            else if (nc.get(i).getY() == Grid.grid_height - 1) {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i + 1], Grid.lengte_node),
                        new Edge(nodes[i - 1], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width + 1], pytho),
                        new Edge(nodes[i - Grid.grid_width - 1], pytho)
                };
            }
            //Elke andere node. (alles in het midden)
            else {
                nodes[i].adjacencies = new Edge[]{
                        new Edge(nodes[i + 1], Grid.lengte_node),
                        new Edge(nodes[i - 1], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i - Grid.grid_width], Grid.lengte_node),
                        new Edge(nodes[i + Grid.grid_width + 1], pytho),
                        new Edge(nodes[i + Grid.grid_width - 1], pytho),
                        new Edge(nodes[i - Grid.grid_width + 1], pytho),
                        new Edge(nodes[i - Grid.grid_width - 1], pytho)
                };
            }
        }

        //Voer A* uit met het begin punt en eind punt als argument
        AstarSearch(nodes[0], nodes[Grid.aantal_nodes - 1]);
        List<Node> path = printPath(nodes[Grid.aantal_nodes - 1]);

        Point coord[] = new Point[path.size()];

        //Zet het berekende pad in de bestaande hashmap.
        for (int i = 0; i < path.size(); i++) {
            for (int j = 0; j < Grid.aantal_nodes; j++) {
                if (path.get(i).value == nodes[j].value) {
                    int cx = (int) nodes[j].pathnodes.get(j).getX();
                    int cy = (int) nodes[j].pathnodes.get(j).getY();
                    coord[i] = new Point(cx, cy);
                    robot.grid.padZien(robot.grid.map, coord[i]);
                }
            }
        }

        //Laat het pad zien en daarbij de nodes die in het pad zitten met de bijbehorende coordinaten
        System.out.println(robot.grid.printGrid(robot.grid.map));
        System.out.println("Useful information: \n");
        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i) + " ---- " + coord[i].getX() + "," + coord[i].getY());
            // System.out.println(path.get(i).h_scores);
        }
    }

    public static List<Node> printPath(Node target) {

        List<Node> path = new ArrayList<Node>();

        for (Node node = target; node != null; node = node.parent) {
            path.add(node);
        }

        Collections.reverse(path);
        System.out.println("Optimal path: \n ");

        return path;
    }

    public static void AstarSearch(Node source, Node goal) {


        Set<Node> explored = new HashSet<Node>();

        PriorityQueue<Node> queue = new PriorityQueue<Node>(20, new Comparator<Node>() {

            public int compare(Node i, Node j) {
                if (i.f_scores > j.f_scores) return 1;
                else if (i.f_scores < j.f_scores) return -1;
                else return 0;
            }
        }
        );

        //cost from start
        source.g_scores = 0;

        queue.add(source);

        boolean found = false;

        while ((!queue.isEmpty()) && (!found)) {

            //the node in having the lowest f_score value
            Node current = queue.poll();

            explored.add(current);

            //goal found
            if (current.value.equals(goal.value)) {
                found = true;
            }

            //check every child of current node
            for (Edge e : current.adjacencies) {
                Node child = e.target;
                double cost = e.cost;
                double temp_g_scores = current.g_scores + cost;
                double temp_f_scores = temp_g_scores + child.h_scores;


                            /*if child node has been evaluated and 
                            the newer f_score is higher, skip*/

                if ((explored.contains(child)) &&
                        (temp_f_scores >= child.f_scores)) {
                    continue;
                }

                            /*else if child node is not in queue or 
                            newer f_score is lower*/

                else if ((!queue.contains(child)) ||
                        (temp_f_scores < child.f_scores)) {

                    child.parent = current;
                    child.g_scores = temp_g_scores;
                    child.f_scores = temp_f_scores;

                    if (queue.contains(child)) {
                        queue.remove(child);
                    }

                    queue.add(child);
                }
            }
        }
    }
}


//class Node{
//
//    public final String value;
//    public double g_scores;
//    public final double h_scores;
//    public double f_scores = 0;
//    public Edge[] adjacencies;
//    public Node parent;
//
//    ArrayList<Point> pathnodes;
//    public Node[] nodelist;
//    Obstakel obstakel;
//
//
//    public Node(String val, double hVal,ArrayList<Point> n, Node[] nodes, Obstakel obstakel){
//    	  value = val;
//          h_scores = hVal;
//          pathnodes = n;
//          nodelist = nodes;
//          this.obstakel = obstakel;
//		}
//
//
//
//    public String toString(){
//            return value;
//    }
//      public static Comparator<Point> comp = new Comparator<Point>() {
//
//      	public int compare(Point c1, Point c2) {
//  			   int cy_1 = (int) c1.getY();
//  			   int cy_2 = (int) c2.getY();
//  			   return cy_1 - cy_2;
//  		   }
//      	};
//
//}
//
//class Edge{
//    public final double cost;
//    public final Node target;
//
//    public Edge(Node targetNode, double costVal){
//            target = targetNode;
//            cost = costVal;
//    }
//}


