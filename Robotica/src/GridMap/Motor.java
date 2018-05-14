package GridMap;

public class Motor extends Thread {

    private int speed = 0;

    private boolean richting = true;


    public Motor() {

        this.speed = 0;

    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isRichting() {
        return richting;
    }

    public void setRichting(boolean richting) {
        this.richting = richting;
    }


    @Override
    public void run() {

    }
}
