package GridMap;

import java.util.concurrent.Semaphore;

public class Connection_spi {

    private static Connection_spi instance = new Connection_spi();
    private int connections;
    private Semaphore sem = new Semaphore(1);

    private Connection_spi() {

    }

    public static Connection_spi getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            doConnect();
        } finally {
            sem.release();
        }

    }

    private void doConnect() {

        synchronized (this) {
            connections++;
            System.out.println("Amount of connections: " + connections);
        }

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
