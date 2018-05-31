package GridMap;

import java.awt.*;


public class Robot {

    Grid grid;
    AlgoritmeKiezer algokiezer;

    private Motor linkerMotor, rechterMotor;
    private Sensor sensorThread;

    private Point position = new Point();

    public static int _tmp = 0;

    Robot(Point position) {
        System.out.println("Robot aangemaakt!! =__=");

        this.position = position;
        grid = new Grid(position);


        Thread sensorThread = new Thread(new Sensor());

        sensorThread.start();

        Thread linkerMotor = new Thread(this.linkerMotor);
        Thread rechterMotor = new Thread(this.rechterMotor);
        linkerMotor.start();
        rechterMotor.start();

        grid.algokiezer.RunAStar();


    }

    public void startRobot() {

        while (true) {

            if (position != grid.getKeysByValue(grid.map, Obstakel.P)) {

                //System.out.println(grid.getKeysByValue(grid.map,Obstakel.P));

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("ROBOT   ");







            }


        }


    }




    //
    public void RijNaarVoren() {
        rechterMotor.setRichting(true);
        linkerMotor.setRichting(true);

        rechterMotor.setSpeed(50);
        linkerMotor.setSpeed(50);

    }

    //
    public void RijNaarAchter() {
        rechterMotor.setRichting(false);
        linkerMotor.setRichting(false);

        rechterMotor.setSpeed(50);
        linkerMotor.setSpeed(50);
    }

    public void stop() {
        rechterMotor.setSpeed(0);
        linkerMotor.setSpeed(0);
    }

//    public void RijNaarRichting(Double, Double) {
//
//    }



    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }


}
