package Pathfinding;

import java.util.concurrent.atomic.AtomicInteger;

public class Sensor implements Runnable {

    private static final AtomicInteger uniqueId = new AtomicInteger();
    private int ID;

    private double zicht;
    private boolean zieObstakel;

    public Sensor() {
        this.ID = uniqueId.getAndIncrement();
        System.out.println("Sensor aangemaakt met id: " + ID);
        zicht = 1.50;
    }

    @Override
    public void run() {
        //System.out.println("asdad");
        while (true) {
            //System.out.println(this.ID + "   ");
            this.scan();
        }
    }

    private void scan() {
        Connection_spi.getInstance().connect();
        zicht = 0.70;
        if(zicht <= 1.00){
            zieObstakel = true;
        }
        else
            zieObstakel = false;
        //zicht = 1.50;
        //afstand = Connection_spi.getInstance().getAfstand();
        //System.out.println(afstand);
    }

    public double getZicht() {
        return zicht;
    }
    public void setZicht(double zicht) {
        this.zicht = zicht;
    }

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isZieObstakel() {
        return zieObstakel;
    }
    public void setZieObstakel(boolean zieObstakel) {
        this.zieObstakel = zieObstakel;
    }
}
