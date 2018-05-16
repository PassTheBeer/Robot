package GridMap;

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
