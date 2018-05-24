package GridMap;

import java.nio.channels.FileLock;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

public class Sensor implements Runnable {


    private static final AtomicInteger uniqueId = new AtomicInteger();
    private int ID;




    public Sensor() {
        this.ID = uniqueId.getAndIncrement();
        System.out.println("Sensor aangemaakt met id: " + ID);


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


        //afstand = Connection_spi.getInstance().getAfstand();
        //System.out.println(afstand);

    }




    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }



}
