package GridMap;

import java.awt.Point;
import java.util.ArrayList;

public class AlgoritmeKiezer {

    Grid grid = new Grid();

    private ArrayList<Point> nodes;
    private ArrayList<Point> obstakels;
    private ArrayList<Point> beginEind;

    AlgoritmeKiezer() {
        nodes = grid.getNode_coordinaten();
        obstakels = grid.getOb_coordinaten();
        beginEind = grid.getBeginEindCoords();
    }

    public void SelectAlgoritm() {

    }

    public void RunAStar() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("ädfadf");


        //AStar astar = new AStar();
        //astar.BerekenPad(nodes, obstakels, beginEind);
        //astar.print(nodes, obstakels); // Outcomment dit als je de coordinaten van nodes & obstakels wilt zien
    }

    /*public void RunDijkstra() {
          Dijkstra dijkstra = new Dijkstra();
          dijkstra.BerekenPad(nodes, obstakels);
          dijkstra.printResult();
    }*/
    public void RunJPS() {

    }

    public void RunHPA() {

    }

    public void RunGreedy() {

    }
}
