
package Pathfinding;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
@SuppressWarnings("Duplicates")

public class Algorithm {

    Grid grid;
    KinderLocker kl;

    Scanner sc = new Scanner(System.in);

    private ArrayList<Point> path;
    private Node[][] neighbours;
    private double hWaarde;
    private Richting richting;
    private int a_o;

    private ArrayList<Point> obstakels;
    private ArrayList<Point> onbekenden;
    private ArrayList<Point> beginEind;

    private boolean richtingVeranderd;

    private static boolean wait;

    public Algorithm(ArrayList<Point> ob, ArrayList<Point> be, ArrayList<Point> onb, KinderLocker kl){
        this.kl = kl;
        wait = false;
        obstakels = ob;
        beginEind = be;
        onbekenden = onb;
        a_o = onbekenden.size();

        neighbours = new Node[a_o][a_o];
        path = new ArrayList<Point>();

    }

    //BerekenH Waarde methode
    public double BerekenH(double e_x, double e_y, double o_x, double o_y) {
        double afstands = Math.sqrt(Math.pow((e_x * Grid.lengte_node) - (o_x * Grid.lengte_node), 2)
                        + Math.pow((e_y * Grid.lengte_node) - (o_y * Grid.lengte_node), 2));
        return afstands;
    }

    public void print(ArrayList<Point> onbekenden) {
        for (int i = 0; i < onbekenden.size(); i++) {
            System.out.println("Node " + i + ": " + onbekenden.get(i).getX() + "," + onbekenden.get(i).getY());
        }
        System.out.println("");
    }

    public void runAlgo() {

        System.out.println("wait: " + wait);
        System.out.println("\n");

        System.out.println(obstakels.size());
        System.out.println("Wilt de robot terug rijden? (True = ja, False = nee)");
        boolean terug = sc.nextBoolean();

        //Standaard declaraties voor het duidelijk maken voor toekomstige berekeningen en codes
        double min_h_waarde = Double.MAX_VALUE;

        double b_x = beginEind.get(0).getX();
        double b_y = beginEind.get(0).getY();
        double e_x = beginEind.get(1).getX();
        double e_y = beginEind.get(1).getY();

        int wall_x = (int) e_x;
        int wall_y = (int) e_y;

        Point temp = new Point(0, 0);
        Point begin = new Point((int) b_x, (int) b_y);
        Point eind = new Point((int) e_x, (int) e_y);
        Stack path_terug = new Stack();

        boolean bereikt = false;

        //Extra indexes voor in de for loop
        int y = 0, j = 0;

        //Zet de begin positie al vast in de Stack (voor de terug weg) en in het pad (heen weg)
        path_terug.push(temp);
        path.add(temp);

        //Zorg er voor dat als er een obstakel in de begin of eind positie geplaatst wordt. Haal hem dan weg
        if (obstakels.contains(eind)) {
            obstakels.remove(eind);
            grid.map.replace(eind, Obstakel.E);
        }
        if(obstakels.contains(temp)) {
            obstakels.remove(begin);
            grid.map.replace(begin, Obstakel.S);
        }

        System.err.println("");

        //Voer de onderstaande algoritme uit zolang de robot niet de eindpositie bereikt heeft
        while (!bereikt) {
            richtingVeranderd = false;

            //Refresh de minimale h waarde steeds. Zodat hij goed berekend wordt voor elke buurman
            min_h_waarde = Double.MAX_VALUE;
            for (int i = 0; i < onbekenden.size(); i++) {

                //Bereken het verschil tussen de pos robot en elk punt in de map
                double vers_x = b_x - onbekenden.get(i).getX();
                double vers_y = b_y - onbekenden.get(i).getY();

                //Is het verschil ongelijk aan de pos robot en zit het verschil tussen de -1 en 1. Dan is het een buur
                if (!(vers_x == 0 && vers_y == 0) && (vers_x >= -1 && vers_x <= 1) && (vers_y >= -1 && vers_y <= 1)) {

                    //Maak een buur point aan met de gevonden coordinaten
                    Point buur_p = new Point((int) onbekenden.get(i).getX(), (int) onbekenden.get(i).getY());

                    //Als er obstakels aanwezig zijn
                    if (obstakels.size() != 0) {
                        for (int k = 0; k < obstakels.size(); k++) {
                                //Als de coordinaat van een buur gelijk staat aan een van de obstakels.
                                //Zet dan de hWaarde op maximaal (Zo gaat de robot nooit door een obstakel)
                                if (buur_p.getX() == obstakels.get(k).getX() && buur_p.getY() == obstakels.get(k).getY()) {
                                    grid.map.put(obstakels.get(k), Obstakel.M);
                                    hWaarde = Double.MAX_VALUE;
                                    break;
                                }
                                //Zo niet, Bereken dan de hWaarde voor die buurman
                                else {
                                    Grid.map.replace(buur_p, Obstakel.N);
                                    hWaarde = BerekenH(e_x, e_y, onbekenden.get(i).getX(), onbekenden.get(i).getY());
                                }
                            //Maak de buurman nodes met de berekende hWaarde (Dit is voor de sier)
                            neighbours[j][y] = new Node("Node " + y, hWaarde, buur_p, Obstakel.N);
                        }
                    }
                    //Als er geen obstakels zijn
                    else {
                        Grid.map.replace(buur_p, Obstakel.N);
                        hWaarde = BerekenH(e_x, e_y, onbekenden.get(i).getX(), onbekenden.get(i).getY());
                        neighbours[j][y] = new Node("Node " + y, hWaarde, buur_p, Obstakel.N);
                    }
                    //Verplaats Point begin steeds naar het punt waarde de hWaarde het kleinst is.
                    //Het point waar begin het laatst staat, is het punt met de laagste hWaarde
                    if (hWaarde < min_h_waarde) {
                        min_h_waarde = hWaarde;
                        begin.move((int) buur_p.getX(), (int) buur_p.getY());
                    }
                    j++;
                }
            }
            //Even slapen (voor de simulatie)
            try {
                Thread.sleep(250);
            } catch (InterruptedException exc) {

            }
            //Laat zien dat de robot van plaats is verwisseld
            grid.map.replace(new Point((int) b_x, (int) b_y), Obstakel.R);

            //Voeg die plaats toe in de path ArrayList & push
            path.add(begin.getLocation());
            path_terug.push(begin.getLocation());
//            System.out.println(begin.getLocation());

            //Als de positie van de robot aan het einde van de map staat, vergroot de map met 1 kolom.
            if (b_x == wall_x) {
                Grid.voeg_xRij(b_x);
                wall_x = Grid.getGrid_width();
            }
            //Als de positie van de robot aan het einde van de map staat, vergroot de map met 1 rij.
            if (b_y == wall_y) {
                Grid.voeg_yRij(b_y);
                wall_y = Grid.getGrid_height();
            }
            //Print de map (voor de simulatie)
            System.out.println(Grid.printGrid(Grid.map));

            //Als de robot het eindpunt bereikt heeft, wacht dan eventjes en zet bereikt op true zodat hij uit de for loop gaat
            if (b_x == eind.getX() && b_y == eind.getY()) {
                richtingVeranderd = false;
                System.out.println("Bestemming bereikt. U heeft 2 seconden te tijd om een verfrissing te pakken! SHIET OP");
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException exc) {
                }
                bereikt = true;
                if(!terug){
                    System.out.println("Bestemming bereikt en de robot gaat niet terug, Systeem sluit over 2 sec");
                    try {
                        Thread.sleep(2000);
                    }
                    catch (InterruptedException exc) {
                    }
                    grid.kill();
                }
            }

            //Verplaats de positie van de robot met de gevonden positie
            b_x = begin.getX();
            b_y = begin.getY();
            Grid.setPositie(new Point((int)b_x, (int)b_y));
            //Checken welke kant de robot eigenlijk gereden heeft, dit is noodzakelijk voor de motoren, zo weten de motoren
            //Welke richting de robot moet rijden.
            if(b_x == path.get(y).getX() && b_y > path.get(y).getY()) {
                richtingVeranderd = true;
                grid.setRichting(Richting.voren, richtingVeranderd);
            }
            else if(b_x == path.get(y).getX() && b_y < path.get(y).getY()){
                richtingVeranderd = true;
                grid.setRichting(Richting.achter, richtingVeranderd);
            }
            else if(b_x > path.get(y).getX() && b_y == path.get(y).getY()){
                richtingVeranderd = true;
                grid.setRichting(Richting.links, richtingVeranderd);
            }
            else if(b_x < path.get(y).getX() && b_y == path.get(y).getY()){
                richtingVeranderd = true;
                grid.setRichting(Richting.rechts, richtingVeranderd);
            }
            else if(b_x > path.get(y).getX() && b_y < path.get(y).getY()){
                richtingVeranderd = true;
                grid.setRichting(Richting.bovenRechts, richtingVeranderd);
            }
            else if(b_x < path.get(y).getX() && b_y < path.get(y).getY()){
                richtingVeranderd = true;
                grid.setRichting(Richting.bovenLinks, richtingVeranderd);
            }
            else if(b_x < path.get(y).getX() && b_y > path.get(y).getY()){
                richtingVeranderd = true;
                grid.setRichting(Richting.onderLinks, richtingVeranderd);
            }
            else{
                richtingVeranderd = true;
                grid.setRichting(Richting.onderRechts, richtingVeranderd);
            }
//            if(path.get(y).getX() > b_x && path.get(y).getY() > b_y){
//                richtingVeranderd = true;
//                grid.setRichting(Richting.onderRechts, richtingVeranderd);
//            }

            y++;

        }
        //Als de robot het eindpunt bereikt heeft, dan loopt hij de Stack af zodat de robot terug loopt.
        if (bereikt & terug) {
            richtingVeranderd = false;
            Iterator<Point> i = path_terug.iterator();
            while (i.hasNext()) {
                Point t = (Point) path_terug.pop();

                grid.map.replace(t, Obstakel.R);
                System.out.println(Grid.printGrid(Grid.map));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException exc) {
                }
                grid.map.replace(t, Obstakel.N);
            }
            System.out.println("Robot weer terug bij het begin, Systeem sluit over 2 sec");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException exc) {
            }
            Grid.kill();
        }
    }
    //Print de alle nodes (voor de simulatie)
    public void printPath() {
        Iterator<Point> it = path.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().getLocation());
        }
    }
    public boolean isRichtingVeranderd() {
        return richtingVeranderd;
    }

    public void setRichtingVeranderd(boolean richtingVeranderd) {
        this.richtingVeranderd = richtingVeranderd;
    }
    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }
}


