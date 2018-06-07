package Pathfinding_Simulatie;

import java.awt.*;

public class Robot {

    private long werktijd = 5000;

    private boolean killThread = false;
    Point position = new Point(0,0);
    Point eindPunt = new Point(-12, 13);

    private Sensor sensor = new Sensor();
    private Grid grid = new Grid(position, eindPunt, sensor.getAfstand());
    private AlgoritmeKiezer algokiezer;
    private Motor motor = new Motor();

    Thread gridThread = new Thread(grid);
    Thread sensorThread = new Thread(sensor);
    Thread linkerMotor = new Thread(motor);
    Thread rechterMotor = new Thread(motor);

    Robot(Point position) {
        System.out.println("Robot aangemaakt ...");
        this.position = position;
    }

    public void startRobot() {

        gridThread.start();
        sensorThread.start();
        linkerMotor.start();
        rechterMotor.start();

        long starttijd = System.currentTimeMillis();

        while (true){
            try{
                while(gridThread.isAlive()){
                    starttijd = System.currentTimeMillis();
                    if(Grid.getRichting() != null){
                        motor.rij(Grid.getRichting(), Grid.isRichtingVeranderd());
                    }
                }
                if(Grid.isKillThread()){
                    gridThread.join();
                    if ((System.currentTimeMillis() - starttijd) > werktijd) {
                        System.out.println("Systeem loopt te lang, systeem sluit");
                        sluitSysteem();
                    }
                }
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
//            if (position != Grid.getKeysByValue(Grid.map, Obstakel.E)) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    public void sluitSysteem(){
        try{
            sensorThread.join();
            linkerMotor.join();
            rechterMotor.join();
            System.out.println("Sensor Thread killed\nLinker motor thread killed\nrRechter motor thread killed\n");
            System.exit(0);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
    public boolean isKillThread() {
        return killThread;
    }
    public void setKillThread(boolean killThread) {
        this.killThread = killThread;
    }

}
