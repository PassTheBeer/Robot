package Pathfinding;

public enum Richting {
    voren(1),
    achter(2),
    links(3),
    rechts(4),
    bovenRechts(5),
    bovenLinks(6),
    onderRechts(7),
    onderLinks(8);

    private int number;

    Richting(int num){
        number = num;
    }
}
