package GridMap;

import java.awt.*;

public class Robot{

    Grid grid;
    AlgoritmeKiezer algokiezer;

    int aantal_Sensoren = 4;
    Thread sensoren[] = new Thread[aantal_Sensoren];

    Motor rechterMotor,linkerMotor;

    private Point position;


    Robot() {
        System.out.println("Robot aangemaakt!! =__=");
        grid = new Grid();
        algokiezer = new AlgoritmeKiezer();
        for (int i = 0; i < aantal_Sensoren; i++) {
            Thread temp = new Thread(new Sensor());
            sensoren[i] = new Thread(temp);

            sensoren[i].start();

        }

        Thread linkerMotor = new Motor();
        Thread rechterMotor = new Motor();
        position = new Point(0, 0);





    }
	
	/*public void StartRobot() {
		
	}

	*/

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
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

    public void stop(){
        rechterMotor.setSpeed(0);
        linkerMotor.setSpeed(0);
    }
//
//    public void RijNaarRichting(Double, Double) {
//
//    }
}
