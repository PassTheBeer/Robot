package Pathfinding;

import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Connection_uart {

    private static Connection_uart instance = new Connection_uart();
    private int connections;
    private Semaphore sem = new Semaphore(1);

    private Connection_uart() {

    }

    public static Connection_uart getInstance() {
        return instance;
    }

    public void connect(int value) {

        try {
            sem.acquire();
            try {
                System.out.println("Motor Amount of Permits of the Semaphore "+sem.availablePermits());
                doConnect(value);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                sem.release();
                System.out.println("Motor Amount of Permits of the Semaphore "+sem.availablePermits());

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void doConnect(int value) throws InterruptedException, IOException {

        synchronized (this) {
            connections++;

            //System.out.println("uart Amount of connections: " + connections);

        }

        /*

        final Console console = new Console();

        // print program title/header
        console.title("<-- The Pi4J Project -->", "Serial Communication Example");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // create an instance of the serial communications class
        final Serial serial = SerialFactory.createInstance();

        // create and register the serial data listener
        serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {


                try {
                    console.println("[HEX DATA]   " + event.getHexByteString());
                    console.println("[ASCII DATA] " + event.getAsciiString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            // create serial config object
            SerialConfig config = new SerialConfig();


            config.device(SerialPort.getDefaultPort())
                    .baud(Baud._38400)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1)
                    .flowControl(FlowControl.NONE);

            // parse optional command argument options to override the default serial settings.
//            if(args.length > 0){
//                config = CommandArgumentParser.getSerialConfig(config, args);
//            }


            console.box(" Connecting to: " + config.toString(),
                    " We are sending ASCII data on the serial port every 1 second.",
                    " Data received on serial port will be displayed below.");


            // open the default serial device/port with the configuration settings
            serial.open(config);

            // continuous loop to keep the program running until the user terminates the program
            while (console.isRunning()) {
                try {

                    serial.write("");
                    //System.out.println(value);

                    serial.write((byte) value);


                } catch (IllegalStateException ex) {
                    ex.printStackTrace();
                }

                // wait 1 second before continuing
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException ex) {
            console.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            return;
        }
        */

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        synchronized (this) {
            connections--;
        }

    }

    public int getConnections() {
        return connections;


    }
}
