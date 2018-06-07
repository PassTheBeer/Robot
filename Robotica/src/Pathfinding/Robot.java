package Pathfinding;

import java.awt.*;

@SuppressWarnings("Duplicates")
public class Robot {

    private long werktijd = 5000;

    private boolean killThread = false;
    Point position = new Point(0,0);
    Point eindPunt = new Point(20, -15);

    KinderLocker sex= new KinderLocker();
    private Sensor sensor = new Sensor();
    private Grid grid = new Grid(position, eindPunt, sensor.getZicht(), sex);
    private Motor motor = new Motor();

    Thread gridThread = new Thread(grid);
    Thread sensorThread = new Thread(sensor);
    Thread linkerMotor = new Thread(motor);
    Thread rechterMotor = new Thread(motor);

    Point coole;

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
                    position = Grid.getPositie();
                    starttijd = System.currentTimeMillis();

                    if(Grid.getRichting() != null){
                        motor.rij(Grid.getRichting(), Grid.isRichtingVeranderd());
                        if(sensor.isZieObstakel()){
//                            System.out.println(Grid.getRichting());
                            if(Grid.getRichting() == Richting.voren){
                                coole = new Point((int)position.getX(), (int)position.getY() - 1);
                            }
                            if(Grid.getRichting() == Richting.achter){
                                coole = new Point((int)position.getX(), (int)position.getY() + 1);
                                break;

                            }
                            if(Grid.getRichting() == Richting.links){
                                coole = new Point((int)position.getX() - 1, (int)position.getY());

                            }
                            if(Grid.getRichting() == Richting.rechts){
                                coole = new Point((int)position.getX() + 1, (int)position.getY());

                            }
                            if(Grid.getRichting() == Richting.bovenLinks){
                                coole = new Point((int)position.getX() - 1, (int)position.getY() - 1);

                            }
                            if(Grid.getRichting() == Richting.bovenRechts){
                                coole = new Point((int)position.getX() + 1, (int)position.getY() - 1);

                            }
                            if(Grid.getRichting() == Richting.onderLinks){
                                coole = new Point((int)position.getX() - 1, (int)position.getY() + 1);

                            }
                            if(Grid.getRichting() == Richting.onderRechts){
                                coole = new Point((int)position.getX() + 1, (int)position.getY() + 1);

                            }
                            grid.plaatsObstakel(coole);
                            if(motor.isGereden()){
                                System.out.println(motor.isGereden());
                                sex.anders();
                                motor.setGereden(false);
                            }
                        }
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
