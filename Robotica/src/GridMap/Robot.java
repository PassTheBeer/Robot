package GridMap;

import java.awt.*;


public class Robot {

    Point position = new Point(0,0);

    Grid grid = new Grid(position);
    AlgoritmeKiezer algokiezer;



    Thread gridThread = new Thread(grid);

    private Sensor sensor = new Sensor();
    Thread sensorThread = new Thread(sensor);

    Motor motor = new Motor();
    Thread linkerMotor = new Thread(motor);
    Thread rechterMotor = new Thread(motor);

    public static int _tmp = 0;

    Robot(Point position) {
        System.out.println("Robot aangemaakt!! =__=");
        this.position = position;
        //startRobot();



    }

    public void startRobot() {

        gridThread.start();
        sensorThread.start();
        linkerMotor.start();
        rechterMotor.start();

        while (true) {

            if (position != Grid.getKeysByValue(Grid.map, Obstakel.P)) {

                //System.out.println(grid.getKeysByValue(grid.map,Obstakel.P));

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("ROBOT   ");

                System.out.println("asdfasdfasdf:   " + Grid.getKeysByValue(Grid.map, Obstakel.R));




            }


        }


    }





    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }


}
