package GridMap;

import java.util.concurrent.Semaphore;

public class Connection {

    private static Connection instance = new Connection();
    private int connections;
    Semaphore sem = new Semaphore(1);

    private Connection() {

    }

    public static Connection getInstance() {
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

    public void doConnect() {

        synchronized (this) {
            connections++;
            System.out.println("Amount of connections: " + connections);
        }

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            connections--;
        }

    }
}
