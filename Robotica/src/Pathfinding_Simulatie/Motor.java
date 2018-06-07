package Pathfinding_Simulatie;

public class Motor implements Runnable {

    private int speed = 0;
    private int graden = 0;
    private boolean richting = true;
    private static boolean richtingVerandering;
    private Richting kant;

    public Motor() {
        System.out.println("Motor geïnitialiseerd");
        this.speed = 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void draai(int g){

    }
    public void rijVoren(){
        if(richtingVerandering) {
            graden = 0;
            draai(graden);
            System.out.println(kant);
        }
    }
    public void rijAchter(){
        if(richtingVerandering) {
            graden = 180;
            draai(graden);
            System.out.println(kant);
        }
    }
    public void rijLinks(){
        if(richtingVerandering) {
            graden = -90;
            draai(graden);
            System.out.println(kant);
        }
    }
    public void rijRechts(){
        if(richtingVerandering) {
            graden = 90;
            draai(graden);
            System.out.println(kant);
        }
    }
    public void rijBovenRechts(){
        if(richtingVerandering) {
            graden = 45;
            draai(graden);
            System.out.println(kant);
        }
    }
    public void rijBovenLinks(){
        if(richtingVerandering) {
            graden = -45;
            draai(graden);
            System.out.println(kant);
        }
    }
    public void rijAchterRechts(){
        if(richtingVerandering) {
            graden = 135;
            draai(graden);
            System.out.println(kant);
        }
    }
    public void rijAchterLinks(){
        if(richtingVerandering) {
            graden = -135;
            draai(graden);
            System.out.println(kant);
        }
    }

    public void rij(Richting richting, boolean rv){
        richtingVerandering = rv;
        kant = richting;
        if(!richtingVerandering)
            setSpeed(0);

        if(richting == Richting.voren)
            rijVoren();
        else if(richting == Richting.achter)
            rijAchter();
        else if(richting == Richting.links)
            rijLinks();
        else if(richting == Richting.rechts)
            rijRechts();
        else if(richting == Richting.bovenRechts)
            rijBovenRechts();
        else if(richting == Richting.bovenLinks)
            rijBovenLinks();
        else if(richting == Richting.onderRechts)
            rijAchterRechts();
        else
            rijAchterLinks();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        Connection_uart.getInstance().connect(speed);
        this.speed = speed;
    }

    public boolean isRichting() {
        return richting;
    }

    public void setRichting(boolean richting) {
        this.richting = richting;
    }
}
