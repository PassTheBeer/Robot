package GridMap;

import java.awt.Point;
import java.util.ArrayList;

public class AlgoritmeKiezer {

    Grid grid;

    private ArrayList<Point> obstakels;
    private ArrayList<Point> onbekenden;
    private ArrayList<Point> beginEind;

    AlgoritmeKiezer(ArrayList<Point> obstakels, ArrayList<Point> beginEind, ArrayList<Point> onbekenden){
        this.obstakels = obstakels;
        this.beginEind = beginEind;
        this.onbekenden = onbekenden;

    }
    public void RunAStar() {

        AStar astar = new AStar(grid.aantal_onbekenden, grid.aantal_nodes);

        astar.test(obstakels, beginEind, onbekenden);

        // Outcomment dit als je de coordinaten van nodes & obstakels wilt zien
        //astar.print(onbekenden);
    }
}
