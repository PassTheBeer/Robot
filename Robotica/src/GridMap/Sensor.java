package GridMap;

import java.util.concurrent.atomic.AtomicInteger;

public class Sensor implements Runnable {


    private static final AtomicInteger uniqueId = new AtomicInteger();
    private int ID;
    private float afstand;

    public Sensor() {
        this.ID = uniqueId.getAndIncrement();
        System.out.println("Sensor aangemaakt met id: " + ID);


    }

    @Override
    public void run() {
        //System.out.println("asdad");
        for (int i = 0; i <10;i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.ID + "   " + this.scan());
        }
    }

    public int scan() {

        int getal = (int) (Math.random() * (150)) + 1;


        return getal;
    }


    public void plaatsObject() {


    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
