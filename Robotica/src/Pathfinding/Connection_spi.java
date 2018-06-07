package Pathfinding;

import com.pi4j.io.spi.SpiDevice;
import com.pi4j.util.Console;

import java.io.IOException;
import java.util.concurrent.Semaphore;


//https://github.com/Pi4J/pi4j/tree/master/pi4j-example/src/main/java

public class Connection_spi {

    private static Connection_spi instance = new Connection_spi();
    static private Semaphore sem = new Semaphore(1);
    private int connections;

    // SPI device
    public static SpiDevice spi = null;

    // ADC channel count
    public static short ADC_CHANNEL_COUNT = 8;  // MCP3004=4, MCP3008=8

    // create Pi4J console wrapper/helper
    // (This is a utility class to abstract some of the boilerplate code)
    protected static final Console console = new Console();

    private int afstand = 0;

    private Connection_spi() {

    }

    public void connect() {

        try {
            sem.acquire();
            try {
                //System.out.println("Sensor Amount of Permits of the Semaphore "+sem.availablePermits());
                afstand = doConnect();
            } finally {
                //System.out.println("Sensor Amount of Permits of the Semaphore "+sem.availablePermits());
                sem.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int doConnect() {

        synchronized (this) {
            connections++;
            //System.out.println("spi Amount of connections: " + connections);
        }
//        console.title("<-- The Pi4J Project -->", "SPI test program using MCP3004/MCP3008 AtoD Chip");
//
//        console.promptForExit();
//
//        try {
//            spi = SpiFactory.getInstance(SpiChannel.CS0, SpiDevice.DEFAULT_SPI_SPEED, SpiDevice.DEFAULT_SPI_MODE);// default spi mode 0
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        while (console.isRunning()) {
//            try {
//                read();
//                Thread.sleep(1000);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//        console.emptyLine();
//
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
        }
        int test = (int) (Math.random() * 50) + 1;
        //System.out.println(test);
        return test;
    }

    public static void read() throws IOException, InterruptedException {
        for(short channel = 0; channel < ADC_CHANNEL_COUNT; channel++) {
            int conversion_value = getConversionValue(channel);
            console.print(String.format(" | %04d", conversion_value)); // print 4 digits with leading zeros
        }

        console.print(" |\r");
        Thread.sleep(250);
    }


//     Communicate to the ADC chip via SPI to get single-ended conversion value for a specified channel.
//     @param channel analog input channel on ADC chip
//     @return conversion value for specified analog input channel
//     @throws IOException

    public static int getConversionValue(short channel) throws IOException {
        // create a data buffer and initialize a conversion request payload
        byte data[] = new byte[]{
                (byte) 0b00000001,                              // first byte, start bit
                (byte) (0b10000000 | (((channel & 7) << 4))),    // second byte transmitted -> (SGL/DIF = 1, D2=D1=D0=0)
                (byte) 0b00000000                               // third byte transmitted....don't care
        };
        // send conversion request to ADC chip via SPI channel
        byte[] result = spi.write(data);

        // calculate and return conversion value from result bytes
        int value = (result[1] << 8) & 0b1100000000; //merge data[1] & data[2] to get 10-bit result
        value |= (result[2] & 0xff);
        return value;
    }

    public int getConnections() {
        return connections;
    }

    public static Connection_spi getInstance() {
        return instance;
    }

    public int getAfstand() {
        return afstand;
    }
}
