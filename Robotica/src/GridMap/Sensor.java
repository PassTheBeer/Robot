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
        for (int i = 0; i < 10; i++) {

            System.out.println(this.ID + "   ");
            this.scan();
        }
    }


    public void scan() {
        Connection.getInstance().connect();


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
