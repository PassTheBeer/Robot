package GridMap;

public class Motor implements Runnable {

    private int speed = 0;

    private boolean richting = true;


    public Motor() {
        System.out.println("Motor geïnitialiseerd");

        this.speed = 0;

    }





    @Override
    public void run() {

        setSpeed(50);

    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        for(int i = 0; i<10;i++){
            Connection_uart.getInstance().connect(speed);
        }

        this.speed = speed;
    }

    public boolean isRichting() {
        return richting;
    }

    public void setRichting(boolean richting) {
        this.richting = richting;
    }
}
